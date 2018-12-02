package com.czz.hwy.action.manager.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.czz.hwy.bean.manager.app.MessageApp;
import com.czz.hwy.bean.mission.app.WorkPlansApp;
import com.czz.hwy.service.manager.app.MessageAppService;
import com.czz.hwy.service.mission.app.WorkPlansAppService;
import com.czz.hwy.utils.AccessOrigin;
import com.czz.hwy.utils.ConstantUtil;
import com.czz.hwy.utils.JsonDateValueProcessor;

/***
 * 发送通知功能
 * @author 张咏雪
 * @Date 2016-10-21
 */
@Controller
@RequestMapping(value = "/informAppController")
public class InformAppController {

	@Autowired
	private WorkPlansAppService workPlansAppService;//代班业务层
	
    @Autowired
    private MessageAppService messageAppService;	
	
    @Resource
    private AccessOrigin accessOrigin;
	/**
	 * 获取发送通知的收取人列表
	 */
	@RequestMapping(value="/inform/{employeeId}", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
	public String getInform(@PathVariable(value="employeeId") Integer employeeId, HttpServletResponse response, HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<String,Object> ();
        // 定义返回集合list
        List<Object> objs = new ArrayList<Object>();
		try {
		    // 获取发送通知人所负责的责任区
		    List<WorkPlansApp> workPlansApp = workPlansAppService.getDutyAreaByEmployeeId(employeeId);
			for (WorkPlansApp bean : workPlansApp) {
			    Map<String,Object> obj = new HashMap<String,Object>();
			    // 责任区名称
			    obj.put("areaName", bean.getAreaName());
			    // 根据发送通知人获取所管辖的所有员工
			    List<Map<String, Object>> lstMap = workPlansAppService.getEmployeeInfoByDutyAreaId(
			            bean.getAreaId(), bean.getRoleId());
			    obj.put("addInfo", lstMap);
			    objs.add(obj);
			}
			map.put("result", ConstantUtil.SUCCESS);
			map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
			map.put("total", objs.size());
			map.put("rows", objs);
		} catch (Exception e) {
			map.put("result", ConstantUtil.FAIL);
			map.put("information", ConstantUtil.ERROR_MSG);
		}
		// 返回用户信息
		JSONObject jsonobject = JSONObject.fromObject(map);
		return jsonobject.toString();
	} 
	
    /**
     * 发送通知
     */	
	@RequestMapping(value="/inform", method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String sendInform(String acceptContent, HttpServletResponse response, HttpServletRequest request) {
        Map<String,Object> map = new HashMap<String,Object> ();
        
        // 信息判断
        if(acceptContent == null || "".equals(acceptContent)){
            map.put("result", ConstantUtil.FAIL);
            map.put("information",ConstantUtil.SELECT_FAIL_MSG);
            return map.toString();
        }
        JSONObject json = JSONObject.fromObject(acceptContent);
        // 通知标题
        String title = (String) json.get("title");
        // 通知内容
        String content = (String) json.get("content");
        // 接收员工ID集合
        String employeeIds = (String) json.get("employeeIds");
        // 发布通知员工ID
        String pushId = (String) json.get("pushId");
        // 发布通知员工名字
        String pushName = (String) json.get("pushName");
        // 保存接收通知的员工ID
        List<String> lstId = new ArrayList<String>();
        // 将员工ID集合进行拆解，用于发送到具体的员工
        for(String id: employeeIds.split(",")){
            lstId.add(id);
        }
        // 去除重复的ID
        @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
        List<String> newList = new ArrayList(new HashSet(lstId)); 
        
        // 推送消息
        Map<String, Object> topicsInfo = new HashMap<String, Object>();
        // 推送内容
        topicsInfo.put("titleName", content);
        topicsInfo.put("checkTime", "");
        topicsInfo.put("checkAddress", "");
        topicsInfo.put("lat", "");
        topicsInfo.put("lng", "");
        topicsInfo.put("checkId", "");
        topicsInfo.put("evalType", "");
        topicsInfo.put("status", "");
        // 2表示通知
        topicsInfo.put("type", 2);
        
        
        // 消息Bean
        MessageApp message = new MessageApp();
        // 创建者ID
        message.setCreate_id(Integer.parseInt(pushId));
        // 发布人
        message.setPublishName(pushName);
        // 内容
        message.setContent(content);
        // 发布时间
        message.setPublish_time(new Date());
        // 创建时间
        message.setCreate_at(new Date());
        // 发送通知的类型9，这样后台管理系统就可以根据接收类型为个人（9）的查询
        message.setReceive_type(9);
        // 通知接收人ID
        message.setReceive_ids(employeeIds);
        // 主题
        message.setTitle(title);
        try {
            // 推送消息
            messageAppService.sendMessage(topicsInfo, newList, message);
            map.put("result", ConstantUtil.SUCCESS);
            map.put("information", "消息发布成功！");
        } catch (Exception e) {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", "异常错误！");
        }
        
	    // 返回用户信息
	    JSONObject jsonobject = JSONObject.fromObject(map);
	    return jsonobject.toString();
	}
	
    /**
     * 通知历史记录
     */ 
    @RequestMapping(value="/informHistory", method=RequestMethod.POST, produces = "text/json;charset=UTF-8")
    @ResponseBody    
    public String informHistory(String acceptContent, HttpServletResponse response, HttpServletRequest request) {
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();
        // 信息判断
        if(acceptContent == null || "".equals(acceptContent)){
            map.put("result", ConstantUtil.FAIL);
            map.put("information",ConstantUtil.SELECT_FAIL_MSG);
            return map.toString();
        }
        // 通知对象
        JSONObject jsonObject = JSONObject.fromObject(acceptContent);
        // 从页面获取字符串的年月日时，使用JSONObject.toBean时，会默认成系统时间，所以使用此方法
        // 避免获取当前系统时间——原因JSONObject不能识别“yyyy-MM-dd”的格式
        JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[]{"yyyy-MM-dd"}));  
        MessageApp message = (MessageApp) JSONObject.toBean(jsonObject, MessageApp.class);
        // 查询消息信息记录总条数
        int total = messageAppService.getMessageByBeanCount(message);
        // 查询消息信息记录
        List<MessageApp> rows = messageAppService.getMessageByBean(message);
        
        map.put("result", ConstantUtil.SUCCESS);
        map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
        map.put("total", total);
        map.put("rows", rows);
        
        // 对返回值里面包含日期字段进行json格式的过滤
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        // 返回信息
        JSONObject json = JSONObject.fromObject(map, jsonConfig);
        return json.toString();
    }
}
