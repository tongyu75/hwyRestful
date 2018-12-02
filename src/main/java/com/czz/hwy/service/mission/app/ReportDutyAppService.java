package com.czz.hwy.service.mission.app;

import java.util.List;

import com.czz.hwy.bean.mission.app.ReportDutyApp;


/**
 * 监察举报考核项目责任人的service接口，用于app接口
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-12-22
 */
public interface ReportDutyAppService{

	/**
	 * 根据list集合，批量新增考核责任人记录，2016-12-22
	 * @param reportDutyList
	 * @return
	 */
	int batchAddReportsDutyByListApp(List<ReportDutyApp> reportDutyList);

	/**
	 * 查看近一周全市每一项监察下每一个责任区下责任人列表， 2017-04-17
	 * @param reportDutyApp
	 * @return
	 */
	List<ReportDutyApp> selectSupervisePeopleDetailsByBeanApp(ReportDutyApp reportDutyApp);


}
