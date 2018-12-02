package com.czz.hwy.action.mission.app;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.czz.hwy.bean.mission.app.RelayApp;
import com.czz.hwy.service.mission.app.RelayAppService;
import com.czz.hwy.utils.ConstantUtil;
import com.czz.hwy.utils.JsonDateValueProcessor;

/***
 * 替班人员功能
 * @author 张咏雪
 * @Date 2016-10-20
 */
@Controller
@RequestMapping(value = "/relayAppController")
public class RelayAppController {

	@Autowired
	private RelayAppService relayAppService;//替班业务层
	
	/**
	 * 查询所有的替班人员记录，不分页，2016-11-21
	 * 要求：（1）环卫工请假代班人员查询其所属责任区所有的环卫工（应该不包含自己，）
	 * 		 （2）检测员请假代班人员可查询出自己所属部门的所有检测员（应该不包含自己）
	 * produces:用于解决springMVC返回json数据乱码问题
	 * , produces = "text/json;charset=UTF-8"
	 */
	@RequestMapping(value = "/allRelay", method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
    @ResponseBody
	public String selectAllRelayList(String acceptContent, HttpServletResponse response, HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<String,Object> ();

		try {
			//1.若是请求参数为空，则返回fail
			if(acceptContent == null || "".equals(acceptContent)){
				map.put("result", ConstantUtil.FAIL);
				map.put("information",ConstantUtil.SELECT_FAIL_MSG);
				return map.toString();
			}
			
			// 2.对请求参数解码并转换为替班人员对象
//			acceptContent = CommonUtils.getDecodeParam(acceptContent);
			JSONObject json = JSONObject.fromObject(acceptContent);
			RelayApp relayApp = (RelayApp) JSONObject.toBean(json, RelayApp.class);
			
			//3.查询替班人员记录总条数
			int total = relayAppService.getRelayCountByBean(relayApp);//查询替班人员记录总条数，2016-09-29
			
			//4.查询替班人员记录集合
			List<RelayApp> rows = relayAppService.getAllRelayListByBean(relayApp);//查询替班人员记录集合，2016-09-29，不分页
			
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
	
	/**
	 * 查询所有的替班人员记录，不分页，2016-10-20
	 * produces:用于解决springMVC返回json数据乱码问题
	 */
	/*@RequestMapping(value = "/allRelay", method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
    @ResponseBody
	public String selectAllRelayList(String acceptContent, HttpServletResponse response, HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<String,Object> ();

		try {
			//1.若是请求参数为空，则返回fail
			if(acceptContent == null || "".equals(acceptContent)){
				map.put("result", ConstantUtil.FAIL);
				map.put("information",ConstantUtil.SELECT_FAIL_MSG);
				return map.toString();
			}
			
			// 2.对请求参数解码并转换为替班人员对象
//			acceptContent = CommonUtils.getDecodeParam(acceptContent);
			JSONObject json = JSONObject.fromObject(acceptContent);
			Relay relay = (Relay) JSONObject.toBean(json, Relay.class);
			
			//3.查询替班人员记录总条数
			int total = relayAppService.getRelayCountByBean(relay);//查询替班人员记录总条数，2016-09-29
			
			//4.查询替班人员记录集合
			List<Relay> rows = relayAppService.getAllRelayListByBean(relay);//查询替班人员记录集合，2016-09-29，不分页
			
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
	} */
}
