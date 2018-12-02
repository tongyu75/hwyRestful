package com.czz.hwy.action.mission.app;

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

import com.czz.hwy.bean.mission.app.CheckDutyApp;
import com.czz.hwy.bean.mission.app.CheckTimeApp;
import com.czz.hwy.bean.mission.app.CheckTimeForSelectApp;
import com.czz.hwy.bean.mission.app.EvalTypeApp;
import com.czz.hwy.bean.mission.app.QualifiedNumberApp;
import com.czz.hwy.bean.mission.app.WorkInfoApp;
import com.czz.hwy.bean.mission.app.WorksForHwyApp;
import com.czz.hwy.service.manager.app.LeaveAppService;
import com.czz.hwy.service.mission.app.CheckDutyAppService;
import com.czz.hwy.service.mission.app.CheckTimeAppService;
import com.czz.hwy.service.mission.app.EvalTypeAppService;
import com.czz.hwy.service.mission.app.QualifiedNumberAppService;
import com.czz.hwy.service.mission.app.TaskInformationAppService;
import com.czz.hwy.service.mission.app.WorkPlansAppService;
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
@RequestMapping(value = "/copyOfWorkCheckAppController")
public class CopyOfWorkCheckAppController {

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
	private EvalTypeAppService evalTypeAppService;//考核分类业务层
	
	@Autowired
	private MqttSendMessage mqttSendMessage;//消息推送业务层
	
	/**
	 * 新增五分钟考核记录，2016-11-08
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	@RequestMapping(value = "/checkTime", method = RequestMethod.POST)
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
				map.put("employeeId", j.getString("employeeId").toString());
				map.put("employeeName", j.getString("employeeName").toString());
				dutyPeopleList.add(dutyMap);
				dutyUserIdList.add(j.getString("employeeId").toString());
			}
			
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

			//4.新增五分钟考核任务，新增考核任务对应责任人，记录五分钟统计次数
			//4.1 新增五分钟考核任务,默认为不合格
			CheckTimeApp checkTimeApp = new CheckTimeApp();
			checkTimeApp.setEvalType(workInfoApp.getEvalType());
			checkTimeApp.setEmployeeId(workInfoApp.getEmployeeId());
			checkTimeApp.setEmployeeName(workInfoApp.getEmployeeName());
			checkTimeApp.setSupervisorId(workInfoApp.getEmployeeId());
			checkTimeApp.setCheckAddress(workInfoApp.getCheckAddress());
			checkTimeApp.setCheckTime(workInfoApp.getCheckTime());
			checkTimeApp.setCheckLat(Double.parseDouble(workInfoApp.getCheckLat()));
			checkTimeApp.setCheckLng(Double.parseDouble(workInfoApp.getCheckLng()));
			checkTimeApp.setCheckStatus(2);
			checkTimeApp.setRemark(workInfoApp.getOtherReason());
			int checkId = checkTimeAppService.insertCheckTimeByBeanApp(checkTimeApp);//新增五分钟考核任务，并将新增数据的主键ID返回
			
			//4.2 新增考核任务对应责任人
			List<CheckDutyApp> checkDutyList = new ArrayList<CheckDutyApp>();
			int count = 0;
			if(checkId > 0){
				//循环责任人列表
				for(Map<String, Object> dutyMap : dutyPeopleList){
					CheckDutyApp checkDutyApp = new CheckDutyApp();
					checkDutyApp.setAreaId(workInfoApp.getAreaId());
					checkDutyApp.setPointId(workInfoApp.getPointId());
					checkDutyApp.setCheckId(checkTimeApp.getId());
					checkDutyApp.setEvalType(workInfoApp.getEvalType());
					checkDutyApp.setEmployeeId(Integer.parseInt(dutyMap.get("employeeId").toString()));
					checkDutyApp.setEmployeeName(dutyMap.get("employeeName").toString());
					checkDutyApp.setCreateAt(workInfoApp.getCheckTime());
					checkDutyApp.setWorkType(2);
					checkDutyList.add(checkDutyApp);
				}
				count = checkDutyAppService.batchAddCheckDutyByListApp(checkDutyList);//根据list集合，批量新增考核责任人记录，2016-11-09
			}
			
			//4.3 记录五分钟统计次数
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
				//5.给责任人推送消息
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
                // mqttSendMessage.sendListMessages(topicsInfo, dutyUserIdList);
								
				//6.将处理结果返给app
				map.put("result", ConstantUtil.SUCCESS);
				map.put("information","提交成功！");
				map.put("rows", topicsInfo);
				return map;
			}else{
				//6.将处理结果返给app
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
	
	
	/**
	 * 查询某个责任区某个时间段内的五分钟考核激励，不分页，2016-11-09
	 * produces:用于解决springMVC返回json数据乱码问题
	 */
	@RequestMapping(value = "/checkTime", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
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
	} 

	/**
	 * 新增五分钟考核记录，2016-11-08
	 */
/*	@RequestMapping(value = "/checkTime", method = RequestMethod.POST)
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
	} */
	
	/**
	 * 查询某个责任区某个时间段内的五分钟考核激励，不分页，2016-11-09
	 * produces:用于解决springMVC返回json数据乱码问题
	 */
/*	@RequestMapping(value = "/checkTime", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
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
