package com.czz.hwy.action.manager.watch;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.czz.hwy.bean.manager.watch.OneClickAlarmWatch;
import com.czz.hwy.bean.user.watch.Attendances;
import com.czz.hwy.bean.user.watch.Users;
import com.czz.hwy.service.manager.watch.OneClickAlarmWatchService;
import com.czz.hwy.utils.AccessOrigin;
import com.czz.hwy.utils.CommonUtils;
import com.czz.hwy.utils.ConstantUtil;
import com.czz.hwy.utils.MqttSendMessage;

/**
 * 一键报警的action
 * 功能描述
 * @author 张咏雪 2016-5-17
 */
@Controller
@RequestMapping(value = "/oneClickAlarmWatchController")
public class OneClickAlarmWatchController {

	@Autowired
	private OneClickAlarmWatchService oneClickAlarmService;
	
    @Autowired
    private MqttSendMessage mqttSendMessage;
	
    @Resource
    private AccessOrigin accessOrigin;
    
	/**一键报警
	 * 1.验证员工Id：判断该用户是否在职
	 * 2.验证报警经纬度是否存在:若不存在，取根据员工ID最近一次的位置
	 * 3.将报警信息做入库操作，若插入报警信息成功，则推送消息给同一责任区的其他人，若失败，则不推送消息
	 *   3.1若插入报警信息成功： 根据员工ID，查询与其同一个责任区所有人员的topics,封装推送消息内容，推送消息
	 * 	 3.2 若插入失败
	 * */
    @RequestMapping(value="/oneClickAlarmWatch", method = RequestMethod.POST)
    @ResponseBody    	
	public Map<String, Object> getOneClickAlarm(String acceptContent, HttpServletRequest request, 
            HttpServletResponse response){
        // 设置允许跨域访问的路径以及允许认证（可以获取cookie信息）
        CommonUtils.setAccessOrigin(request, response, accessOrigin);
        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();
        // 判断接收参数
        if(acceptContent == null || "".equals(acceptContent)){
            map.put("result", ConstantUtil.FAIL);
            map.put("information", ConstantUtil.ALARM_FAIL);
            return map;
        }
		
		//从请求信息中获取请求字段
		JSONObject jsonObject = JSONObject.fromObject(acceptContent);
		String employeeId = jsonObject.getString("employeeId");//报警员工ID
		String lat = jsonObject.getString("lat");//报警纬度
		String lng = jsonObject.getString("lng");//报警经度
		String address = jsonObject.getString("address");//报警地址
		
		/*1.验证员工ID:判断该用户是否在职*/
		if(employeeId == null || "".equals(employeeId)){
			map.put("result", ConstantUtil.FAIL);
			map.put("information", ConstantUtil.ALARM_FAIL);
			return map;
		}
		
		//根据employeeId，判断是否是在职员工，并返回employeeName
		Users user = oneClickAlarmService.getEmployeeById(employeeId);
		if(user == null){//如果该用户不存在或不在职
			map.put("result", ConstantUtil.FAIL);
			map.put("information", ConstantUtil.ALARM_FAIL);
			return map;
		}
		
		OneClickAlarmWatch oneClickAlarm = new OneClickAlarmWatch();
		//验证用户Id通过
		oneClickAlarm.setEmployeeId(employeeId);
		oneClickAlarm.setEmployeeName(user.getShowname());
		
		/*2.验证报警经纬度是否存在:若不存在，取根据员工ID最近一次的位置*/
		if(lat == null || "".equals(lat) || lng == null || "".equals(lng)){//若经纬度不存在
			Attendances attendances = oneClickAlarmService.selectAttendancesById(employeeId);
			if(attendances == null){
				map.put("result", ConstantUtil.FAIL);
				map.put("information", ConstantUtil.ALARM_FAIL);
				return map;
			}
			lat = String.valueOf(attendances.getLat());
			lng = String.valueOf(attendances.getLng());
			address = String.valueOf(attendances.getAddress());
		}
		oneClickAlarm.setLat(lat);
		oneClickAlarm.setLng(lng);
		oneClickAlarm.setAddress(address);
		
		/*3.将报警信息做入库操作*/
		int result = oneClickAlarmService.insertAlarm(oneClickAlarm);
		
		/*3.1若插入报警信息成功： 根据员工ID，查询与其同一个责任区所有人员的topics,封装推送消息内容，推送消息*/
		if(result == 1){
			List<String> topicsList = oneClickAlarmService.getTopicsListById(employeeId);
			Map<String, Object> topicsInfo = new HashMap<String, Object>();
			topicsInfo.put("titleName", "报警信息");
			topicsInfo.put("checkId", employeeId);
			topicsInfo.put("checkTime", new Date());
			topicsInfo.put("lat", lat);
			topicsInfo.put("lng", lng);
			topicsInfo.put("checkAddress", address);
			topicsInfo.put("status", user.getShowname());
			topicsInfo.put("evalType", null);
			topicsInfo.put("type", 3);
					
			//推送消息
            // mqttSendMessage.sendTopicsMessages(topicsInfo, topicsList);
			
			//返回执行结果
			map.put("result", ConstantUtil.SUCCESS);
			map.put("information", ConstantUtil.ALARM_SUCCESS);
			return map;
		}else{/*3.2 若插入失败*/
			map.put("result", ConstantUtil.FAIL);
			map.put("information", ConstantUtil.ALARM_FAIL);
			return map;
		}
	}
}
