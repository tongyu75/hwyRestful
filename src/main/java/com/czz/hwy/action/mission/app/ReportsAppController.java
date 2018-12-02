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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.czz.hwy.bean.mission.app.ReportDutyApp;
import com.czz.hwy.bean.mission.app.ReportInfoApp;
import com.czz.hwy.bean.mission.app.ReportsApp;
import com.czz.hwy.bean.mission.app.ReportsCountApp;
import com.czz.hwy.service.mission.app.ReportDutyAppService;
import com.czz.hwy.service.mission.app.ReportInfoAppService;
import com.czz.hwy.service.mission.app.ReportsAppService;
import com.czz.hwy.service.mission.app.ReportsCountAppService;
import com.czz.hwy.utils.ConstantUtil;
import com.czz.hwy.utils.JsonDateValueProcessor;

/***
 * 监督举报统计控制类
 * @author 张咏雪
 * @Date 2017-04-14
 */
@Controller
@RequestMapping(value = "/reportsAppController")
public class ReportsAppController {
	
	private static final int days = 7;//举报或监察统计天数，目前为近一周
	
	@Autowired
	private ReportsCountAppService reportsCountAppService;//监察举报统计业务层
	
	@Autowired
	private ReportsAppService reportsAppService;//监察举报业务层
	
	@Autowired
	private ReportDutyAppService reportDutyAppService;//监察责任人业务层
	
	@Autowired
	private ReportInfoAppService reportInfoAppService;//新的举报功能业务层
	
	/**
	 * 用于督察员查看近一周全市违规排污举报统计情况。 2017-04-17
	 * produces:用于解决springMVC返回json数据乱码问题
	 */
	@RequestMapping(value = "/reportStatistics", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
	public String selectReportStatistics(HttpServletResponse response, HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<String,Object> ();

		try {
			
			//1.查看近一周全市违规排污举报统计情况
			ReportsCountApp reportsCountApp = new ReportsCountApp();
			reportsCountApp.setDays(days);
			List<ReportsCountApp> rows = reportsCountAppService.selectReportStatisticsByBeanApp(reportsCountApp);//查看近一周全市违规排污举报统计情况， 2017-04-17
			
			//2.根据查询结果，返回相应数据
			map.put("result", ConstantUtil.SUCCESS);
			map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
			map.put("total", rows.size());
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
	 * 用于督察员查看近一周全市违规排污举报人详细信息。 2017-04-17
	 * produces:用于解决springMVC返回json数据乱码问题
	 */
	@RequestMapping(value = "/reportDetails/{evalValue}", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
	public String selectReportDetails(@PathVariable(value="evalValue")int evalValue, HttpServletResponse response, HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<String,Object> ();

		try {
			
			//1.查看近一周全市违规排污举报人详细信息
			ReportInfoApp reportInfoApp = new ReportInfoApp();
			reportInfoApp.setDays(days);
			reportInfoApp.setEvalValue(evalValue);
			List<ReportInfoApp> rows = reportInfoAppService.selectReportInfoListByBeanApp(reportInfoApp);//查看近一周全市违规排污举报人详细信息， 2017-04-27
			
			//2.根据查询结果，返回相应数据
			map.put("result", ConstantUtil.SUCCESS);
			map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
			map.put("total", rows.size());
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
	 * 用于督察员查看近一周全市劳动纪律督察统计情况。 2017-04-17
	 * produces:用于解决springMVC返回json数据乱码问题
	 */
	@RequestMapping(value = "/superviseStatistics", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
	public String selectSuperviseStatistics(HttpServletResponse response, HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<String,Object> ();

		try {
			
			//1.查看近一周全市劳动纪律督察统计情况
			ReportsCountApp reportsCountApp = new ReportsCountApp();
			reportsCountApp.setDays(days);
			List<ReportsCountApp> rows = reportsCountAppService.selectSuperviseStatisticsByBeanApp(reportsCountApp);//查看近一周全市劳动纪律督察统计情况， 2017-04-17
			
			//2.根据查询结果，返回相应数据
			map.put("result", ConstantUtil.SUCCESS);
			map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
			map.put("total", rows.size());
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
	 * 用于督察员查看近一周全市未按规定路线作业细化表，即近一周每种监察分类关于每个责任区不合格统计次数。 2017-04-17
	 * produces:用于解决springMVC返回json数据乱码问题
	 */
	@RequestMapping(value = "/superviseAreaDetails/{evalValue}", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
	public String selectSuperviseAreaDetails(@PathVariable(value="evalValue")int evalValue, HttpServletResponse response, HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<String,Object> ();

		try {
			
			//1.查看近一周全市未按规定路线作业细化表，即近一周每种监察分类关于每个责任区不合格统计次数
			ReportsCountApp reportsCountApp = new ReportsCountApp();
			reportsCountApp.setDays(days);
			reportsCountApp.setEvalValue(evalValue);
			List<ReportsCountApp> rows = reportsCountAppService.selectSuperviseAreaDetailsByBeanApp(reportsCountApp);//查看近一周全市未按规定路线作业细化表，即近一周每种监察分类关于每个责任区不合格统计次数， 2017-04-17
			
			//2.根据查询结果，返回相应数据
			map.put("result", ConstantUtil.SUCCESS);
			map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
			map.put("total", rows.size());
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
	 * 用于督察员查看近一周全市每一项监察下每一个责任区下责任人列表。 2017-04-17
	 * produces:用于解决springMVC返回json数据乱码问题
	 */
	@RequestMapping(value = "/supervisePeopleDetails/{evalValue}/{areaId}", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
	public String selectSupervisePeopleDetails(@PathVariable(value="evalValue")int evalValue, @PathVariable(value="areaId")int areaId, HttpServletResponse response, HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<String,Object> ();

		try {
			
			//1.查看近一周全市每一项监察下每一个责任区下责任人列表
			ReportDutyApp reportDutyApp = new ReportDutyApp();
			reportDutyApp.setDays(days);
			reportDutyApp.setSupervisorType(evalValue);
			reportDutyApp.setAreaId(areaId);
			List<ReportDutyApp> rows = reportDutyAppService.selectSupervisePeopleDetailsByBeanApp(reportDutyApp);//查看近一周全市每一项监察下每一个责任区下责任人列表， 2017-04-17
			
			//2.根据查询结果，返回相应数据
			map.put("result", ConstantUtil.SUCCESS);
			map.put("information", ConstantUtil.SELECT_SUCCESS_MSG);
			map.put("total", rows.size());
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
