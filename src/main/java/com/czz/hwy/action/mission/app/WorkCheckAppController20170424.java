package com.czz.hwy.action.mission.app;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.czz.hwy.bean.mission.app.CheckGramForSelectApp;
import com.czz.hwy.bean.mission.app.CheckTimeForSelectApp;
import com.czz.hwy.bean.mission.app.EvalTypeApp;
import com.czz.hwy.bean.mission.app.EvalTypeCheckApp;
import com.czz.hwy.bean.mission.app.EvaluationsApp;
import com.czz.hwy.bean.mission.app.ReportsApp;
import com.czz.hwy.bean.mission.app.WorkInfoApp;
import com.czz.hwy.bean.mission.app.WorksForHwyApp;
import com.czz.hwy.service.manager.app.LeaveAppService;
import com.czz.hwy.service.mission.app.CheckDutyAppService;
import com.czz.hwy.service.mission.app.CheckGramAppService;
import com.czz.hwy.service.mission.app.CheckTimeAppService;
import com.czz.hwy.service.mission.app.EvalTypeAppService;
import com.czz.hwy.service.mission.app.EvalTypeCheckAppService;
import com.czz.hwy.service.mission.app.EvaluationsAppService;
import com.czz.hwy.service.mission.app.QualifiedNumberAppService;
import com.czz.hwy.service.mission.app.ReportsAppService;
import com.czz.hwy.service.mission.app.TaskInformationAppService;
import com.czz.hwy.service.mission.app.WorkCheckAppService;
import com.czz.hwy.service.mission.app.WorkPlansAppService;
import com.czz.hwy.utils.ConstantUtil;
import com.czz.hwy.utils.JsonDateValueProcessor;
import com.czz.hwy.utils.MqttSendMessage;

/***
 * 任务考核项目功能，用于app接口
 * 五克，五分钟，监察，举报
 * @author 张咏雪
 * @Date 2016-11-07
 * 2017-04-27 修改举报逻辑，使举报可以提交，处理
 */
@Controller
@RequestMapping(value = "/workCheckAppController20170424")
public class WorkCheckAppController20170424 {

	@Autowired
	private WorkCheckAppService workCheckAppService;//考核任务业务层
	
	@Autowired
	private CheckTimeAppService checkTimeAppService;//五分钟考核业务层
	
	@Autowired
	private CheckDutyAppService checkDutyAppService;//考核项目对应责任人业务层
	
	@Autowired
	private WorkPlansAppService workPlansAppService;//排班计划业务层
	
	@Autowired
	private LeaveAppService leaveAppService;//请假业务层
	
	@Autowired
	private TaskInformationAppService taskInformationAppService;//工作调度业务层（任务业务层）
	
	@Autowired
	private WorksForHwyApp worksForHwyApp;//用于配置环卫云app考核中使用的参数
	
	@Autowired
	private QualifiedNumberAppService qualifiedNumberAppService;//统计每日提交五克五分钟总次数
	
	@Autowired
	private EvalTypeAppService evalTypeAppService;//举报考核分类业务层
	
	@Autowired
	private EvalTypeCheckAppService evalTypeCheckAppService;//五克，五分钟，监察考核分类业务层
	
	@Autowired
	private MqttSendMessage mqttSendMessage;//消息推送业务层
	
	@Autowired
	private EvaluationsAppService evaluationsAppService;//考核标准业务层
	
	@Autowired
	private CheckGramAppService checkGramAppService;//五克检测业务层
	
	@Autowired
	private ReportsAppService reportsAppService;//监察举报业务层
	
	// 日期格式化
    private DateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
	
