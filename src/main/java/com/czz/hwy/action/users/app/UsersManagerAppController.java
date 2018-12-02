package com.czz.hwy.action.users.app;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.czz.hwy.bean.user.app.UsersApp;
import com.czz.hwy.service.usermanagement.app.UsersAppService;
import com.czz.hwy.utils.ConstantUtil;
import com.czz.hwy.utils.JsonDateValueProcessor;

/**
 * 通过此Action里的接口，获取用户信息
 * 
 * @author 以克论净环卫管理系统 佟士儒 20161229
 * @version V1.0
 */
@Controller
@RequestMapping(value = "/usersManAppController")
public class UsersManagerAppController {
	
    @Autowired
    UsersAppService usersAppService;
    /**
     * 【APP端】上传头像
     * @param employeeId 员工ID
     * @param file 图片信息
     */
    @RequestMapping(value="/upUserPhoto", method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> uploadPicture(Integer employeeId,
            MultipartHttpServletRequest muRequest,HttpServletRequest request, HttpServletResponse response) {
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();
        // 实现图片的上传
        // 定义上传图片的路径
        if (employeeId == null) {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", "请先填写员工号,在上传图片");
            return map;
        }
        MultipartFile file = muRequest.getFile("file");
        Date date = new Date();
        String realPath = request.getServletContext().getRealPath("/public/photo/" + employeeId);
        File upload_path = new File(realPath);
        // 生成上传文件名
        String newFile = date.getTime() + ".jpg";
        if (!upload_path.exists()) {
            upload_path.mkdir();
        }
        // 创建上传路径
        File newPath = new File(realPath + "/" + newFile);
        // 将上传的图片保存到指定目录中
        try {
            file.transferTo(newPath);
            //改变上传图片的大小
            String filedir = realPath + "/" + newFile;
            File oldfile = new File(filedir);
            // 文件存在时
            if (oldfile.exists()) { 
                // 读入原文件
                InputStream is = new FileInputStream(filedir); 
                // 此时在读入流中改变上传图片的大小(将图片进行压缩)
                BufferedImage prevImage = ImageIO.read(is);
                double width = prevImage.getWidth();
                double height = prevImage.getHeight();
                double percent = 200 / width;
                int newWidth = (int) (width * percent);
                int newHeight = (int) (height * percent);
                BufferedImage image = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_BGR);
                Graphics graphics = image.createGraphics();
                graphics.drawImage(prevImage, 0, 0, newWidth, newHeight, null);
                FileOutputStream os = new FileOutputStream(filedir);
                ImageIO.write(image, "jpg", os);
                os.flush();
                is.close();
                os.close();
            }
            map.put("result", ConstantUtil.SUCCESS);
            map.put("information", ConstantUtil.ERROR_MSG);
        } catch (IOException e) {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", ConstantUtil.ERROR_MSG);
        }
        return map;
    }
    
    /**
     * 手机登录
     * @param employeeId 员工ID
     * @param password 密码
     * @param imei 手机的imei号
     * @param file 图片信息
     */
    @RequestMapping(value="/loginApp/{employeeId}/{password}/{imei}", method=RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody    
    public String loginApp(@PathVariable(value="employeeId")Integer employeeId, 
            @PathVariable(value="password")String password, @PathVariable(value="imei")String imei, 
            HttpServletRequest request) {
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();
        // 查询登录用户信息
        UsersApp users = usersAppService.login(request, employeeId, password, imei);
        // 判断如果查询user对象结果不为空的话返回对象信息，空则返回错误信息
        if (users != null) {
            if("1".equals(users.getUpimei())){
                map.put("result", ConstantUtil.FAIL);
                map.put("information", ConstantUtil.UPIME);
            }else if("2".equals(users.getUpimei())){
                map.put("result", ConstantUtil.FAIL);
                map.put("information", ConstantUtil.UPEE);
            }
            map.put("result", ConstantUtil.SUCCESS);
            map.put("information", users);
        } else {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", ConstantUtil.USER_INFO_ERR);
        }
        // 对返回值里面包含日志字段进行json格式的过滤
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class,
                new JsonDateValueProcessor());
        // 返回用户信息
        JSONObject json = JSONObject.fromObject(map, jsonConfig);
        return json.toString();
    }
}
