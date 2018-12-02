package com.czz.hwy.service.mission.app;

import java.util.List;
import java.util.Map;

import com.czz.hwy.bean.mission.app.EvalTypeCheckApp;
import com.czz.hwy.bean.mission.app.EvaluationsApp;
import com.czz.hwy.bean.mission.app.ReportInfoApp;
import com.czz.hwy.bean.mission.app.WorkInfoApp;

/***
 * 任务考核项目功能service接口，用于app接口
 * 五克，五分钟，监察，举报
 * @author 张咏雪
 * @Date 2016-12-21
 */
public interface WorkCheckAppService{
	/**
	 * 进行五克任务新增，五克任务责任人新增，五克统计次数更新，五克罚款，五克任务责任人推送
	 * @param workInfoApp    提交五克详情
	 * @param map            返回map
	 * @param dutyUserIdList 责任人列表
	 * @param evalTypeCheckApp    考核类型信息
	 * @param evaluationsApp 考核标准信息
	 * @return
	 */
	Map<String, Object> addCheckGram(WorkInfoApp workInfoApp, Map<String, Object> map, List<String> dutyUserIdList, EvalTypeCheckApp evalTypeCheckApp, EvaluationsApp evaluationsApp);
	
	/**
	 * 进行五分钟任务新增，五分钟任务责任人新增，五分钟统计次数更新，五分钟罚款，五分钟任务责任人推送
	 * @param workInfoApp    提交五分钟详情
	 * @param map            返回map
	 * @param dutyUserIdList 责任人列表
	 * @param evalTypeCheckApp    考核类型信息
	 * @param evaluationsApp 考核标准信息
	 * @return
	 */
	Map<String, Object> addCheckTime(WorkInfoApp workInfoApp, Map<String, Object> map, List<String> dutyUserIdList, EvalTypeCheckApp evalTypeCheckApp, EvaluationsApp evaluationsApp);

	
	/**
	 * 进行监察任务新增，监察任务责任人新增，监察统计次数更新，监察罚款，监察任务责任人推送
	 * @param workInfoApp      提交监察详情
	 * @param map              返回map
	 * @param dutyUserIdList   责任人列表
	 * @param evalTypeCheckApp 考核类型信息
	 * @param evaluationsApp   考核标准信息
	 * @return
	 */
	Map<String, Object> addSupervise(WorkInfoApp workInfoApp,Map<String, Object> map, List<String> dutyUserIdList, EvalTypeCheckApp evalTypeCheckApp, EvaluationsApp evaluationsApp);

	/**
	 * 进行举报任务新增,2017-04-23
	 * @param workInfoApp   提交举报详情
	 * @param map           返回map
	 * @return
	 */
	Map<String, Object> addReports(WorkInfoApp workInfoApp, Map<String, Object> map);
	
	/**
	 * 新增举报记录（分两种：一种是未处理的举报信息，一种是已处理的举报信息）:
	 * 2017-04-24：(1)环卫工、检测员、考核员上报举报信息（举报处理结果为未处理，没有处理说明，上报给上级进行处理）
	 * 2017-04-25：(1)检测员、考核员提交举报信息（举报处理结果为已处理，有处理说明，不需要上报给上级进行处理）
	 * @param reportInfoApp 提交的举报信息
	 * @param map			返回map
	 * @return
	 */
	Map<String, Object> addReportForRefer(ReportInfoApp reportInfoApp,Map<String, Object> map);

	/**
	 * 检测员、考核员上报给上级进行处理，并给上级发送消息通知,2017-04-26
	 * @param existsReportInfoApp  上报的举报信息
	 * @param map                  返回map
	 * @return
	 */
	Map<String, Object> reportInfoForSuperior(ReportInfoApp existsReportInfoApp, Map<String, Object> map);

	/**
	 * 检测员、考核员、督察员处理完成举报记录 ,2017-04-27
	 * @param existsReportInfoApp
	 * @param map
	 * @return
	 */
	Map<String, Object> reportInfoForManage(ReportInfoApp existsReportInfoApp,Map<String, Object> map);
	
}