	/**
	 * 新增五分钟考核记录，2016-11-08
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	@RequestMapping(value = "/checkTime", method = RequestMethod.POST)
    @ResponseBody
	public Map<String,Object> addCheckTime(String acceptContent, HttpServletResponse response, HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<String,Object> ();
		
		//1.若是请求参数为空，则返回fail
 		if(acceptContent == null || "".equals(acceptContent)){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","提交失败，五分钟考核记录信息不能为空！");
			return map;
		}
		
		//2.若请求不为空，则将请求参数转换为五分钟考核项目接收信息对象
		JSONObject json = JSONObject.fromObject(acceptContent);
		WorkInfoApp workInfoApp = (WorkInfoApp) JSONObject.toBean(json, WorkInfoApp.class);
		
		//解析责任人列表
		String dutyPeopleArr = json.getString("dutyPeopleList");
		JSONArray jsonArray = JSONArray.fromObject(dutyPeopleArr);
		List<JSONObject> dutyPeopleJSON = new ArrayList<JSONObject>();
		if(dutyPeopleArr != null && !"".equals(dutyPeopleArr)){
			dutyPeopleJSON = jsonArray.toList(jsonArray, JSONObject.class);
		}
		
		if(dutyPeopleJSON.size() == 0){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","提交失败:责任人不能为空！");
			return map;
		}
		
		List<Map<String, Object>> dutyPeopleList = new ArrayList<Map<String,Object>>();//用于保存责任人列表
		List<String> dutyUserIdList = new ArrayList<String>();//责任人ID列表
		for(JSONObject j : dutyPeopleJSON){//获取责任人列表
			Map<String, Object> dutyMap = new HashMap<String, Object>();
			dutyMap.put("employeeId", j.getString("employeeId").toString());
			dutyMap.put("employeeName", j.getString("employeeName").toString());
			dutyPeopleList.add(dutyMap);
			dutyUserIdList.add(j.getString("employeeId").toString());
		}
		workInfoApp.setDutyPeopleList(dutyPeopleList);
		
		//3.判断请求参数是否为空
		if(workInfoApp.getEmployeeId() == 0){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","提交失败:提交人不能为空！");
			return map;
		}
		
		if(workInfoApp.getAreaId() == 0){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","提交失败:责任区不能为空！");
			return map;
		}
		
		if(workInfoApp.getPointId() == 0){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","提交失败:责任点不能为空！");
			return map;
		}
		
		if(workInfoApp.getEvalType() == 0){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","提交失败:考核类型不能为空！");
			return map;
		}
		
		EvalTypeCheckApp evalTypeCheckApp = new EvalTypeCheckApp();
		evalTypeCheckApp.setEvalValue(workInfoApp.getEvalType());
		evalTypeCheckApp = evalTypeCheckAppService.getEvalTypeCheckByBeanApp(evalTypeCheckApp);//根据考核类型ID获取考核类型详细信息，2016-12-20
		if(evalTypeCheckApp == null){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","提交失败:该考核类型不存在！");
			return map;
		}
		
		if(workInfoApp.getCheckAddress() == null || "".equals(workInfoApp.getCheckAddress())){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","提交失败:考核地址不能为空！");
			return map;
		}
		
		if(json.getString("checkTime") == null ){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","提交失败:考核时间不能为空！");
			return map;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			workInfoApp.setCheckTime(sdf.parse(json.getString("checkTime")));
		} catch (ParseException e) {
			map.put("result", ConstantUtil.FAIL);
			map.put("information", "提交失败:考核时间格式不正确！");
			return map;
		}
		
		if(workInfoApp.getCheckLat() == null || "".equals(workInfoApp.getCheckLat())){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","提交失败:考核纬度不能为空！");
			return map;
		}
		
		if(workInfoApp.getCheckLng() == null || "".equals(workInfoApp.getCheckLng())){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","提交失败:考核经度不能为空！");
			return map;
		}
		
		//获取该责任区责任点的考核标准值
		EvaluationsApp evaluationsApp = new EvaluationsApp();
		evaluationsApp.setAreaId(workInfoApp.getAreaId());
//		evaluationsApp.setPointId(workInfoApp.getPointId());
		evaluationsApp.setEvalType(workInfoApp.getEvalType());
		//五分钟只针对责任区，不针对责任点
		evaluationsApp = evaluationsAppService.getEvaluationsByBeanApp(evaluationsApp);//根据责任区Id，责任点ID和考核类型值，获取考核标准信息。2016-12-20
		
		/*if(evaluationsApp == null){
			evaluationsApp = new EvaluationsApp();
			evaluationsApp.setEvalType(evalTypeCheckApp.getEvalValue());
			evaluationsApp.setEvalName(evalTypeCheckApp.getEvalName());
		}*/
		
		if(evaluationsApp == null){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","提交失败:该责任区的该考核类型不存在，请先维护考核类型！");
			return map;
		}
		
		//4.进行五分钟任务新增，五分钟任务责任人新增，五分钟统计次数更新，五分钟罚款，五分钟任务责任人推送
		map = workCheckAppService.addCheckTime(workInfoApp, map, dutyUserIdList, evalTypeCheckApp, evaluationsApp);
		
