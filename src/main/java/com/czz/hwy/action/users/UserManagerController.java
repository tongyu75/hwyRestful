package com.czz.hwy.action.users;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.czz.hwy.bean.mission.UserArea;
import com.czz.hwy.bean.user.Users;
import com.czz.hwy.service.mission.UserAreaService;
import com.czz.hwy.service.usermanagement.UsersService;
import com.czz.hwy.utils.AccessOrigin;
import com.czz.hwy.utils.CommonUtils;
import com.czz.hwy.utils.ConstantUtil;
import com.czz.hwy.utils.JsonDateValueProcessor;

@Controller
@RequestMapping(value = "/userManController")
public class UserManagerController {

    @Autowired
    private UsersService usersService;

    @Resource
    private AccessOrigin accessOrigin;    
    
    // 员工归属责任区管理业务层
    @Autowired
    private UserAreaService userAreaService;
    
    /**
     * 人员信息查询
     * @param acceptContent 查询条件
     */
    @RequestMapping(value="/user", method=RequestMethod.GET)
    @ResponseBody
    public String getUserForSearch(String acceptContent, HttpServletRequest request, 
            HttpServletResponse response) {
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();
        // 判断接收参数
        if(acceptContent == null || "".equals(acceptContent)){
            map.put("result", ConstantUtil.FAIL);
            map.put("information",  "查询失败，查询信息不能为空！");
            return map.toString();
        }
        
        // 对请求参数解码并转换为人员信息对象
        acceptContent = CommonUtils.getDecodeParam(acceptContent);
        JSONObject json = JSONObject.fromObject(acceptContent);
        Users user = (Users) JSONObject.toBean(json, Users.class);
        // 查询人员信息记录总条数
        int count = usersService.getUserCount(user);
        // 查询人员信息记录
        List<Users> resultUsers = usersService.getAllUserByBean(user);
        // 设置返回数据
        map.put("result", ConstantUtil.SUCCESS);
        map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
        map.put("total", count);
        map.put("rows", resultUsers);
        
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        // 返回用户信息
        JSONObject jsonobject = JSONObject.fromObject(map, jsonConfig);
        return jsonobject.toString();
    }
    
