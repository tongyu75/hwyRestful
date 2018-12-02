/**
 * 
 */
package com.czz.hwy.action.message;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.czz.hwy.bean.manager.Message;
import com.czz.hwy.bean.user.Users;
import com.czz.hwy.service.manager.MessageService;
import com.czz.hwy.service.usermanagement.UsersService;
import com.czz.hwy.utils.AccessOrigin;
import com.czz.hwy.utils.CommonUtils;
import com.czz.hwy.utils.ConstantUtil;
import com.czz.hwy.utils.JsonDateValueProcessor;

/**
 * 消息管理
 * @author 佟士儒2016-11-02
 */
@Controller
@RequestMapping(value = "/msgManageController")
public class MsgManageController {
    
    @Resource
    private AccessOrigin accessOrigin;    
    
    @SuppressWarnings("rawtypes")
    @Autowired
    private RedisTemplate redisTemplate;
    
    @Autowired
    private MessageService messageService;
    
    @Autowired
    private UsersService usersService;
    
    // 日期format
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    /**
     * 消息查询查询
     * @param acceptContent 查询条件
     */
    @RequestMapping(value="/message", method=RequestMethod.GET)
    @ResponseBody    
    public String showMessages(String acceptContent, HttpServletRequest request, 
            HttpServletResponse response){
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();
        // 判断接收参数
        if(acceptContent == null || "".equals(acceptContent)){
            map.put("result", ConstantUtil.FAIL);
            map.put("information", "查询失败，查询信息不能为空！");
            return map.toString();
        }
        
        // 对请求参数解码并转换为报警信息对象
        acceptContent = CommonUtils.getDecodeParam(acceptContent);
        JSONObject json = JSONObject.fromObject(acceptContent);
        
        // 设置参数
        Message message = new Message();
        // 发布人
        message.setPublishName(json.getString("publishName"));
        // 消息标题
        message.setTitle(json.getString("title"));
        // 消息内容
        message.setContent(json.getString("content"));
        // 接收类型
        String receiveType = json.getString("receive_type");
        if (!StringUtils.isEmpty(receiveType)) {
            message.setReceive_type(Integer.parseInt(receiveType));
        }
        // 消息发布起始时间
        String fTime = json.getString("f_publishTime");
        if (!StringUtils.isEmpty(fTime)) {
            try {
                message.setF_publishTime(sdf.parse(fTime));
            } catch (ParseException e) {
                map.put("result", ConstantUtil.FAIL);
                map.put("information", "异常错误！");
            }
        }
        // 消息发布起始时间
        String tTime = json.getString("t_publishTime");
        if (!StringUtils.isEmpty(tTime)) {
            try {
                message.setT_publishTime(sdf.parse(tTime));
            } catch (ParseException e) {
                map.put("result", ConstantUtil.FAIL);
                map.put("information", "异常错误！");
            }
        }
        // 创建人员工号
        String createId = json.getString("create_id");
        if (!StringUtils.isEmpty(createId)) {
            message.setCreate_id(json.getInt("create_id"));
        }
        // 消息发布起始时间
        String page = json.getString("page");
        if (!StringUtils.isEmpty(page)) {
        	message.setPage(Integer.parseInt(page));
        }
        // 处理时间避免获取当前系统时间——原因JSONObject不能识别“yyyy-MM-dd”的格式  
        // JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[]{"yyyy-MM-dd"}));  
        // Message message = (Message) JSONObject.toBean(json, Message.class);
        
        // 查询消息信息记录总条数
        int total = messageService.getMessageByBeanCount(message);
        // 查询消息信息记录
        List<Message> rows = messageService.getMessageByBean(message);
        
        // 设置返回数据
        map.put("result", ConstantUtil.SUCCESS);
        map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
        map.put("total", total);
        map.put("rows", rows);
        
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        // 返回责任区信息
        JSONObject jsonobject = JSONObject.fromObject(map, jsonConfig);
        return jsonobject.toString();
        
    }
    /**
     * 发布消息
     * @param acceptContent 查询条件
     */
    @RequestMapping(value="/message", method=RequestMethod.POST)
    @ResponseBody    
    public Map<String, Object> beginPublishMsg(String acceptContent, HttpServletRequest request, 
            HttpServletResponse response){
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();
        // 判断接收参数
        if(acceptContent == null || "".equals(acceptContent)){
            map.put("result", ConstantUtil.FAIL);
            map.put("information", "发布失败，发布信息不能为空！");
            return map;
        }
        
        // 对请求参数解码并转换为报警信息对象
        JSONObject json = JSONObject.fromObject(acceptContent);
        Message message = (Message) JSONObject.toBean(json, Message.class);
        message.setPublish_time(new Date());
        // 从缓存中获取用户信息
       String userToken = CommonUtils.getCookieValue(request, "userToken");
        Users user = (Users) redisTemplate.opsForValue().get(userToken);
        // 创建者ID
        message.setCreate_id(user.getEmployeeId());
        // 发布人
        message.setPublishName(user.getShowname());
        //定义接收通知的用户集合
        List<Users> lstUser = new ArrayList<Users>();
        // 当接收类型未9时表示指定个别人
        if(message.getReceive_type() != 20){
            // 根据角色查询
            Users bean = new Users();
            bean.setRoleId(message.getReceive_type());
            // 接收类型为个人的时候，向所有人推送
            if (message.getReceive_type() == 5) {
                bean.setRoleId(0);
            }
            lstUser = usersService.getMessageUsersInfoListByBean(bean);
        }else{
            //由于传回来的就是用户工号字符串，所以减少与数据库的交互
            //m.put("ids", "("+receive_ids+")");
            String[] employeeIds = message.getReceive_ids().split(",");
            for(String idStr:employeeIds){
                Users temp = new Users();
                temp.setEmployeeId(Integer.valueOf(idStr));
                lstUser.add(temp);
            }
        }
            
        // 推送消息
        Map<String, Object> topicsInfo = new HashMap<String, Object>();
        // 推送内容
        topicsInfo.put("titleName", message.getTitle());
        topicsInfo.put("checkTime", "");
        topicsInfo.put("checkAddress", "");
        topicsInfo.put("lat", "");
        topicsInfo.put("lng", "");
        topicsInfo.put("checkId", "");
        topicsInfo.put("evalType", "");
        topicsInfo.put("status", "");
        // 2表示通知
        topicsInfo.put("type", 2);
        
        // 接收人
        List<String> topicsList = new ArrayList<String>();
        for(Users bean : lstUser){
            topicsList.add(String.valueOf(bean.getEmployeeId()));
        }
        try {
            // 推送消息
            messageService.sendMessage(topicsInfo, topicsList, message);
            map.put("result", ConstantUtil.SUCCESS);
            map.put("information", "消息发布成功！");
        } catch (Exception e) {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", "异常错误！");
        }
        return map;
    }
}
