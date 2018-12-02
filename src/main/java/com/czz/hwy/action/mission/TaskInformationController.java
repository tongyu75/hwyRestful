package com.czz.hwy.action.mission;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.czz.hwy.bean.mission.TaskInformation;
import com.czz.hwy.bean.user.Users;
import com.czz.hwy.service.mission.TaskInformationService;
import com.czz.hwy.utils.AccessOrigin;
import com.czz.hwy.utils.CommonUtils;
import com.czz.hwy.utils.ConstantUtil;
import com.czz.hwy.utils.JsonDateValueProcessor;

/***
 * 任务管理功能，用于pc端接口
 * @author 张咏雪
 * @Date 2016-11-14
 */
@Controller
@RequestMapping(value = "/taskInformationController")
public class TaskInformationController {

	@Autowired
	private TaskInformationService taskInformationService;//任务管理业务层
	
	@Resource
    private AccessOrigin accessOrigin;//跨域

	@SuppressWarnings("rawtypes")
    @Autowired
	private RedisTemplate redisTemplate;//缓存服务器
	
	@SuppressWarnings("unchecked")
	public String getUserKey(){
		Users users  = new Users();
		users.setEmployeeId(2015101501);
		 //将用户信息储存到缓存服务器中
        String userKey = CommonUtils.getEncryptByPublicKey("2015101501");
        redisTemplate.opsForValue().set(userKey,users );
        redisTemplate.expire(userKey, 30, TimeUnit.MINUTES);
        return userKey;
	}
	
	/**
	 * 查询任务列表，2016-11-14
	 */
	@RequestMapping(value = "/taskInformation", method = RequestMethod.GET)
    @ResponseBody
	public String selectTaskInformationList(String acceptContent,HttpServletResponse response, HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<String,Object> ();
		// 0.设置允许跨域访问的路径
		CommonUtils.setAccessOrigin(request,response, accessOrigin);
		
		try {
			//1.若是请求参数为空，则返回fail
			if(acceptContent == null || "".equals(acceptContent)){
				map.put("result", ConstantUtil.FAIL);
				map.put("information",ConstantUtil.SELECT_FAIL_MSG);
				return map.toString();
			}
			
			// 2.对请求参数解码并转换为任务对象
			acceptContent = CommonUtils.getDecodeParam(acceptContent);
			JSONObject json = JSONObject.fromObject(acceptContent);
			TaskInformation taskInformation = (TaskInformation) JSONObject.toBean(json, TaskInformation.class);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(json.getString("taskStart") == null || "".equals(json.getString("taskStart"))){
				taskInformation.setTaskStart(null);
			}else{
				taskInformation.setTaskStart(sdf.parse(json.getString("taskStart")));
			}
			if(json.getString("taskEnd") == null || "".equals(json.getString("taskEnd"))){
				taskInformation.setTaskEnd(null);
			}else{
				taskInformation.setTaskEnd(sdf.parse(json.getString("taskEnd")));
			}
			
			//3.查询任务信息记录总条数
			int total = taskInformationService.getTaskInformationCount(taskInformation);//查询任务信息记录总条数，2016-11-14
			
			//4.查询任务信息记录集合，分页
			List<TaskInformation> rows = taskInformationService.getTaskInformationList(taskInformation);//查询任务信息记录集合，2016-11-14，分页
			
			//5.根据查询结果，返回相应数据
			map.put("result", ConstantUtil.SUCCESS);
			map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
			map.put("total", total);
			map.put("rows", rows);
		} catch (Exception e) {
			map.put("result", ConstantUtil.FAIL);
			map.put("information", ConstantUtil.ERROR_MSG);
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		// 返回用户信息
		JSONObject jsonobject = JSONObject.fromObject(map, jsonConfig);
		return jsonobject.toString();
	} 
	
	
}