		if(map.size() == 0){//如果map为空
			map.put("result", ConstantUtil.FAIL);
			map.put("information","提交失败！");
		}
		//5.返回消息
		return map;
	} 
	
	/**
	 * 查询某个责任区某个时间段内的五分钟考核记录，不分页，2016-11-09
	 * produces:用于解决springMVC返回json数据乱码问题
	 */
	@RequestMapping(value = "/checkTime", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
	public String selectAllCheckTimeList(String acceptContent, HttpServletResponse response, HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<String,Object> ();

		try {
			//1.若是请求参数为空，则返回fail
			if(acceptContent == null || "".equals(acceptContent)){
				map.put("result", ConstantUtil.FAIL);
				map.put("information",ConstantUtil.SELECT_FAIL_MSG);
				return map.toString();
			}
			
			// 2.对请求参数进行解析
//			acceptContent = CommonUtils.getDecodeParam(acceptContent);
			JSONObject json = JSONObject.fromObject(acceptContent);
			Map<String, Object> selectMap = new HashMap<String, Object>();
			selectMap.put("areaId", json.getString("areaId"));
			selectMap.put("beginDate", json.getString("beginDate"));
			selectMap.put("endDate", json.getString("endDate"));
			
			//3.查询某个责任区某个时间段内的五分钟考核总条数
			int total = checkTimeAppService.getCheckTimeCountByMapApp(selectMap);//查询某个责任区某个时间段内的五分钟考核总条数，2016-11-09
			
			//4.查询某个责任区某个时间段内的五分钟考核集合
			List<CheckTimeForSelectApp> rows = checkTimeAppService.getCheckTimeListByMapApp(selectMap);//查询某个责任区某个时间段内的五分钟考核集合，2016-11-09，不分页
			
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
	 * 新增五克记录，2016-12-19
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	@RequestMapping(value = "/checkGram", method = RequestMethod.POST)
    @ResponseBody
	public Map<String,Object> addCheckGram(String acceptContent, HttpServletResponse response, HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<String,Object> ();
		
		//1.若是请求参数为空，则返回fail
		if(acceptContent == null || "".equals(acceptContent)){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","提交失败，五克考核记录信息不能为空！");
			return map;
		}
		
		//2.若请求不为空，则将请求参数转换为五分钟考核项目接收信息对象
		JSONObject json = JSONObject.fromObject(acceptContent);
		WorkInfoApp workInfoApp = (WorkInfoApp) JSONObject.toBean(json, WorkInfoApp.class);
		
		//解析责任人列表
		String dutyPeopleArr = json.getString("dutyPeopleList");
		JSONArray jsonArray = JSONArray.fromObject(dutyPeopleArr);
		List<JSONObject> dutyPeopleJSON = new ArrayList<JSONObject>();
		if(dutyPeopleArr != null && !"".equals(dutyPeopleArr)){
			dutyPeopleJSON = jsonArray.toList(jsonArray, JSONObject.class);
		}
		
		if(dutyPeopleJSON.size() == 0){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","提交失败:责任人不能为空！");
			return map;
		}
		
		List<Map<String, Object>> dutyPeopleList = new ArrayList<Map<String,Object>>();//用于保存责任人列表
		List<String> dutyUserIdList = new ArrayList<String>();//责任人ID列表
		for(JSONObject j : dutyPeopleJSON){//获取责任人列表
			Map<String, Object> dutyMap = new HashMap<String, Object>();
			dutyMap.put("employeeId", j.getString("employeeId").toString());
			dutyMap.put("employeeName", j.getString("employeeName").toString());
			dutyPeopleList.add(dutyMap);
			dutyUserIdList.add(j.getString("employeeId").toString());
		}
		workInfoApp.setDutyPeopleList(dutyPeopleList);
		
		//3.判断请求参数是否为空
		if(workInfoApp.getEmployeeId() == 0){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","提交失败:提交人不能为空！");
			return map;
		}
		
		if(workInfoApp.getAreaId() == 0){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","提交失败:责任区不能为空！");
			return map;
		}
		
		if(workInfoApp.getPointId() == 0){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","提交失败:责任点不能为空！");
			return map;
		}
		
		if(workInfoApp.getEvalType() == 0){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","提交失败:考核类型不能为空！");
			return map;
		}
		
		EvalTypeCheckApp evalTypeCheckApp = new EvalTypeCheckApp();
		evalTypeCheckApp.setEvalValue(workInfoApp.getEvalType());
		evalTypeCheckApp = evalTypeCheckAppService.getEvalTypeCheckByBeanApp(evalTypeCheckApp);//根据考核类型ID获取考核类型详细信息，2016-12-20
		if(evalTypeCheckApp == null){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","提交失败:该考核类型不存在！");
			return map;
		}
		
		if(workInfoApp.getCheckAddress() == null || "".equals(workInfoApp.getCheckAddress())){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","提交失败:考核地址不能为空！");
			return map;
		}
		
		if(json.getString("checkTime") == null ){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","提交失败:考核时间不能为空！");
			return map;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			workInfoApp.setCheckTime(sdf.parse(json.getString("checkTime")));
		} catch (ParseException e) {
			map.put("result", ConstantUtil.FAIL);
			map.put("information", "提交失败:考核时间格式不正确！");
			return map;
		}
		
		if(workInfoApp.getCheckLat() == null || "".equals(workInfoApp.getCheckLat())){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","提交失败:考核纬度不能为空！");
			return map;
		}
		
		if(workInfoApp.getCheckLng() == null || "".equals(workInfoApp.getCheckLng())){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","提交失败:考核经度不能为空！");
			return map;
		}
		
		if(workInfoApp.getCheckValue() == null || "".equals(workInfoApp.getCheckLng())){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","提交失败:浮尘重量不能为空！");
			return map;
		}
		
		double checkValue = Double.parseDouble(workInfoApp.getCheckValue());
		if(checkValue < 0){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","提交失败:浮尘重量不能为负数！");
			return map;
		}
		//获取该责任区责任点的五克检测限定值，从而判定是否合格
		//若是不合格是否应该进行罚款
		EvaluationsApp evaluationsApp = new EvaluationsApp();
		evaluationsApp.setAreaId(workInfoApp.getAreaId());
		evaluationsApp.setPointId(workInfoApp.getPointId());
		evaluationsApp.setEvalType(workInfoApp.getEvalType());
		evaluationsApp = evaluationsAppService.getEvaluationsByBeanApp(evaluationsApp);//根据责任区Id，责任点ID和考核类型值，获取考核标准信息。2016-12-20
		
		if(evaluationsApp == null){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","提交失败:该责任区责任点的该考核类型不存在，请先维护考核类型！");
			return map;
		}else if(evaluationsApp.getLimitValue() == null){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","提交失败:该责任区责任点的该考核类型的限定值不存在，请先维护考核类型的限定值！");
			return map;
		}
		
		if(evaluationsApp.getLimitValue() >= checkValue){//如果限定值大于检测值，说明该五克检测记录合格，否则不合格
			workInfoApp.setCheckStatus(1);
		}else{
			workInfoApp.setCheckStatus(2);
		}
		
		//4.进行五克任务新增，五克任务责任人新增，五克统计次数更新，五克罚款，五克任务责任人推送
		map = workCheckAppService.addCheckGram(workInfoApp, map, dutyUserIdList, evalTypeCheckApp, evaluationsApp);
		
		if(map.size() == 0){//如果map为空
			map.put("result", ConstantUtil.FAIL);
			map.put("information","提交失败！");
		}
		//5.返回消息
		return map;
	} 
	
	/**
	 * 查询某个责任区某个时间段内的五克考核记录，不分页，2016-12-22
	 * produces:用于解决springMVC返回json数据乱码问题
	 */
	@RequestMapping(value = "/checkGram", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
	public String selectAllCheckGramList(String acceptContent, HttpServletResponse response, HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<String,Object> ();

		try {
			//1.若是请求参数为空，则返回fail
			if(acceptContent == null || "".equals(acceptContent)){
				map.put("result", ConstantUtil.FAIL);
				map.put("information",ConstantUtil.SELECT_FAIL_MSG);
				return map.toString();
			}
			
			// 2.对请求参数进行解析
//			acceptContent = CommonUtils.getDecodeParam(acceptContent);
			JSONObject json = JSONObject.fromObject(acceptContent);
			Map<String, Object> selectMap = new HashMap<String, Object>();
			selectMap.put("areaId", json.getString("areaId"));
			selectMap.put("beginDate", json.getString("beginDate"));
			selectMap.put("endDate", json.getString("endDate"));
			
			//3.查询某个责任区某个时间段内的五克考核总条数
			int total = checkGramAppService.getCheckGramCountByMapApp(selectMap);//查询某个责任区某个时间段内的五克考核总条数，2016-12-22
			
			//4.查询某个责任区某个时间段内的五克考核集合
			List<CheckGramForSelectApp> rows = checkGramAppService.getCheckGramListByMapApp(selectMap);//查询某个责任区某个时间段内的五克考核集合，2016-12-22，不分页
			
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
	 * 新增监察记录，2016-12-22
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	@RequestMapping(value = "/supervise", method = RequestMethod.POST)
    @ResponseBody
	public Map<String,Object> addSupervise(String acceptContent, HttpServletResponse response, HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<String,Object> ();
		
		//1.若是请求参数为空，则返回fail
		if(acceptContent == null || "".equals(acceptContent)){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","提交失败，监察考核记录信息不能为空！");
			return map;
		}
		
		//2.若请求不为空，则将请求参数转换为五分钟考核项目接收信息对象
		JSONObject json = JSONObject.fromObject(acceptContent);
		WorkInfoApp workInfoApp = (WorkInfoApp) JSONObject.toBean(json, WorkInfoApp.class);
		
		//解析责任人列表
		String dutyPeopleArr = json.getString("dutyPeopleList");
		JSONArray jsonArray = JSONArray.fromObject(dutyPeopleArr);
		List<JSONObject> dutyPeopleJSON = new ArrayList<JSONObject>();
		if(dutyPeopleArr != null && !"".equals(dutyPeopleArr)){
			dutyPeopleJSON = jsonArray.toList(jsonArray, JSONObject.class);
		}
		
		if(dutyPeopleJSON.size() == 0){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","提交失败:责任人不能为空！");
			return map;
		}
		
		List<Map<String, Object>> dutyPeopleList = new ArrayList<Map<String,Object>>();//用于保存责任人列表
		List<String> dutyUserIdList = new ArrayList<String>();//责任人ID列表
		for(JSONObject j : dutyPeopleJSON){//获取责任人列表
			Map<String, Object> dutyMap = new HashMap<String, Object>();
			dutyMap.put("employeeId", j.getString("employeeId").toString());
			dutyMap.put("employeeName", j.getString("employeeName").toString());
			dutyPeopleList.add(dutyMap);
			dutyUserIdList.add(j.getString("employeeId").toString());
		}
		workInfoApp.setDutyPeopleList(dutyPeopleList);
		
		//3.判断请求参数是否为空
		if(workInfoApp.getEmployeeId() == 0){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","提交失败:提交人不能为空！");
			return map;
		}
		
		if(workInfoApp.getAreaId() == 0){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","提交失败:责任区不能为空！");
			return map;
		}
		
		if(workInfoApp.getPointId() == 0){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","提交失败:责任点不能为空！");
			return map;
		}
		
		if(workInfoApp.getEvalType() == 0){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","提交失败:考核类型不能为空！");
			return map;
		}
		
		EvalTypeCheckApp evalTypeCheckApp = new EvalTypeCheckApp();
		evalTypeCheckApp.setEvalValue(workInfoApp.getEvalType());
		evalTypeCheckApp = evalTypeCheckAppService.getEvalTypeCheckByBeanApp(evalTypeCheckApp);//根据考核类型ID获取考核类型详细信息，2016-12-20
		if(evalTypeCheckApp == null){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","提交失败:该考核类型不存在！");
			return map;
		}
		
		if(workInfoApp.getCheckAddress() == null || "".equals(workInfoApp.getCheckAddress())){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","提交失败:考核地址不能为空！");
			return map;
		}
		
		if(json.getString("checkTime") == null ){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","提交失败:考核时间不能为空！");
			return map;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			workInfoApp.setCheckTime(sdf.parse(json.getString("checkTime")));
		} catch (ParseException e) {
			map.put("result", ConstantUtil.FAIL);
			map.put("information", "提交失败:考核时间格式不正确！");
			return map;
		}
		
		if(workInfoApp.getCheckLat() == null || "".equals(workInfoApp.getCheckLat())){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","提交失败:考核纬度不能为空！");
			return map;
		}
		
		if(workInfoApp.getCheckLng() == null || "".equals(workInfoApp.getCheckLng())){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","提交失败:考核经度不能为空！");
			return map;
		}
		
		EvaluationsApp evaluationsApp = new EvaluationsApp();
		evaluationsApp.setAreaId(workInfoApp.getAreaId());
//		evaluationsApp.setPointId(workInfoApp.getPointId());
		evaluationsApp.setEvalType(workInfoApp.getEvalType());
		evaluationsApp = evaluationsAppService.getEvaluationsByBeanApp(evaluationsApp);//根据责任区Id，责任点ID和考核类型值，获取考核标准信息。2016-12-20
		/*if(evaluationsApp == null){
			evaluationsApp = new EvaluationsApp();
			evaluationsApp.setEvalType(evalTypeCheckApp.getEvalValue());
			evaluationsApp.setEvalName(evalTypeCheckApp.getEvalName());
		}*/
		
		if(evaluationsApp == null){//没有对这个责任区授权这个考核类型
			map.put("result", ConstantUtil.FAIL);
			map.put("information","提交失败:该责任区的该考核类型不存在，请先维护考核类型！");
			return map;
		}
		
		
		//4.进行监察任务新增，监察任务责任人新增，监察统计次数更新，监察罚款，监察任务责任人推送
		map = workCheckAppService.addSupervise(workInfoApp, map, dutyUserIdList, evalTypeCheckApp, evaluationsApp);
		
		if(map.size() == 0){//如果map为空
			map.put("result", ConstantUtil.FAIL);
			map.put("information","提交失败！");
		}
		//5.返回消息
		return map;
	} 
	
	/**
	 * 查询某个责任区某个时间段内的监察考核记录，不分页，2016-12-22
	 * produces:用于解决springMVC返回json数据乱码问题
	 */
	@RequestMapping(value = "/supervise", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
	public String selectAllSuperviseList(String acceptContent, HttpServletResponse response, HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<String,Object> ();

		try {
			//1.若是请求参数为空，则返回fail
			if(acceptContent == null || "".equals(acceptContent)){
				map.put("result", ConstantUtil.FAIL);
				map.put("information",ConstantUtil.SELECT_FAIL_MSG);
				return map.toString();
			}
			
			// 2.对请求参数进行解析
//			acceptContent = CommonUtils.getDecodeParam(acceptContent);
			JSONObject json = JSONObject.fromObject(acceptContent);
			ReportsApp reportsApp = (ReportsApp) JSONObject.toBean(json, ReportsApp.class);
			if(reportsApp == null) {
				map.put("result", ConstantUtil.FAIL);
				map.put("information",ConstantUtil.SELECT_FAIL_MSG);
				return map.toString();
	        }
		    try {
		    	reportsApp.setStartTimeDate(sdf.parse(reportsApp.getStartTimeStr() + " 00:00:00"));
		    	reportsApp.setEndTimeDate(sdf.parse(reportsApp.getEndTimeStr() + " 23:59:59"));
	        } catch (ParseException e) {
	        	map.put("result", ConstantUtil.FAIL);
				map.put("information", ConstantUtil.ERROR_MSG);
				return map.toString();
	        }
			
			//3.查询某个责任区某个时间段内的监察考核总条数
//			int total = reportsAppService.getReportsCountByMapApp(reportsApp);//查询某个责任区某个时间段内的监察考核总条数，2016-12-23
			
			//4.查询某个责任区某个时间段内的监察考核集合
			List<ReportsApp> rows = reportsAppService.getSuperviseListByBeanApp(reportsApp);//查询某个责任区某个时间段内的监察考核集合，2016-12-23，不分页
			
			//5.根据查询结果，返回相应数据
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
	 * 新增举报记录，2016-12-23
	 */
	@RequestMapping(value = "/reports", method = RequestMethod.POST)
    @ResponseBody
	public Map<String,Object> addReports(String acceptContent, HttpServletResponse response, HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<String,Object> ();
		
		//1.若是请求参数为空，则返回fail
		if(acceptContent == null || "".equals(acceptContent)){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","提交失败，举报记录信息不能为空！");
			return map;
		}
		
		//2.若请求不为空，则将请求参数转换为五分钟考核项目接收信息对象
		JSONObject json = JSONObject.fromObject(acceptContent);
		WorkInfoApp workInfoApp = (WorkInfoApp) JSONObject.toBean(json, WorkInfoApp.class);
		
		//3.判断请求参数是否为空
		if(workInfoApp.getEmployeeId() == 0){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","提交失败:提交人不能为空！");
			return map;
		}
		
		if(workInfoApp.getEvalType() == 0){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","提交失败:考核类型不能为空！");
			return map;
		}
		
		EvalTypeApp evalTypeApp = new EvalTypeApp();
		evalTypeApp.setEvalValue(workInfoApp.getEvalType());
		evalTypeApp = evalTypeAppService.getEvalTypeByBeanApp(evalTypeApp);//根据考核类型ID获取举报考核类型详细信息
		if(evalTypeApp == null){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","提交失败:该考核类型不存在！");
			return map;
		}
		
		if(workInfoApp.getCheckAddress() == null || "".equals(workInfoApp.getCheckAddress())){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","提交失败:考核地址不能为空！");
			return map;
		}
		
		if(json.getString("checkTime") == null ){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","提交失败:考核时间不能为空！");
			return map;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			workInfoApp.setCheckTime(sdf.parse(json.getString("checkTime")));
		} catch (ParseException e) {
			map.put("result", ConstantUtil.FAIL);
			map.put("information", "提交失败:考核时间格式不正确！");
			return map;
		}
		
		if(workInfoApp.getCheckLat() == null || "".equals(workInfoApp.getCheckLat())){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","提交失败:考核纬度不能为空！");
			return map;
		}
		
		if(workInfoApp.getCheckLng() == null || "".equals(workInfoApp.getCheckLng())){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","提交失败:考核经度不能为空！");
			return map;
		}
		
		//4.进行举报任务新增
		map = workCheckAppService.addReports(workInfoApp, map);
		
		if(map.size() == 0){//如果map为空
			map.put("result", ConstantUtil.FAIL);
			map.put("information","提交失败！");
		}
		//5.返回消息
		return map;
	} 
	
	/**
	 * 查询某个时间段内的自己提交的举报记录，不分页，2016-12-23
	 * produces:用于解决springMVC返回json数据乱码问题
	 */
	@RequestMapping(value = "/reports", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
	public String selectAllReportsList(String acceptContent, HttpServletResponse response, HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<String,Object> ();

		try {
			//1.若是请求参数为空，则返回fail
			if(acceptContent == null || "".equals(acceptContent)){
				map.put("result", ConstantUtil.FAIL);
				map.put("information",ConstantUtil.SELECT_FAIL_MSG);
				return map.toString();
			}
			
			// 2.对请求参数进行解析
//			acceptContent = CommonUtils.getDecodeParam(acceptContent);
			JSONObject json = JSONObject.fromObject(acceptContent);
			ReportsApp reportsApp = (ReportsApp) JSONObject.toBean(json, ReportsApp.class);
			if(reportsApp == null) {
				map.put("result", ConstantUtil.FAIL);
				map.put("information",ConstantUtil.SELECT_FAIL_MSG);
				return map.toString();
	        }
		    try {
		    	reportsApp.setStartTimeDate(sdf.parse(reportsApp.getStartTimeStr() + " 00:00:00"));
		    	reportsApp.setEndTimeDate(sdf.parse(reportsApp.getEndTimeStr() + " 23:59:59"));
	        } catch (ParseException e) {
	        	map.put("result", ConstantUtil.FAIL);
				map.put("information", ConstantUtil.ERROR_MSG);
				return map.toString();
	        }
			
			//3.查询某个时间段内的自己提交的举报记录集合
			List<ReportsApp> rows = reportsAppService.getReportsListByBeanApp(reportsApp);//查询某个时间段内的自己提交的举报记录集合，2016-12-23，不分页
			
			//5.根据查询结果，返回相应数据
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
	 * 新增五分钟考核记录，2016-11-08
	 */
	/*@RequestMapping(value = "/checkTime", method = RequestMethod.POST)
    @ResponseBody
	public Map<String,Object> addCheckTime(String acceptContent, HttpServletResponse response, HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<String,Object> ();
		try {
			//1.若是请求参数为空，则返回fail
			if(acceptContent == null || "".equals(acceptContent)){
				map.put("result", ConstantUtil.FAIL);
				map.put("information","提交失败，五分钟考核记录信息不能为空！");
				return map;
			}
			
			//2.若请求不为空，则将请求参数转换为五分钟考核项目接收信息对象
			JSONObject json = JSONObject.fromObject(acceptContent);
			WorkInfoApp workInfoApp = (WorkInfoApp) JSONObject.toBean(json, WorkInfoApp.class);
			
			//3.判断请求参数是否为空
			if(workInfoApp.getEmployeeId() == 0){
				map.put("result", ConstantUtil.FAIL);
				map.put("information","提交失败:提交人不能为空！");
				return map;
			}
			
			if(workInfoApp.getAreaId() == 0){
				map.put("result", ConstantUtil.FAIL);
				map.put("information","提交失败:责任区不能为空！");
				return map;
			}
			
			if(workInfoApp.getPointId() == 0){
				map.put("result", ConstantUtil.FAIL);
				map.put("information","提交失败:责任点不能为空！");
				return map;
			}
			
			if(workInfoApp.getEvalType() == 0){
				map.put("result", ConstantUtil.FAIL);
				map.put("information","提交失败:考核类型不能为空！");
				return map;
			}
			
			EvalTypeApp evalTypeApp = new EvalTypeApp();
			evalTypeApp.setEvalValue(workInfoApp.getEvalType());
			evalTypeApp = evalTypeAppService.getEvalTypeByBeanApp(evalTypeApp);//根据考核类型ID获取考核类型详细信息，2016-11-09
			if(evalTypeApp == null){
				map.put("result", ConstantUtil.FAIL);
				map.put("information","提交失败:该考核类型不存在！");
				return map;
			}
			
			if(workInfoApp.getCheckAddress() == null || "".equals(workInfoApp.getCheckAddress())){
				map.put("result", ConstantUtil.FAIL);
				map.put("information","提交失败:考核地址不能为空！");
				return map;
			}
			
			if(json.getString("checkTime") == null ){
				map.put("result", ConstantUtil.FAIL);
				map.put("information","提交失败:考核时间不能为空！");
				return map;
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			workInfoApp.setCheckTime(sdf.parse(json.getString("checkTime")));
			
			if(workInfoApp.getCheckLat() == null || "".equals(workInfoApp.getCheckLat())){
				map.put("result", ConstantUtil.FAIL);
				map.put("information","提交失败:考核纬度不能为空！");
				return map;
			}
			
			if(workInfoApp.getCheckLng() == null || "".equals(workInfoApp.getCheckLng())){
				map.put("result", ConstantUtil.FAIL);
				map.put("information","提交失败:考核经度不能为空！");
				return map;
			}
			
			//4.查询出某责任区，责任点下正在上班的员工列表，目前只查询环卫工
			//TODO 查询出某责任区，责任点下正在上班的员工列表，目前只查询环卫工,之后是否会查询检测员，考核员，不确定
			List<WorkPlansApp> workPlansList = workPlansAppService.getCurWorkPlansListByBeanApp(workInfoApp);//获取某个责任区责任点下正在上班的员工列表，2016-11-08
			if(workPlansList.size() == 0){
				map.put("result", ConstantUtil.FAIL);
				map.put("information","提交失败:该责任点当前时间没有责任人，不允许进行考核！");
				return map;
			}
			
			//5.循环责任人列表，依次判断责任人是否请假，是否出任务，
			//比较规则： (1)如果有请假，则判断是否有代班信息，（1.1）若是有代班信息，责任人换成代班人，（1.2）若无代班信息，则去除该责任人
			//          (2)如果无请假，则判断是否有任务，（2.1）若是出任务中，则去除该责任人，（2.2）若是无任务，则保持该责任人
			
			List<String> dutyUserIdList = new ArrayList<String>();//责任人ID列表
			LeaveApp leaveApp = new LeaveApp();
			for(WorkPlansApp workPlansApp : workPlansList){
				leaveApp.setApplyId(workPlansApp.getEmployeeId());
				leaveApp.setTimeForSelect(workInfoApp.getCheckTime());
				List<LeaveApp> leaveList = leaveAppService.getLeaveListByBeanApp(leaveApp);//获取某个人包含某个时间的请假记录，2016-11-08
				if(leaveList.size() > 0){//如果有请假
					//取第一个请假信息中的代班人ID
					int relayId = leaveList.get(0).getRelayId();
					if(relayId != 0){//如果存在代班人，则责任人就是 代班人
						dutyUserIdList.add(relayId + "");
					}else{//如果不存在代班人，则不对该责任人进行考核
						break;
					}
				}else{//如果无请假
					TaskInformationApp  taskInformationApp = new TaskInformationApp();
					taskInformationApp.setTimeForSelect(workInfoApp.getCheckTime());
					taskInformationApp.setTaskDutyPeople("工号:" + workPlansApp.getEmployeeId() + ",");
					taskInformationApp.setTaskStartBufferTime(worksForHwyApp.getTaskStartBufferTime());
					taskInformationApp.setTaskEndBufferTime(worksForHwyApp.getTaskEndBufferTime());
					List<TaskInformationApp> taskList = taskInformationAppService.getTaskInformationListByBeanApp(taskInformationApp);//获取某个人在某个时间正在出任务的列表，2016-11-09
					if (taskList.size() > 0) {//如果有任务，则不对该责任人进行考核
						break;
					}else{//如果没有任务，则对该责任人进行考核
						dutyUserIdList.add(workPlansApp.getEmployeeId() + "");
					}
				}
			}
			
			if(dutyUserIdList.size() == 0){
				map.put("result", ConstantUtil.FAIL);
				map.put("information","提交失败:当前责任人正在请假中或出任务中,不能进行考核！");
				return map;
			}

			//6.新增五分钟考核任务，新增考核任务对应责任人，记录五分钟统计次数
			//6.1 新增五分钟考核任务,默认为不合格
			CheckTimeApp checkTimeApp = new CheckTimeApp();
			checkTimeApp.setEvalType(workInfoApp.getEvalType());
			checkTimeApp.setEmployeeId(workInfoApp.getEmployeeId());
			checkTimeApp.setSupervisorId(workInfoApp.getEmployeeId());
			checkTimeApp.setCheckAddress(workInfoApp.getCheckAddress());
			checkTimeApp.setCheckTime(workInfoApp.getCheckTime());
			checkTimeApp.setCheckLat(Double.parseDouble(workInfoApp.getCheckLat()));
			checkTimeApp.setCheckLng(Double.parseDouble(workInfoApp.getCheckLng()));
			checkTimeApp.setCheckStatus(2);
			checkTimeApp.setRemark(workInfoApp.getOtherReason());
			int checkId = checkTimeAppService.insertCheckTimeByBeanApp(checkTimeApp);//新增五分钟考核任务，并将新增数据的主键ID返回
			
			//6.2 新增考核任务对应责任人
			List<CheckDutyApp> checkDutyList = new ArrayList<CheckDutyApp>();
			int count = 0;
			if(checkId > 0){
				//循环责任人列表
				for(String dutyUserId : dutyUserIdList){
					CheckDutyApp checkDutyApp = new CheckDutyApp();
					checkDutyApp.setAreaId(workInfoApp.getAreaId());
					checkDutyApp.setPointId(workInfoApp.getPointId());
					checkDutyApp.setCheckId(checkTimeApp.getId());
					checkDutyApp.setEvalType(workInfoApp.getEvalType());
					checkDutyApp.setEmployeeId(Integer.parseInt(dutyUserId));
					checkDutyApp.setCreateAt(workInfoApp.getCheckTime());
					checkDutyApp.setWorkType(2);
					checkDutyList.add(checkDutyApp);
				}
				count = checkDutyAppService.batchAddCheckDutyByListApp(checkDutyList);//
			}
			
			//6.3 记录五分钟统计次数
			if(checkId > 0 && count == checkDutyList.size()){
				//更新五分钟考核次数统计记录
				//1.首先判断该条五分钟考核次数统计记录是否存在
				QualifiedNumberApp qualifiedNumberApp = new QualifiedNumberApp();
				qualifiedNumberApp.setEmployeeId(workInfoApp.getEmployeeId());
				qualifiedNumberApp.setType(evalTypeApp.getType());
				qualifiedNumberApp.setCreateAt(workInfoApp.getCheckTime());
				qualifiedNumberApp.setCreateId(workInfoApp.getEmployeeId());
				qualifiedNumberApp.setUpdateId(workInfoApp.getEmployeeId());
				qualifiedNumberApp.setUpdateAt(workInfoApp.getCheckTime());
				QualifiedNumberApp isQualifiedNumberApp = qualifiedNumberAppService.getQualifiedNumberByBeanApp(qualifiedNumberApp);//根据bean获取五分钟统计记录，2016-11-09
				if(isQualifiedNumberApp == null){//如果为空，则新增一条
					qualifiedNumberApp.setNum(1);
					qualifiedNumberAppService.insertQulifiedNumberByBeanApp(qualifiedNumberApp);//新增五分钟统计次数，2016-11-09
				}else{
					qualifiedNumberApp.setNum(isQualifiedNumberApp.getNum() + 1);
					qualifiedNumberApp.setId(isQualifiedNumberApp.getId());
					qualifiedNumberAppService.updateQulifiedNumberByBeanApp(qualifiedNumberApp);//更新五分钟统计次数，2016-11-09
				}
			}
			
			//根据结果做相应处理
			if(checkId > 0 && count == checkDutyList.size()){
				//7.给责任人推送消息
				Map<String, Object> topicsInfo = new HashMap<String, Object>();
				topicsInfo.put("titleName", evalTypeApp.getEvalName());
				topicsInfo.put("checkTime", workInfoApp.getCheckTime());
				topicsInfo.put("checkAddress", workInfoApp.getCheckAddress());
				topicsInfo.put("lat", workInfoApp.getCheckLat());
				topicsInfo.put("lng", workInfoApp.getCheckLng());
				topicsInfo.put("checkId", checkTimeApp.getId());
				topicsInfo.put("evalType", workInfoApp.getEvalType());
				topicsInfo.put("status", workInfoApp.getCheckStatus());
				topicsInfo.put("type", 1);//1 五分钟，工作提醒
				mqttSendMessage.sendListMessages(topicsInfo, dutyUserIdList);
								
				//8.将处理结果返给app
				map.put("result", ConstantUtil.SUCCESS);
				map.put("information","提交成功！");
				map.put("rows", topicsInfo);
				return map;
			}else{
				//8.将处理结果返给app
				map.put("result", ConstantUtil.FAIL);
				map.put("information","提交失败！");
				return map;
			}
		} catch (Exception e) {
			map.put("result", ConstantUtil.FAIL);
			map.put("information", ConstantUtil.ERROR_MSG);
			return map;
		}
	} 
	*/
	/**
	 * 查询某个责任区某个时间段内的五分钟考核激励，不分页，2016-11-09
	 * produces:用于解决springMVC返回json数据乱码问题
	 */
	/*@RequestMapping(value = "/checkTime", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
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
			
			// 2.对请求参数进行解析
//			acceptContent = CommonUtils.getDecodeParam(acceptContent);
			JSONObject json = JSONObject.fromObject(acceptContent);
			Map<String, Object> selectMap = new HashMap<String, Object>();
			selectMap.put("areaId", json.getString("areaId"));
			selectMap.put("beginDate", json.getString("beginDate"));
			selectMap.put("endDate", json.getString("endDate"));
			
			//3.查询某个责任区某个时间段内的五分钟考核总条数
			int total = checkTimeAppService.getCheckTimeCountByMapApp(selectMap);//查询某个责任区某个时间段内的五分钟考核总条数，2016-11-09
			
			//4.查询某个责任区某个时间段内的五分钟考核集合
			List<CheckTimeForSelectApp> rows = checkTimeAppService.getCheckTimeListByMapApp(selectMap);//查询某个责任区某个时间段内的五分钟考核集合，2016-11-09，不分页
			
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
