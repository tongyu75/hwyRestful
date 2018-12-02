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
import com.czz.hwy.bean.mission.app.ReportInfoApp;
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
import com.czz.hwy.service.mission.app.ReportInfoAppService;
import com.czz.hwy.service.mission.app.ReportsAppService;
import com.czz.hwy.service.mission.app.TaskInformationAppService;
import com.czz.hwy.service.mission.app.WorkCheckAppService;
import com.czz.hwy.service.mission.app.WorkPlansAppService;
import com.czz.hwy.utils.CommonUtils;
import com.czz.hwy.utils.ConstantUtil;
import com.czz.hwy.utils.JsonDateValueProcessor;
import com.czz.hwy.utils.MqttSendMessage;

/***
 * 任务考核项目功能，用于app接口
 * 五克，五分钟，监察，举报
 * @author 张咏雪
 * @Date 2016-11-07
 */
@Controller
@RequestMapping(value = "/workCheckAppController")
public class WorkCheckAppController {

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
	
	@Autowired
	private ReportInfoAppService reportInfoAppService;//新的举报业务层
	
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
	 * 新增举报记录（分两种：一种是未处理的举报信息，一种是已处理的举报信息）:
	 * 2017-04-24：(1)环卫工、检测员、考核员上报举报信息（举报处理结果为未处理，没有处理说明，上报给上级进行处理）
	 * 2017-04-25：(1)检测员、考核员提交举报信息（举报处理结果为已处理，有处理说明，不需要上报给上级进行处理）
	 */
	@RequestMapping(value = "/reportInfo", method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
    @ResponseBody
	public String addReportForRefer(String acceptContent, HttpServletResponse response, HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<String,Object> ();
		
		//1.若是请求参数为空，则返回fail
		if(acceptContent == null || "".equals(acceptContent)){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","举报失败，举报记录信息不能为空！");
			return CommonUtils.JsonObjectToStringForMap(map);
		}
		
		//2.若请求不为空，则将请求参数转换为五分钟考核项目接收信息对象
		JSONObject json = JSONObject.fromObject(acceptContent);
		ReportInfoApp reportInfoApp = (ReportInfoApp) JSONObject.toBean(json, ReportInfoApp.class);
		
		//3.判断请求参数是否为空
		if(reportInfoApp.getSupervisorId() == 0){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","举报失败:举报人ID不能为空！");
			return CommonUtils.JsonObjectToStringForMap(map);
		}
		
		if(reportInfoApp.getSupervisorName() == null || "".equals(reportInfoApp.getSupervisorName())){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","举报失败:举报人名称不能为空！");
			return CommonUtils.JsonObjectToStringForMap(map);
		}
		
		if(reportInfoApp.getSupervisorRole() == 0){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","举报失败:举报人角色ID不能为空！");
			return CommonUtils.JsonObjectToStringForMap(map);
		}
		
		if(reportInfoApp.getEvalValue() == 0){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","举报失败:考核类型不能为空！");
			return CommonUtils.JsonObjectToStringForMap(map);
		}
		
		if(reportInfoApp.getAddressFrom() == null || "".equals(reportInfoApp.getAddressFrom())){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","举报失败:举报地址不能为空！");
			return CommonUtils.JsonObjectToStringForMap(map);
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			reportInfoApp.setCheckTime(sdf.parse(json.getString("checkTime")));
		} catch (ParseException e) {
			map.put("result", ConstantUtil.FAIL);
			map.put("information", "举报失败:举报时间格式不正确！");
			return CommonUtils.JsonObjectToStringForMap(map);
		}
		
		if(reportInfoApp.getCheckTime() == null){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","举报失败:举报时间不能为空！");
			return CommonUtils.JsonObjectToStringForMap(map);
		}
		
		if(reportInfoApp.getCheckLat() == 0){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","举报失败:举报纬度不能为空！");
			return CommonUtils.JsonObjectToStringForMap(map);
		}
		
		if(reportInfoApp.getCheckLng() == 0){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","举报失败:举报经度不能为空！");
			return CommonUtils.JsonObjectToStringForMap(map);
		}
		
		if(reportInfoApp.getStatus() == 0){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","举报失败:举报记录状态不能为空！");
			return CommonUtils.JsonObjectToStringForMap(map);
		}
		
		
		EvalTypeApp evalTypeApp = new EvalTypeApp();
		evalTypeApp.setEvalValue(reportInfoApp.getEvalValue());
		evalTypeApp = evalTypeAppService.getEvalTypeByBeanApp(evalTypeApp);//根据考核类型ID获取举报考核类型详细信息
		if(evalTypeApp == null){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","举报失败:该考核类型不存在！");
			return CommonUtils.JsonObjectToStringForMap(map);
		}
		
		reportInfoApp.setEvalName(evalTypeApp.getEvalName());
		//4.环卫工、检测员、考核员提交举报，并上报上级
		map = workCheckAppService.addReportForRefer(reportInfoApp, map);
		
		if(map.size() == 0){//如果map为空
			map.put("result", ConstantUtil.FAIL);
			map.put("information","举报失败！");
		}
		//5.返回消息
		return CommonUtils.JsonObjectToStringForMap(map);
	} 
	
	/**
	 * 2017-04-26：
	 * 查询一段时间内举报信息记录列表，分页，一页十条
	 * 环卫工：查看自己举报的举报记录列表
	 * 检测员：查看自己举报的举报记录以及自己责任区环卫工举报的举报记录列表
	 * 考核员：查看自己举报的举报记录以及自己负责的责任区下环卫工或检测员举报的举报记录列表
	 * 督察员：查看所有的举报记录列表
	 * produces:用于解决springMVC返回json数据乱码问题
	 */
	@RequestMapping(value = "/reportInfo", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
	public String selectAllReportInfoList(String acceptContent, HttpServletResponse response, HttpServletRequest request){
		
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
			ReportInfoApp reportInfoApp = (ReportInfoApp) JSONObject.toBean(json, ReportInfoApp.class);
			if(reportInfoApp == null) {
				map.put("result", ConstantUtil.FAIL);
				map.put("information",ConstantUtil.SELECT_FAIL_MSG);
				return map.toString();
	        }
			
			//3. 查询举报记录集合
			List<ReportInfoApp> rows = new ArrayList<ReportInfoApp>();
			if(reportInfoApp.getRoleId() == 1){//环卫工：查看一段时间内自己举报的举报记录列表
				rows = reportInfoAppService.getReportInfoListByBeanAppForHWG(reportInfoApp);//环卫工：查看一段时间内自己举报的举报记录列表,2017-04-26
			}else if(reportInfoApp.getRoleId() == 2){//检测员：查一段时间内看自己举报的举报记录以及自己责任区环卫工举报的举报记录列表
				rows = reportInfoAppService.getReportInfoListByBeanAppForCJY(reportInfoApp);//检测员：查看一段时间内自己举报的举报记录以及自己责任区环卫工举报的举报记录列表,2017-04-26
			}else if(reportInfoApp.getRoleId() == 3){//考核员：查看一段时间内自己举报的举报记录以及自己负责的责任区下环卫工或检测员举报的季报记录列表
				rows = reportInfoAppService.getReportInfoListByBeanAppForKHY(reportInfoApp);//考核员：查看一段时间内自己举报的举报记录以及自己负责的责任区下环卫工或检测员举报的季报记录列表,2017-04-26
			}else if(reportInfoApp.getRoleId() == 4){//督察员：查看一段时间内所有的举报记录列表
				rows = reportInfoAppService.getReportInfoListByBeanAppForDCY(reportInfoApp);//督察员：查看一段时间内所有的举报记录列表,2017-04-26
			}
			
			//4.根据查询结果，返回相应数据
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
	 * 2017-04-26：
	 * 上报举报：将不能处理的举报信息上报给上级进行处理。
	 * (1)检测员处理不了的举报信息，将举报信息上报给考核员进行处理。
	 * (2)考核员处理不了的举报信息，将举报信息上报给督察员进行处理。
	 */
	@RequestMapping(value = "/reportInfoForSuperior", method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
    @ResponseBody
	public String reportInfoForSuperior(String acceptContent, HttpServletResponse response, HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<String,Object> ();
		
		//1.若是请求参数为空，则返回fail
		if(acceptContent == null || "".equals(acceptContent)){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","上报失败，上报信息不能为空！");
			return CommonUtils.JsonObjectToStringForMap(map);
		}
		
		//2.若请求不为空，则将请求参数转换为五分钟考核项目接收信息对象
		JSONObject json = JSONObject.fromObject(acceptContent);
		ReportInfoApp reportInfoApp = (ReportInfoApp) JSONObject.toBean(json, ReportInfoApp.class);
		
		//3.判断请求参数是否为空
		if(reportInfoApp.getManageId() == 0){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","上报失败:上报人ID不能为空！");
			return CommonUtils.JsonObjectToStringForMap(map);
		}
		
		if(reportInfoApp.getManageName() == null || "".equals(reportInfoApp.getManageName())){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","上报失败:上报人名称不能为空！");
			return CommonUtils.JsonObjectToStringForMap(map);
		}
		
		if(reportInfoApp.getManageRole() == 0){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","上报失败:上报人角色ID不能为空！");
			return CommonUtils.JsonObjectToStringForMap(map);
		}
		
		if(reportInfoApp.getId() == 0){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","上报失败:上报的举报信息Id不能为空！");
			return CommonUtils.JsonObjectToStringForMap(map);
		}
		
		//4.获取待上报的举报记录
		reportInfoApp.setStatus(2);//查询未处理的举报信息
		ReportInfoApp existsReportInfoApp = reportInfoAppService.getReportInfoAppByBeanApp(reportInfoApp);//根据查询条件查询举报信息，2017-04-26
		if(existsReportInfoApp == null){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","上报失败:上报的举报信息不存在或已被处理！");
			return CommonUtils.JsonObjectToStringForMap(map);
		}
		
		existsReportInfoApp.setManageId(reportInfoApp.getManageId());
		existsReportInfoApp.setManageName(reportInfoApp.getManageName());
		existsReportInfoApp.setManageRole(reportInfoApp.getManageRole());
		existsReportInfoApp.setManageMemo(reportInfoApp.getManageMemo());
		
		//5.检测员、考核员上报给上级进行处理，并给上级发送消息通知
		map = workCheckAppService.reportInfoForSuperior(existsReportInfoApp, map);
		
		if(map.size() == 0){//如果map为空
			map.put("result", ConstantUtil.FAIL);
			map.put("information","上报失败！");
		}
		//5.返回消息
		return CommonUtils.JsonObjectToStringForMap(map);
	} 
	
	/**
	 * 2017-04-27：
	 * 处理完成接口：处理完成举报记录：检测员、考核员和督察员记录处理说明，上传处理图片，处理结束后举报记录状态为已处理，并且不再推送消息给任何人。
	 */
	@RequestMapping(value = "/reportInfoForManage", method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
    @ResponseBody
	public String reportInfoForManage(String acceptContent, HttpServletResponse response, HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<String,Object> ();
		
		//1.若是请求参数为空，则返回fail
		if(acceptContent == null || "".equals(acceptContent)){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","处理失败，上报信息不能为空！");
			return CommonUtils.JsonObjectToStringForMap(map);
		}
		
		//2.若请求不为空，则将请求参数转换为五分钟考核项目接收信息对象
		JSONObject json = JSONObject.fromObject(acceptContent);
		ReportInfoApp reportInfoApp = (ReportInfoApp) JSONObject.toBean(json, ReportInfoApp.class);
		
		//3.判断请求参数是否为空
		if(reportInfoApp.getManageId() == 0){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","处理失败:处理人ID不能为空！");
			return CommonUtils.JsonObjectToStringForMap(map);
		}
		
		if(reportInfoApp.getManageName() == null || "".equals(reportInfoApp.getManageName())){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","处理失败:处理人名称不能为空！");
			return CommonUtils.JsonObjectToStringForMap(map);
		}
		
		if(reportInfoApp.getManageRole() == 0){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","处理失败:处理人角色ID不能为空！");
			return CommonUtils.JsonObjectToStringForMap(map);
		}
		
		if(reportInfoApp.getId() == 0){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","处理失败:处理的举报信息Id不能为空！");
			return CommonUtils.JsonObjectToStringForMap(map);
		}
		
		//4.获取待处理的举报记录
		reportInfoApp.setStatus(2);//查询未处理的举报信息
		ReportInfoApp existsReportInfoApp = reportInfoAppService.getReportInfoAppByBeanApp(reportInfoApp);//根据查询条件查询举报信息，2017-04-26
		if(existsReportInfoApp == null){
			map.put("result", ConstantUtil.FAIL);
			map.put("information","处理失败:待处理的举报信息不存在或已被处理！");
			return CommonUtils.JsonObjectToStringForMap(map);
		}
		
		existsReportInfoApp.setManageId(reportInfoApp.getManageId());
		existsReportInfoApp.setManageName(reportInfoApp.getManageName());
		existsReportInfoApp.setManageRole(reportInfoApp.getManageRole());
		existsReportInfoApp.setManageMemo(reportInfoApp.getManageMemo());
		
		//5.检测员、考核员、督察员处理完成举报记录
		map = workCheckAppService.reportInfoForManage(existsReportInfoApp, map);
		
		if(map.size() == 0){//如果map为空
			map.put("result", ConstantUtil.FAIL);
			map.put("information","处理失败！");
		}
		//5.返回消息
		return CommonUtils.JsonObjectToStringForMap(map);
	} 

}
