/**
 * 
 */
package com.czz.hwy.action.users;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.czz.hwy.bean.user.Users;
import com.czz.hwy.service.usermanagement.UsersService;
import com.czz.hwy.utils.AccessOrigin;
import com.czz.hwy.utils.CommonUtils;
import com.czz.hwy.utils.CreateCheckImg;


/**
 * web端用户登录
 * @author 佟士儒
 *
 */
@Controller
public class UserLoginAction{
	
	//验证码的长度，如果修改login.js中validateCodeLen也要修改
	private final static int  checkNum = 4;
	
	@Autowired
	private UsersService usersService;

	@SuppressWarnings("rawtypes")
    @Autowired
	private RedisTemplate redisTemplate;
	
	@Resource
	private AccessOrigin accessOrigin;
	
	/**
	 * 生成验证码图片
     * @param date 唯一标识,防止调用存在缓存
     * @return 生成验证码图片
	 */
	@SuppressWarnings("unchecked")
    @RequestMapping(value="/CheckImg/{date}", method = RequestMethod.GET)
	public void getCheckImg(@PathVariable(value="date") String date, HttpServletRequest request, 
	        HttpServletResponse response) throws IOException{
	    //设置不缓存图片  
        response.setHeader("Pragma", "No-cache");

        response.setHeader("Cache-Control", "No-cache");
        
        response.setDateHeader("Expires", 0);
        
        //指定生成的相应图片  
        response.setContentType("image/jpeg");

        CreateCheckImg idCode = new CreateCheckImg();
        
        BufferedImage image =new BufferedImage(idCode.getWidth() , idCode.getHeight() , BufferedImage.TYPE_INT_BGR);
        
        Graphics2D g = image.createGraphics();  
        //定义字体样式  
        Font myFont = new Font("黑体" , Font.ITALIC , 24);  
        //设置字体  
        g.setFont(myFont);  
          
        g.setColor(idCode.getRandomColor(200 , 250));  
        //绘制背景  
        g.fillRect(0, 0, idCode.getWidth() , idCode.getHeight());  
          
        g.setColor(idCode.getRandomColor(180, 200));
        
        idCode.drawRandomLines(g, 50);  
        
        //获取随机数据
        String[] randomStrs = idCode.getRandomStr(checkNum);
        //将验证码储存到缓存服务器中
        String validateCode = CommonUtils.getEncryptByPublicKey(CommonUtils.getUUID());
        //usersService.addValidateCodeForRedis(validateCode, StringUtils.join(randomStrs));
        redisTemplate.opsForValue().set(validateCode, StringUtils.join(randomStrs));
        redisTemplate.expire(validateCode, 4, TimeUnit.HOURS);
        //返回生成的验证码到Cookie
        Cookie cookie = new Cookie("captchaCode",validateCode);  
        cookie.setPath("/");
        cookie.setMaxAge(14400);
        response.addCookie(cookie);
        
        idCode.drawRandomString(randomStrs, g);
        
        g.dispose();
        ImageIO.write(image, "JPEG", response.getOutputStream());
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
	}
	/**
	 * 验证客户端传回的验证码是否正确
	 * @param checkcode 验证码
     * @return 结果 
	 */
	@RequestMapping(value="/validateCode/{checkcode}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> validateCode(@PathVariable(value="checkcode") String checkcode, 
	        HttpServletRequest request, HttpServletResponse response){
		// 从缓存服务器中获取验证码
		// String rightCode = usersService.getValidateCodeForRedis("11");
	    String captchaCode = CommonUtils.getCookieValue(request, "captchaCode");
	    String validateCode = (String) redisTemplate.opsForValue().get(captchaCode);
	    Map<String,Object> map = new HashMap<String,Object>();
		
		if(checkcode.equalsIgnoreCase(validateCode)){
			map.put("status", 1);
		}else{
			
			map.put("status", 2);
		}
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
		return map;
	}
	/**
	 * 登录用户验证
     * @param userId 用户名
     * @param pwd 密码 
     * @return 结果      
	 */
    @SuppressWarnings("unchecked")
    @RequestMapping(value="/validateUser/{userId}/{pwd}", method = RequestMethod.GET)
    @ResponseBody    
	public Map<String,Object> validateUser(@PathVariable(value="userId") String userId,
	        @PathVariable(value="pwd") String pwd, HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
            /*
             * TODO T int employeeId = Integer.parseInt(userId); //通过用户编号获取用户
             * Users u = usersService.getUserInfoByEmployeeId(employeeId);
             */
			
            // 根据员工号号查询当前是否已存在员工号
            Users paramUser = new Users();
            paramUser.setShowname(userId);
            Users u = usersService.getUserInfoByBean(paramUser);

			if(u==null){
				
				map.put("status", 2);
				
			}else if(u!=null){
				if(!pwd.equals(u.getPassword())){
					
					map.put("status", 2);
					
				}else{
			        //将用户信息存到缓存服务器中
			        String user = CommonUtils.getEncryptByPublicKey(userId);
			        //usersService.addValidateCodeForRedis(validateCode, StringUtils.join(randomStrs));
			        redisTemplate.opsForValue().set(user, u);
			        redisTemplate.expire(user, 4, TimeUnit.HOURS);

			        //返回生成的验证码到Cookie
			        Cookie cookie = new Cookie("userToken", user); 
			        cookie.setPath("/");
			        cookie.setMaxAge(14400);
			        response.addCookie(cookie);
			        
			        map.put("role", u.getRoleId());
					map.put("status", 1);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
			map.put("status", 3);
		}
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        return map;
	}
    
	/**
	 * 用户退出
	 * @throws IOException 
	 */
    @SuppressWarnings("unchecked")
    @RequestMapping(value="/logout", method = RequestMethod.GET)    
    @ResponseBody
	public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String captchaCode = StringUtils.EMPTY;
        String userToken = StringUtils.EMPTY;
        Cookie[] cookie = request.getCookies();
        for (Cookie c : cookie) {
            if ("captchaCode".equals(c.getName())) {
                captchaCode = c.getValue();
            }
            if ("userToken".equals(c.getName())) {
                userToken = c.getValue();
            }            
        }
        // 清除缓存服务器中的验证码
        redisTemplate.delete(captchaCode);
        // 清除缓存服务器中的登录用户信息
        redisTemplate.delete(userToken);
        // 设置允许跨域访问的路径
        CommonUtils.setAccessOrigin(request, response, accessOrigin);        
	}
}