    /**
     * 添加员工信息
     * @param acceptContent 员工信息
     */
    @RequestMapping(value="/user", method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> insertUser(String acceptContent, HttpServletRequest request, HttpServletResponse response) {
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();
        // 判断接收参数
        if(acceptContent == null || "".equals(acceptContent)){
            map.put("result", ConstantUtil.FAIL);
            map.put("information",  "新增失败，新增信息不能为空！");
            return map;
        }
        
        // 对请求参数解码并转换为人员信息对象
        JSONObject json = JSONObject.fromObject(acceptContent);
        Users user = (Users) JSONObject.toBean(json, Users.class);
        
        // 根据员工号号查询当前是否已存在员工号
        Users userId = new Users();
        userId.setEmployeeId(user.getEmployeeId());
        Users resultUser = usersService.getUserInfoByBean(userId);
        
        // 员工号不存在,添加员工信息
        if (resultUser == null) {
            // 如果没有身份证号，默认是123456，如果有身份证号密码为身份证后六位
            String idCard = user.getIdCard();
            if (StringUtils.isEmpty(idCard)) {
            	user.setPassword("123456");
            } else {
            	if (idCard.length() > 6) {
            		user.setPassword(idCard.substring(idCard.length() - 6, idCard.length()));
            	} else {
                	user.setPassword("123456");
            	}
            }
            user.setStatus(1);
            // 绑定手表标识(2.未绑定)
            user.setHasWatch(2);
            // 绑定手机标识(2.未绑定)
            user.setHasMobile(2);
            int opinion = usersService.insertUsers(user);
            
            // 插入成功
            if(opinion > 0){
                map.put("result", ConstantUtil.SUCCESS);
                map.put("information", ConstantUtil.INSERT_SUCCESS_MSG);
            }else{
                map.put("result", ConstantUtil.FAIL);
                map.put("information", ConstantUtil.INSERT_FAIL_MSG);
            }
        } else {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", "该员工号已存在。");
        }            
        return map;
    }    
    
    /**
     * 修改员工信息
     * @param acceptContent 员工信息
     */
    @RequestMapping(value="/user", method=RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> updateUser(String acceptContent, HttpServletRequest request, HttpServletResponse response) {
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();
        // 判断接收参数
        if(acceptContent == null || "".equals(acceptContent)){
            map.put("result", ConstantUtil.FAIL);
            map.put("information",  "修改失败，修改信息不能为空！");
            return map;
        }
        // 对请求参数解码并转换为人员信息对象
        JSONObject json = JSONObject.fromObject(acceptContent);
        Users user = (Users) JSONObject.toBean(json, Users.class);
        // 修改员工信息
        int opinion = usersService.updateUsersByBean(user);
        // 修改成功
        if(opinion > 0){
            map.put("result", ConstantUtil.SUCCESS);
            map.put("information", ConstantUtil.UPDATE_SUCCESS_MSG);
        }else{
            map.put("result", ConstantUtil.FAIL);
            map.put("information", ConstantUtil.UPDATE_FAIL_MSG);
        }
        return map;
    }
    
    /**
     * 删除员工信息
     * @param employeeId 员工ID
     */
    @RequestMapping(value="/user", method=RequestMethod.DELETE)
    @ResponseBody    
    public Map<String, Object> deleteUser(Integer employeeId, 
            HttpServletRequest request, HttpServletResponse response) {
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();
        //根据ID获取员工归属责任区关系信息
        UserArea bean = new UserArea();
        bean.setEmployeeId(employeeId);
        UserArea userArea = userAreaService.getUserAreaByEmployeeId(bean);
        // 只允许删除排班计划不存的用户
        if (userArea == null) {
            // 删除用户信息,只是逻辑删除
            Users user = new Users();
            // 用户ID
            user.setEmployeeId(employeeId);
            // 删除标识
            user.setStatus(2);
            // 删除时间
            user.setUpdateAt(new Date());
            int opinion = usersService.updateUsersByBean(user);
            // 删除成功
            if(opinion > 0){
                map.put("result", ConstantUtil.SUCCESS);
                map.put("information", ConstantUtil.DELETE_SUCCESS_MSG);
            }else{
                map.put("result", ConstantUtil.FAIL);
                map.put("information", ConstantUtil.DELETE_FAIL_MSG);
            }
        } else {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", ConstantUtil.DELETE_USER_FAIL);
        }
        return map;
    }
    
    /**
     * 上传员工图片
     * @param employeeId 员工ID
     * @param muRequest 图片信息
     */
    @RequestMapping(value="/upUserPicture/{employeeId}", method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> uploadPicture(@PathVariable(value="employeeId") Integer employeeId, 
            MultipartHttpServletRequest muRequest, HttpServletRequest request, HttpServletResponse response) {
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();
        // 实现图片的上传
        // 定义上传图片的路径
        if (employeeId == null) {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", "请先填写员工号,在上传图片");
            return map;
        }
        MultipartFile file = muRequest.getFile("fileToUpload");
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
        } catch (IOException e) {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", ConstantUtil.ERROR_MSG);
        }
        String huixian = "public/photo/" + employeeId + "/" + newFile;
        map.put("path", huixian);
        map.put("result", ConstantUtil.SUCCESS);
        return map;
    }
    
    /**
     * 【本地】下载头像
     * @param employeeId 员工ID
     */
    @RequestMapping(value="/downUserPicture/{employeeId}", method=RequestMethod.GET)
    @ResponseBody    
    public Map<String, Object> getLoaclPhoto(@PathVariable(value="employeeId") Integer employeeId, HttpServletRequest request, HttpServletResponse response) {
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();
        List<Long> nameList = new ArrayList<Long>();
        // 获取头像文件路径,本系统中的路径为服务器路径
        String realPath = request.getServletContext().getRealPath("/") + "public/photo/" + employeeId + "/";
        File photoPath = new File(realPath);
        String[] fileName = photoPath.list();
        for (String name : fileName) {
            String subName = name.substring(0, name.lastIndexOf(".")); // 获取当前文件的存放时间值
            nameList.add(Long.parseLong(subName));
        }
        // 对nameList进行排序
        if (nameList.size() > 1) {
            Collections.sort(nameList);
        }
        Long name = nameList.get(nameList.size() - 1);
        String returnFile = name + ".jpg";
        map.put("path", returnFile);
        return map;
    }    
    
    /**
     * 导入员工信息
     */
    @RequestMapping(value="/leadUser", method=RequestMethod.GET)
    @ResponseBody    
    public Map<String, Object> leadExcel(MultipartHttpServletRequest muRequest, 
            HttpServletRequest request, HttpServletResponse response) {
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            MultipartFile file = muRequest.getFile("upload");
            int opinion = usersService.insertLeadUserInfo(file);
            // 全部导入成功
            if(opinion > 0){
                map.put("result", ConstantUtil.SUCCESS);
                map.put("information", ConstantUtil.LEAD_SUCCESS_MSG);
            }else{
                map.put("result", ConstantUtil.FAIL);
                map.put("information", ConstantUtil.LEAD_FAIL_MSG);
            }
        } catch (Exception e) {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", ConstantUtil.ERROR_MSG);
        }
        return map;
    }

}
