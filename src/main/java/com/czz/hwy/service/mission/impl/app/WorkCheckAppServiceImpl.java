package com.czz.hwy.service.mission.impl.app;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.czz.hwy.bean.manager.app.FinesCountApp;
import com.czz.hwy.bean.manager.app.FinesDetailApp;
import com.czz.hwy.bean.manager.app.SubTopicsApp;
import com.czz.hwy.bean.mission.app.CheckDutyApp;
import com.czz.hwy.bean.mission.app.CheckGramApp;
import com.czz.hwy.bean.mission.app.CheckTimeApp;
import com.czz.hwy.bean.mission.app.EvalTypeCheckApp;
import com.czz.hwy.bean.mission.app.EvaluationsApp;
import com.czz.hwy.bean.mission.app.QualifiedNumberApp;
import com.czz.hwy.bean.mission.app.ReportDutyApp;
import com.czz.hwy.bean.mission.app.ReportInfoApp;
import com.czz.hwy.bean.mission.app.ReportsApp;
import com.czz.hwy.bean.mission.app.WorkInfoApp;
import com.czz.hwy.bean.mission.app.WorkPlansApp;
import com.czz.hwy.bean.user.app.UsersApp;
import com.czz.hwy.service.manager.app.FinesCountAppService;
import com.czz.hwy.service.manager.app.FinesDetailAppService;
import com.czz.hwy.service.manager.app.SubTopicsAppService;
import com.czz.hwy.service.mission.app.CheckDutyAppService;
import com.czz.hwy.service.mission.app.CheckGramAppService;
import com.czz.hwy.service.mission.app.CheckTimeAppService;
import com.czz.hwy.service.mission.app.QualifiedNumberAppService;
import com.czz.hwy.service.mission.app.ReportDutyAppService;
import com.czz.hwy.service.mission.app.ReportInfoAppService;
import com.czz.hwy.service.mission.app.ReportsAppService;
import com.czz.hwy.service.mission.app.WorkCheckAppService;
import com.czz.hwy.service.mission.app.WorkPlansAppService;
import com.czz.hwy.service.usermanagement.app.UsersAppService;
import com.czz.hwy.utils.ConstantUtil;
import com.czz.hwy.utils.GetDistance;
import com.czz.hwy.utils.JsonDateValueProcessor;
import com.czz.hwy.utils.MqttSendMessage;

/***
 * 任务考核项目功能service接口实现类，用于app接口
 * 五克，五分钟，监察，举报
 * @author 张咏雪
 * @Date 2016-12-21
 */
@Service
@Transactional
public class WorkCheckAppServiceImpl implements WorkCheckAppService {
	
	@Autowired
	private CheckGramAppService checkGramAppService;//五克业务层
	
	@Autowired
	private CheckTimeAppService checkTimeAppService;//五分钟业务层
	
	@Autowired
	private CheckDutyAppService checkDutyAppService;//五克，五分钟责任人业务层
	
	@Autowired
	private QualifiedNumberAppService qualifiedNumberAppService;//五克，五分钟统计次数业务层

	@Autowired
	private MqttSendMessage mqttSendMessage;//消息推送业务层
	
	@Autowired
	private UsersAppService usersAppService;//员工业务层
	
	@Autowired
	private SubTopicsAppService subTopicsService;//业务层
	
	@Autowired
	private FinesDetailAppService finesDetailAppService;//罚款详情业务层
	
	@Autowired
	private FinesCountAppService finesCountAppService;//罚款总计业务层
	
	@Autowired
	private ReportsAppService reportsAppService;//监察举报业务层
	
	@Autowired
	private ReportDutyAppService reportDutyAppService;//监察举报责任人业务层
	
	private static final double DISCREPANCY = 15.0;//判断是否在某个地方提交过举报任务的距离允许误差
	
	@Autowired
	private ReportInfoAppService reportInfoAppService;//新的举报业务层
	
	@Autowired
	private WorkPlansAppService workPlansAppService;//排班计划业务层
	
