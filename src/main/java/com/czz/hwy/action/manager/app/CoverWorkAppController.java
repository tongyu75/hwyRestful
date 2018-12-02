package com.czz.hwy.action.manager.app;

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

import com.czz.hwy.bean.manager.app.CoverWorkApp;
import com.czz.hwy.service.manager.app.CoverWorkAppService;
import com.czz.hwy.utils.ConstantUtil;
import com.czz.hwy.utils.JsonDateValueProcessor;

/***
 * 代班记录查询功能
 * @author 张咏雪
 * @Date 2016-10-21
 */
@Controller
@RequestMapping(value = "/coverWorkAppController")
public class CoverWorkAppController {

	@Autowired
	private CoverWorkAppService coverWorkAppService;//代班业务层
	
	/**
	 * 环卫工可以查看自己某一段时间内的代班记录，不分页，2016-10-21
	 * produces:用于解决springMVC返回json数据乱码问题
	 */
	@RequestMapping(value = "/coverWork", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
	public String selectCoverWorkList(String acceptContent, HttpServletResponse response, HttpServletRequest request){
		
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
			CoverWorkApp coverWorkApp = (CoverWorkApp) JSONObject.toBean(json, CoverWorkApp.class);
			
			//3.查询自己的某段时间段内的请假记录对应的代班详情总条数
			int total = coverWorkAppService.getCoverWorkCountByBean(coverWorkApp);//查看自己某一段时间内的代班记录总条数，2016-10-21
			
			//4.查询自己的某段时间段内的请假记录对应的代班详情集合
			List<CoverWorkApp> rows = coverWorkAppService.getCoverWorkListByBean(coverWorkApp);//查看自己某一段时间内的代班记录集合，2016-10-21，不分页
			
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
	 * 查询某个责任区某个时间段内的请假记录对应的代班详情，不分页，2016-10-21
	 * produces:用于解决springMVC返回json数据乱码问题
	 */
	@RequestMapping(value = "/allCoverWork", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
	public String selectAllCoverWorkList(String acceptContent, HttpServletResponse response, HttpServletRequest request){
		
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
			CoverWorkApp coverWorkApp = (CoverWorkApp) JSONObject.toBean(json, CoverWorkApp.class);
			
			//注释掉的原因是，检测员也传递责任区ID过来，不用再根据员工ID存在
			/*if(coverWork.getSelectByRoleId() == 2){//如果是检测员，查询出检测员负责的责任区
				int areaId = coverWorkAppService.getAreaIdByBean(coverWork);//查询出检测员负责的责任区,2016-10-21
				coverWork.setDutyAreaId(areaId);
			}*/
			
			
			//3.查询某个责任区某个时间段内的请假记录对应的代班详情总条数
			int total = coverWorkAppService.getAllCoverWorkCountByBean(coverWorkApp);//查询某个责任区某个时间段内的请假记录对应的代班详情总条数，2016-10-21
			
			//4.查询某个责任区某个时间段内的请假记录对应的代班详情集合
			List<CoverWorkApp> rows = coverWorkAppService.getAllCoverWorkListByBean(coverWorkApp);//查询某个责任区某个时间段内的请假记录对应的代班详情集合，2016-10-21，不分页
			
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