	/**
	 * 进行五克任务新增，五克任务责任人新增，五克统计次数更新，五克罚款，五克任务责任人推送，2016-12-20
	 */
	public Map<String, Object> addCheckGram(WorkInfoApp workInfoApp, Map<String, Object> map, List<String> dutyUserIdList, 
			EvalTypeCheckApp evalTypeCheckApp, EvaluationsApp evaluationsApp) {
		
		try{
			//1.新增五克考核任务，新增考核任务对应责任人，记录五克统计次数
			//1.1 新增五克考核任务,默认为不合格
			CheckGramApp checkGramApp = new CheckGramApp();
			checkGramApp.setEvalType(workInfoApp.getEvalType());
			checkGramApp.setEmployeeId(workInfoApp.getEmployeeId());
			checkGramApp.setEmployeeName(workInfoApp.getEmployeeName());
			checkGramApp.setCheckAddress(workInfoApp.getCheckAddress());
			checkGramApp.setCheckTime(workInfoApp.getCheckTime());
			checkGramApp.setCheckLat(Double.parseDouble(workInfoApp.getCheckLat()));
			checkGramApp.setCheckLng(Double.parseDouble(workInfoApp.getCheckLng()));
			checkGramApp.setCheckValue(Double.parseDouble(workInfoApp.getCheckValue()));
			checkGramApp.setPublishTime(workInfoApp.getPublishTime());
			checkGramApp.setCheckStatus(workInfoApp.getCheckStatus());
			int checkId = checkGramAppService.insertCheckGramByBeanApp(checkGramApp);//新增五克考核任务，并将新增数据的主键ID返回,2016-12-20
			
			//1.2 新增考核任务对应责任人
			List<CheckDutyApp> checkDutyList = new ArrayList<CheckDutyApp>();
			int count = 0;
			if(checkId > 0){
				//循环责任人列表
				for(Map<String, Object> dutyMap : workInfoApp.getDutyPeopleList()){
					CheckDutyApp checkDutyApp = new CheckDutyApp();
					checkDutyApp.setAreaId(workInfoApp.getAreaId());
					checkDutyApp.setPointId(workInfoApp.getPointId());
					checkDutyApp.setCheckId(checkGramApp.getId());
					checkDutyApp.setEvalType(workInfoApp.getEvalType());
					checkDutyApp.setEmployeeId(Integer.parseInt(dutyMap.get("employeeId").toString()));
					checkDutyApp.setEmployeeName(dutyMap.get("employeeName").toString());
					checkDutyApp.setCreateAt(workInfoApp.getCheckTime());
					checkDutyApp.setFines(workInfoApp.getCheckStatus() == 2 ? workInfoApp.getFines() : 0);
					checkDutyApp.setWorkType(2);
					checkDutyList.add(checkDutyApp);
				}
				count = checkDutyAppService.batchAddCheckDutyByListApp(checkDutyList);//根据list集合，批量新增考核责任人记录，2016-12-20
			}
			
			//1.3 记录五克统计次数
			if(checkId > 0 && count == checkDutyList.size()){
				//更新五克考核次数统计记录
				updateQualifiedNumber(workInfoApp, evalTypeCheckApp);
			}
			
			//根据结果做相应处理
			if(checkId > 0 && count == checkDutyList.size()){
				//2.给责任人推送消息
				workInfoApp.setId(checkGramApp.getId());
				Map<String, Object> topicsInfo = new HashMap<String, Object>();
				topicsInfo.put("titleName", evalTypeCheckApp.getEvalName());
				topicsInfo.put("checkTime", workInfoApp.getCheckTime());
				topicsInfo.put("checkAddress", workInfoApp.getCheckAddress());
				topicsInfo.put("lat", workInfoApp.getCheckLat());
				topicsInfo.put("lng", workInfoApp.getCheckLng());
				topicsInfo.put("checkId", checkGramApp.getId());
				topicsInfo.put("evalType", workInfoApp.getEvalType());
				topicsInfo.put("status", workInfoApp.getCheckStatus());
				topicsInfo.put("type", 1);//1 五克，工作提醒
                // mqttSendMessage.sendListMessages(topicsInfo, dutyUserIdList);
					
				//3.生成罚款信息
				finesdetail(dutyUserIdList, workInfoApp, evaluationsApp);
				
				//4.将处理结果返给app
				map.put("result", ConstantUtil.SUCCESS);
				map.put("information","提交成功！");
				map.put("rows", topicsInfo);
				return map;
			}else{
				//4.将处理结果返给app
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
	 * 进行五分钟任务新增，五分钟任务责任人新增，五分钟统计次数更新，五分钟罚款，五分钟任务责任人推送
	 * @param workInfoApp    提交五分钟详情
	 * @param map            返回map
	 * @param dutyUserIdList 责任人列表
	 * @param evalTypeCheckApp    考核类型信息
	 * @param evaluationsApp 考核标准信息
	 * @return
	 */
	public Map<String, Object> addCheckTime(WorkInfoApp workInfoApp,Map<String, Object> map, List<String> dutyUserIdList,EvalTypeCheckApp evalTypeCheckApp, EvaluationsApp evaluationsApp) {
		
		try{
			//1.新增五分钟考核任务，新增考核任务对应责任人，记录五分钟统计次数
			//1.1 新增五分钟考核任务,默认为不合格
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
			workInfoApp.setCheckStatus(2);
			checkTimeApp.setRemark(workInfoApp.getOtherReason());
			int checkId = checkTimeAppService.insertCheckTimeByBeanApp(checkTimeApp);//新增五分钟考核任务，并将新增数据的主键ID返回
			
			//1.2 新增考核任务对应责任人
			List<CheckDutyApp> checkDutyList = new ArrayList<CheckDutyApp>();
			int count = 0;
			if(checkId > 0){
				//循环责任人列表
				for(Map<String, Object> dutyMap : workInfoApp.getDutyPeopleList()){
					CheckDutyApp checkDutyApp = new CheckDutyApp();
					checkDutyApp.setAreaId(workInfoApp.getAreaId());
					checkDutyApp.setPointId(workInfoApp.getPointId());
					checkDutyApp.setCheckId(checkTimeApp.getId());
					checkDutyApp.setEvalType(workInfoApp.getEvalType());
					checkDutyApp.setEmployeeId(Integer.parseInt(dutyMap.get("employeeId").toString()));
					checkDutyApp.setEmployeeName(dutyMap.get("employeeName").toString());
					checkDutyApp.setCreateAt(workInfoApp.getCheckTime());
					checkDutyApp.setFines(workInfoApp.getCheckStatus() == 2 ? workInfoApp.getFines() : 0);
					checkDutyApp.setWorkType(2);
					checkDutyList.add(checkDutyApp);
				}
				count = checkDutyAppService.batchAddCheckDutyByListApp(checkDutyList);//根据list集合，批量新增考核责任人记录，2016-11-09
			}
			
			//1.3 记录五分钟统计次数
			if(checkId > 0 && count == checkDutyList.size()){
				//更新五分钟考核次数统计记录
				updateQualifiedNumber(workInfoApp, evalTypeCheckApp);
			}
			
			//根据结果做相应处理
			if(checkId > 0 && count == checkDutyList.size()){
				//2.给责任人推送消息
				workInfoApp.setId(checkTimeApp.getId());
				Map<String, Object> topicsInfo = new HashMap<String, Object>();
				topicsInfo.put("titleName", evalTypeCheckApp.getEvalName());
				topicsInfo.put("checkTime", workInfoApp.getCheckTime());
				topicsInfo.put("checkAddress", workInfoApp.getCheckAddress());
				topicsInfo.put("lat", workInfoApp.getCheckLat());
				topicsInfo.put("lng", workInfoApp.getCheckLng());
				topicsInfo.put("checkId", checkTimeApp.getId());
				topicsInfo.put("evalType", workInfoApp.getEvalType());
				topicsInfo.put("status", workInfoApp.getCheckStatus());
				topicsInfo.put("type", 1);//1 五分钟，工作提醒
                // mqttSendMessage.sendListMessages(topicsInfo, dutyUserIdList);
				
				//3.生成罚款信息
				finesdetail(dutyUserIdList, workInfoApp, evaluationsApp);
								
				//4.将处理结果返给app
				map.put("result", ConstantUtil.SUCCESS);
				map.put("information","提交成功！");
				map.put("rows", topicsInfo);
				return map;
			}else{
				//4.将处理结果返给app
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
	 * 进行监察任务新增，监察任务责任人新增，监察统计次数更新，监察罚款，监察任务责任人推送
	 * @param workInfoApp      提交监察详情
	 * @param map              返回map
	 * @param dutyUserIdList   责任人列表
	 * @param evalTypeCheckApp 考核类型信息
	 * @param evaluationsApp   考核标准信息
	 * @return
	 */
	public Map<String, Object> addSupervise(WorkInfoApp workInfoApp,
			Map<String, Object> map, List<String> dutyUserIdList,
			EvalTypeCheckApp evalTypeCheckApp, EvaluationsApp evaluationsApp) {
		try{
			//1.新增监察考核任务，新增监察考核任务对应责任人
			//1.1 新增监察考核任务,默认为不合格
			ReportsApp reportsApp = new ReportsApp();
			reportsApp.setSupervisorType(workInfoApp.getEvalType());
			reportsApp.setSupervisorUser(workInfoApp.getEmployeeId());
			reportsApp.setSupervisorName(workInfoApp.getEmployeeName());
			reportsApp.setAddressFrom(workInfoApp.getCheckAddress());
			reportsApp.setCheckLat(Double.parseDouble(workInfoApp.getCheckLat()));
			reportsApp.setCheckLng(Double.parseDouble(workInfoApp.getCheckLng()));
			reportsApp.setCheckTime(workInfoApp.getCheckTime());
			reportsApp.setCreateAt(new Date());
			workInfoApp.setCheckStatus(2);//默认为不合格
			reportsApp.setStatus(2);
			reportsApp.setOtherReason(workInfoApp.getOtherReason());
			
			int checkId = reportsAppService.insertSuperviseByBeanApp(reportsApp);//新增监察考核任务，并将新增数据的主键ID返回,2016-12-22
			
			//1.2 新增考核任务对应责任人
			List<ReportDutyApp> reportDutyList = new ArrayList<ReportDutyApp>();
			int count = 0;
			if(checkId > 0){
				//循环责任人列表
				for(Map<String, Object> dutyMap : workInfoApp.getDutyPeopleList()){
					ReportDutyApp reportDutyApp = new ReportDutyApp();
					reportDutyApp.setSupervisorId(reportsApp.getId());// 监督举报考核ID
					reportDutyApp.setSupervisorType(workInfoApp.getEvalType());// 任务类型
					reportDutyApp.setCreateAt(new Date());// 创建时间
					reportDutyApp.setEmployeeId(Integer.parseInt(dutyMap.get("employeeId").toString()));// 责任人ID
					reportDutyApp.setEmployeeName(dutyMap.get("employeeName").toString());
					reportDutyApp.setAreaId(workInfoApp.getAreaId());
					reportDutyApp.setPointId(workInfoApp.getPointId());
					reportDutyApp.setWorkType(2);
					reportDutyApp.setFines(workInfoApp.getCheckStatus() == 2 ? workInfoApp.getFines() : 0);
					reportDutyList.add(reportDutyApp);
				}
				count = reportDutyAppService.batchAddReportsDutyByListApp(reportDutyList);//根据list集合，批量新增考核责任人记录，2016-12-22
			}
			
			//根据结果做相应处理
			if(checkId > 0 && count == reportDutyList.size()){
				//2.给责任人推送消息
				workInfoApp.setId(reportsApp.getId());
				Map<String, Object> topicsInfo = new HashMap<String, Object>();
				topicsInfo.put("titleName", evalTypeCheckApp.getEvalName());
				topicsInfo.put("checkTime", workInfoApp.getCheckTime());
				topicsInfo.put("checkAddress", workInfoApp.getCheckAddress());
				topicsInfo.put("lat", workInfoApp.getCheckLat());
				topicsInfo.put("lng", workInfoApp.getCheckLng());
				topicsInfo.put("checkId", reportsApp.getId());
				topicsInfo.put("evalType", workInfoApp.getEvalType());
				topicsInfo.put("status", workInfoApp.getCheckStatus());
				topicsInfo.put("type", 1);//1 ，工作提醒
                // mqttSendMessage.sendListMessages(topicsInfo, dutyUserIdList);
				
				//3.生成罚款信息
				finesdetail(dutyUserIdList, workInfoApp, evaluationsApp);
								
				//4.将处理结果返给app
				map.put("result", ConstantUtil.SUCCESS);
				map.put("information","提交成功！");
				map.put("rows", topicsInfo);
				return map;
			}else{
				//4.将处理结果返给app
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
	 * 进行举报任务新增  2016-12-23
	 * @param workInfoApp   提交举报详情
	 * @param map           返回map
	 * @return
	 */
	public Map<String, Object> addReports(WorkInfoApp workInfoApp, Map<String, Object> map) {
		try{
			//1.封装举报考核任务,默认为不合格
			ReportsApp reportsApp = new ReportsApp();
			reportsApp.setSupervisorType(workInfoApp.getEvalType());
			reportsApp.setSupervisorUser(workInfoApp.getEmployeeId());
			reportsApp.setSupervisorName(workInfoApp.getEmployeeName());
			reportsApp.setAddressFrom(workInfoApp.getCheckAddress());
			reportsApp.setCheckLat(Double.parseDouble(workInfoApp.getCheckLat()));
			reportsApp.setCheckLng(Double.parseDouble(workInfoApp.getCheckLng()));
			reportsApp.setCheckTime(workInfoApp.getCheckTime());
			reportsApp.setCreateAt(new Date());
			workInfoApp.setCheckStatus(2);//默认为不合格
			reportsApp.setStatus(2);
			reportsApp.setOtherReason(workInfoApp.getOtherReason());
			
			//2.判断30分钟内该范围该考核类型的举报任务是否已经提交过
			
			//2.1 获取30分钟内该考核类型的举报记录列表
			List<ReportsApp> reportsAppExitsList =  reportsAppService.getExitsReportsAppByBeanApp(reportsApp);//获取30分钟内该考核类型的举报记录列表，2016-12-23
			
			//2.2 循环举报记录列表，判断该考核类型的举报在该范围内是否已提交过？
			boolean flag = false;//表示没有提交过
			for (ReportsApp reportsAppExists : reportsAppExitsList) {
				double distance = GetDistance.getLongDistance(reportsApp.getCheckLng(),
						reportsApp.getCheckLat(), reportsAppExists.getCheckLng(),
						reportsAppExists.getCheckLat());
				if (distance <= DISCREPANCY) {//两点具体小于15米，说明在该范围内提交过该类型的任务，不能再次提交
					flag = true;
					break;
				}
			}
			
			int checkId =  0 ;
			if(flag){//2.3 若是30分钟内该范围该考核类型的举报任务已经提交过，则给予提示
				map.put("result", ConstantUtil.FAIL);
				map.put("information", ConstantUtil.REPORTS_EXITS);
				return map;
			}else{//2.4 若是30分钟内该范围该考核类型的举报任务没有提交过，则进行新增操作
				// 插入举报任务
				checkId = reportsAppService.insertReportsAppByBeanApp(reportsApp);//新增监察考核任务，并将新增数据的主键ID返回,2016-12-22
				workInfoApp.setId(reportsApp.getId());
			}
			
			//根据结果做相应处理
			if(checkId > 0){
				//3.将处理结果返给app
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
				JSONObject jsonobject = JSONObject.fromObject(workInfoApp, jsonConfig);
				Map<String, Object> resInfo = new HashMap<String, Object>();
				resInfo.put("workInfo", workInfoApp);
				map.put("result", ConstantUtil.SUCCESS);
				map.put("information", resInfo);
				return map;
			}else{
				//3.将处理结果返给app
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
	 * 更新五克,五分钟考核次数统计记录，2016-12-21
	 * @return
	 */
	public void updateQualifiedNumber(WorkInfoApp workInfoApp, EvalTypeCheckApp evalTypeCheckApp){
		//1.首先判断该条五克考核次数统计记录是否存在
		QualifiedNumberApp qualifiedNumberApp = new QualifiedNumberApp();
		qualifiedNumberApp.setEmployeeId(workInfoApp.getEmployeeId());
		qualifiedNumberApp.setType(evalTypeCheckApp.getType());
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
	
	/**
	 * 生成罚款信息，只针对环卫工，不会连带上级，2016-12-20
	 * @param dutyPlansList 责任人列表：只有环卫工
	 * @param workInfoApp   提交考核信息   
	 * @param evaluationsApp 考核标准信息
	 */
	private void finesdetail(List<String> dutyUserIdList, WorkInfoApp workInfoApp, EvaluationsApp evaluationsApp) {
		//1.如果为合格，则不进行罚款
		if(workInfoApp.getCheckStatus() == 1){
			return;
		}
		//2.如果没有设置罚款金额，则不进行罚款
		if(workInfoApp.getFines() <= 0){
			return;
		}
		
		List<String> topicsList = new ArrayList<String>();
		FinesDetailApp finesDetailApp = null;
		//3.循环环卫工责任人，对环卫工进行处罚
		for (String dutyUserId : dutyUserIdList){//责任人都是环卫工
			UsersApp usersApp2 = usersAppService.getUserInfoByEmployeeId(Integer.parseInt(dutyUserId));
			SubTopicsApp subTopicsApp = new SubTopicsApp();
			subTopicsApp.setEmployeeId(usersApp2.getEmployeeId());
			subTopicsApp = subTopicsService.getSubTopicsAppByBeanApp(subTopicsApp);//通过userId获得当前订阅信息，2016-12-20

			// 消息发布给环卫工
			if (subTopicsApp != null) {
				// 设置环卫工ID
				topicsList.add(subTopicsApp.getTopics());
			}
			// 3.1 做成罚款记录
			finesDetailApp = insertFinesInfo(usersApp2, workInfoApp, evaluationsApp);
		}

		//4. 发布罚款详细给环卫工
		if (finesDetailApp != null) {
			Map<String, Object> topicsInfo = new HashMap<String, Object>();
			topicsInfo.put("id", workInfoApp.getId());
			topicsInfo.put("evalType", workInfoApp.getEvalType());
			topicsInfo.put("amount", workInfoApp.getFines());
			topicsInfo.put("checkAddress", workInfoApp.getCheckAddress());
			topicsInfo.put("checkTime", workInfoApp.getCheckTime());
			topicsInfo.put("type", 2);//
            // mqttSendMessage.sendListMessages(topicsInfo, dutyUserIdList);
		}
	}

	/**
	 * 新增罚款记录，2016-12-20，只针对环卫工
	 */
	private FinesDetailApp insertFinesInfo(UsersApp usersApp, WorkInfoApp workInfoApp, EvaluationsApp evaluationsApp) {

		Date date = new Date();
		
		//1.添加罚款详情
		FinesDetailApp finesDetailApp = new FinesDetailApp();
		finesDetailApp.setCheckId(workInfoApp.getId());
		finesDetailApp.setAmount(workInfoApp.getFines());
		finesDetailApp.setCheckUser(workInfoApp.getEmployeeId());
		finesDetailApp.setDeptId(usersApp.getDeptId());
		finesDetailApp.setEmployeeId(usersApp.getEmployeeId());
		finesDetailApp.setEvalId(workInfoApp.getEvalType());
		finesDetailApp.setStandId(evaluationsApp.getId());//如果考核标准为空，则设置考核标准ID为0
		finesDetailApp.setCreateAt(date);
		finesDetailApp.setCheckTime(date);
		finesDetailApp.setUpdateAt(date);
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String fa = f.format(workInfoApp.getCheckTime());
		finesDetailApp.setRemark(fa + "，在" + workInfoApp.getCheckAddress()
				+ usersApp.getShowname() + ",因" + evaluationsApp.getEvalName() + "罚款"
				+ workInfoApp.getFines() + "元");
		finesDetailAppService.inertFinesDetailAppByBeanApp(finesDetailApp);//新增罚款详情，2016-12-20
		
		//2.添加罚款总计
		FinesCountApp finesCountApp = new FinesCountApp();
		finesCountApp.setEmployeeId(usersApp.getEmployeeId());
		finesCountApp.setMonth(date);
		finesCountApp = finesCountAppService.getFinesCountAppByBeanApp(finesCountApp);//根据bean获取罚款总计信息，2016-12-20
		if (finesCountApp != null) {
			// 进行累加
			double amount = finesCountApp.getAmount();
			BigDecimal b1 = new BigDecimal(Double.toString(amount));
			BigDecimal b2 = new BigDecimal(Double.toString(workInfoApp.getFines()));
			amount = b1.add(b2).doubleValue();
			finesCountApp.setAmount(amount);
			finesCountApp.setCount(finesCountApp.getCount() + 1);
			finesCountAppService.updateFinesCountByBeanApp(finesCountApp);//更新罚款总计信息，2016-12-20
		} else {
			finesCountApp = new FinesCountApp();
			finesCountApp.setAmount(workInfoApp.getFines());
			finesCountApp.setEmployeeId(usersApp.getEmployeeId());
			finesCountApp.setCount(1);
			finesCountApp.setDeptId(usersApp.getDeptId());
			finesCountApp.setMonth(new Date());
			finesCountApp.setCreateAt(new Date());
			finesCountAppService.insertFinesAppByBeanApp(finesCountApp);//新增罚款总计记录，2016-12-20
		}
		return finesDetailApp;
	}

	/**
	 * 新增举报记录（分两种：一种是未处理的举报信息，一种是已处理的举报信息）:
	 * 2017-04-24：(1)环卫工、检测员、考核员上报举报信息（举报处理结果为未处理，没有处理说明，上报给上级进行处理）
	 * 2017-04-25：(1)检测员、考核员提交举报信息（举报处理结果为已处理，有处理说明，不需要上报给上级进行处理）
	 */
	public Map<String, Object> addReportForRefer(ReportInfoApp reportInfoApp,Map<String, Object> map) {
		Date nowDate = reportInfoApp.getCheckTime();//举报时间
		
		try{
			//1.组装举报信息类
			reportInfoApp.setCreateAt(nowDate);
			reportInfoApp.setUpdateAt(nowDate);
			
			//1.1 记录上报或处理轨迹
			int manageStatus = reportInfoApp.getStatus() == 2 ? 3 : 1;//处理结果,如果是上报举报，处理结果为已上报；如果是提交举报并已处理，处理结果为已处理
			if(reportInfoApp.getSupervisorRole() == 2){//如果上报人角色为检测员，则记录检测员的上报轨迹
				reportInfoApp.setJcyManageId(reportInfoApp.getSupervisorId());
				reportInfoApp.setJcyManageName(reportInfoApp.getSupervisorName());
				reportInfoApp.setJcyManageStatus(manageStatus);//1 已处理 2 未处理 3已上报
				reportInfoApp.setJcyManageDate(nowDate);
				reportInfoApp.setJcyManageMemo(reportInfoApp.getManageMemo());
			}else if(reportInfoApp.getSupervisorRole() == 3){//如果上报人角色为考核员，则记录考核员的上报轨迹
				reportInfoApp.setKhyManageId(reportInfoApp.getSupervisorId());
				reportInfoApp.setKhyManageName(reportInfoApp.getSupervisorName());
				reportInfoApp.setKhyManageStatus(manageStatus);//1 已处理 2 未处理 3已上报
				reportInfoApp.setKhyManageDate(nowDate);
				reportInfoApp.setKhyManageMemo(reportInfoApp.getManageMemo());
			}/*if(reportInfoApp.getSupervisorRole() == 4){//如果上报人角色为督察员，则记录督察员的上报轨迹
				reportInfoApp.setDcyManageId(reportInfoApp.getSupervisorId());
				reportInfoApp.setDcyManageName(reportInfoApp.getSupervisorName());
				reportInfoApp.setDcyManageStatus(manageStatus);//1 已处理 2 未处理 3已上报
				reportInfoApp.setDcyManageDate(nowDate);
				reportInfoApp.setDcyManageMemo(reportInfoApp.getManageMemo());
			}*/
			
			//1.2 记录处理人角色
			//如果提交的是未处理过的举报信息，则将举报信息上报给上级
			if(reportInfoApp.getStatus() == 2){
				reportInfoApp.setManageRole(reportInfoApp.getSupervisorRole() + 1);//上报给上级，处理人角色为举报人上级角色
			}
			//如果提交的是处理过的举报信息，则举报人为处理人
			else if(reportInfoApp.getStatus() == 1){
				reportInfoApp.setManageRole(reportInfoApp.getSupervisorRole());//处理人角色为举报人角色
			}
			
			//1.3记录举报人责任区ID集合
			//根据员工ID和员工角色获取员工所在责任区
			WorkPlansApp workPlansApp = new WorkPlansApp();
			workPlansApp.setEmployeeId(reportInfoApp.getSupervisorId());
			workPlansApp.setRoleId(reportInfoApp.getSupervisorRole());
			List<WorkPlansApp> areaIdList = workPlansAppService.getAreaListByBeanApp(workPlansApp);//根据员工ID和员工角色获取员工所在责任区，2017-04-25
			String areaIds = "";
			for(WorkPlansApp plans : areaIdList){
				areaIds += plans.getAreaId() + ",";
			}
			reportInfoApp.setAreaIds(areaIds.length() > 0 ? areaIds.substring(0, areaIds.length() - 1) : areaIds);
			
			//2.判断30分钟内该范围该考核类型的举报任务是否已经提交过(未处理的)
			
			//2.1 获取30分钟内该考核类型的举报记录列表(未处理的)
			List<ReportInfoApp> reportInfoAppExitsList =  reportInfoAppService.getExitsReportInfoAppByBeanApp(reportInfoApp);//获取30分钟内该考核类型的举报记录列表(未处理的)，2017-04-24
			
			//2.2 循环举报记录列表，判断该考核类型的举报在该范围内是否已提交过？
			boolean flag = false;//表示没有提交过
			for (ReportInfoApp reportInfoAppExists : reportInfoAppExitsList) {
				double distance = GetDistance.getLongDistance(reportInfoApp.getCheckLng(),
						reportInfoApp.getCheckLat(), reportInfoAppExists.getCheckLng(),
						reportInfoAppExists.getCheckLat());
				if (distance <= DISCREPANCY) {//两点具体小于15米，说明在该范围内提交过该类型的任务，不能再次提交
					flag = true;
					break;
				}
			}
			
			int checkId =  0 ;
			if(flag){//2.3 若是30分钟内该范围该考核类型的举报任务已经提交过，则给予提示
				map.put("result", ConstantUtil.FAIL);
				map.put("information", ConstantUtil.REPORTS_EXITS);
				return map;
			}else{//2.4 若是30分钟内该范围该考核类型的举报任务没有提交过，则进行新增操作
				// 插入举报任务
				checkId = reportInfoAppService.insertReportInfoAppByBeanApp(reportInfoApp);//新增举报信息，并将新增数据的主键ID返回,2017-04-25
				reportInfoApp.setId(reportInfoApp.getId());
			}
			
			//根据结果做相应处理
			if(checkId > 0){
				Map<String, Object> topicsInfo = new HashMap<String, Object>();
				topicsInfo.put("titleName", reportInfoApp.getEvalName());
				topicsInfo.put("checkTime", reportInfoApp.getCheckTime());
				topicsInfo.put("checkAddress", reportInfoApp.getAddressFrom());
				topicsInfo.put("lat", reportInfoApp.getCheckLat());
				topicsInfo.put("lng", reportInfoApp.getCheckLng());
				topicsInfo.put("checkId", reportInfoApp.getId());
				topicsInfo.put("evalType", reportInfoApp.getEvalValue());
				topicsInfo.put("status", reportInfoApp.getStatus());
				topicsInfo.put("type", 9);//9 ，举报记录
				
				//3.如果举报未处理，上报给上级，给上级推送消息，提醒上级进行处理
				//根据角色ID和责任区ID集合获取负责人列表(获取检测员、考核员或督察员列表)
				if(reportInfoApp.getStatus() == 2){
					Map<String, Object> selectMap = new HashMap<String, Object>();
					selectMap.put("areaIds", reportInfoApp.getAreaIds());
					selectMap.put("roleId", reportInfoApp.getManageRole());
					List<String> userList = workPlansAppService.getHeadPeopleListByMap(selectMap);//根据角色ID和责任区ID集合获取负责人列表(获取检测员、考核员或督察员列表)，2017-04-25
					if(userList.size() > 0){
                        // mqttSendMessage.sendListMessages(topicsInfo,
                        // userList);
					}
				}
				
				//4.将处理结果返给app
				map.put("result", ConstantUtil.SUCCESS);
				map.put("information","举报成功！");
				map.put("rows", topicsInfo);
				return map;
			}else{
				//3.将处理结果返给app
				map.put("result", ConstantUtil.FAIL);
				map.put("information","举报失败！");
				return map;
			}
		} catch (Exception e) {
			map.put("result", ConstantUtil.FAIL);
			map.put("information", ConstantUtil.ERROR_MSG);
			return map;
		}
	}

	/**
	 * 检测员、考核员上报给上级进行处理，并给上级发送消息通知,2017-04-26
	 */
	public Map<String, Object> reportInfoForSuperior(ReportInfoApp reportInfoApp, Map<String, Object> map) {
		try{
			//1 记录上报轨迹
 			int manageStatus = 3;//3表示上报
			Date nowDate = new Date();//上报时间
			if(reportInfoApp.getManageRole() == 2){//如果上报人角色为检测员，则记录检测员的上报轨迹
				reportInfoApp.setJcyManageId(reportInfoApp.getManageId());
				reportInfoApp.setJcyManageName(reportInfoApp.getManageName());
				reportInfoApp.setJcyManageStatus(manageStatus);//1 已处理 2 未处理 3已上报
				reportInfoApp.setJcyManageDate(nowDate);
				reportInfoApp.setJcyManageMemo(reportInfoApp.getManageMemo());
			}else if(reportInfoApp.getManageRole() == 3){//如果上报人角色为考核员，则记录考核员的上报轨迹
				reportInfoApp.setKhyManageId(reportInfoApp.getManageId());
				reportInfoApp.setKhyManageName(reportInfoApp.getManageName());
				reportInfoApp.setKhyManageStatus(manageStatus);//1 已处理 2 未处理 3已上报
				reportInfoApp.setKhyManageDate(nowDate);
				reportInfoApp.setKhyManageMemo(reportInfoApp.getManageMemo());
			}
			reportInfoApp.setManageRole(reportInfoApp.getManageRole() + 1);//上报给上级，处理人角色为举报人上级角色
			
			//2.更新上报相关信息
			int count =  reportInfoAppService.updateReportInfoAppByBeanApp(reportInfoApp);//根据bean更新举报信息，2017-04-26
			
			//3.上报给上级，给上级推送消息，提醒上级进行处理
			if(count > 0){
				
				//3.1如果举报未处理，上报给上级，给上级推送消息，提醒上级进行处理
				//根据角色ID和责任区ID集合获取负责人列表(获取检测员、考核员或督察员列表)
				Map<String, Object> selectMap = new HashMap<String, Object>();
				selectMap.put("areaIds", reportInfoApp.getAreaIds());
				selectMap.put("roleId", reportInfoApp.getManageRole());
				List<String> userList = workPlansAppService.getHeadPeopleListByMap(selectMap);//根据角色ID和责任区ID集合获取负责人列表(获取检测员、考核员或督察员列表)，2017-04-25
				if(userList.size() > 0){
					Map<String, Object> topicsInfo = new HashMap<String, Object>();
					topicsInfo.put("titleName", reportInfoApp.getEvalName());
					topicsInfo.put("checkTime", reportInfoApp.getCheckTime());
					topicsInfo.put("checkAddress", reportInfoApp.getAddressFrom());
					topicsInfo.put("lat", reportInfoApp.getCheckLat());
					topicsInfo.put("lng", reportInfoApp.getCheckLng());
					topicsInfo.put("checkId", reportInfoApp.getId());
					topicsInfo.put("evalType", reportInfoApp.getEvalValue());
					topicsInfo.put("status", reportInfoApp.getStatus());
					topicsInfo.put("type", 9);//9 ，举报记录
                    // mqttSendMessage.sendListMessages(topicsInfo, userList);
				}
				
				//4.将处理结果返给app
				map.put("result", ConstantUtil.SUCCESS);
				map.put("information","上报成功！");
				return map;
			}else{
				//4.将处理结果返给app
				map.put("result", ConstantUtil.FAIL);
				map.put("information","上报失败！");
				return map;
			}
		} catch (Exception e) {
			map.put("result", ConstantUtil.FAIL);
			map.put("information", ConstantUtil.ERROR_MSG);
			return map;
		}
	}

	/**
	 * 检测员、考核员、督察员处理完成举报记录 ,2017-04-27
	 */
	public Map<String, Object> reportInfoForManage(ReportInfoApp reportInfoApp, Map<String, Object> map) {
		try{
			//1 记录处理轨迹
			Date nowDate = new Date();//处理时间
			reportInfoApp.setStatus(1);//1 表示已处理 
			reportInfoApp.setUpdateAt(nowDate);
 			int manageStatus = 1;//1表示已处理 
			if(reportInfoApp.getManageRole() == 2){//如果处理人角色为检测员，则记录检测员的处理轨迹
				reportInfoApp.setJcyManageId(reportInfoApp.getManageId());
				reportInfoApp.setJcyManageName(reportInfoApp.getManageName());
				reportInfoApp.setJcyManageStatus(manageStatus);//1 已处理 2 未处理 3已上报
				reportInfoApp.setJcyManageDate(nowDate);
				reportInfoApp.setJcyManageMemo(reportInfoApp.getManageMemo());
			}else if(reportInfoApp.getManageRole() == 3){//如果处理人角色为考核员，则记录考核员的处理轨迹
				reportInfoApp.setKhyManageId(reportInfoApp.getManageId());
				reportInfoApp.setKhyManageName(reportInfoApp.getManageName());
				reportInfoApp.setKhyManageStatus(manageStatus);//1 已处理 2 未处理 3已上报
				reportInfoApp.setKhyManageDate(nowDate);
				reportInfoApp.setKhyManageMemo(reportInfoApp.getManageMemo());
			}else if(reportInfoApp.getManageRole() == 4){//如果处理人角色为督察员，则记录督察员的处理轨迹
				reportInfoApp.setDcyManageId(reportInfoApp.getManageId());
				reportInfoApp.setDcyManageName(reportInfoApp.getManageName());
				reportInfoApp.setDcyManageStatus(manageStatus);//1 已处理 2 未处理 3已上报
				reportInfoApp.setDcyManageDate(nowDate);
				reportInfoApp.setDcyManageMemo(reportInfoApp.getManageMemo());
			}
			
			//2.更新处理人相关信息以及举报状态
			int count =  reportInfoAppService.updateReportInfoAppByBeanAppForManage(reportInfoApp);//处理完成：根据bean更新举报信息，2017-04-27
			
			//3.上报给上级，给上级推送消息，提醒上级进行处理
			if(count > 0){
				//4.将处理结果返给app
				map.put("result", ConstantUtil.SUCCESS);
				map.put("information","处理成功！");
				return map;
			}else{
				//4.将处理结果返给app
				map.put("result", ConstantUtil.FAIL);
				map.put("information","处理失败！");
				return map;
			}
		} catch (Exception e) {
			map.put("result", ConstantUtil.FAIL);
			map.put("information", ConstantUtil.ERROR_MSG);
			return map;
		}
	}
	
	
}
