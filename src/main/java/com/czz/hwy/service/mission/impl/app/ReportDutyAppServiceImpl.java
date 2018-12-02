package com.czz.hwy.service.mission.impl.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.mission.app.ReportDutyApp;
import com.czz.hwy.dao.mission.app.ReportDutyAppDao;
import com.czz.hwy.service.mission.app.ReportDutyAppService;

/**
 * 监察举报考核项目责任人的service实现类，用于app接口
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-12-22
 */
@Service
public class ReportDutyAppServiceImpl implements ReportDutyAppService {
	
	@Autowired
	private ReportDutyAppDao reportDutyAppDao;//监察举报责任人数据层

	/**
	 * 根据list集合，批量新增考核责任人记录，2016-12-22
	 */
	public int batchAddReportsDutyByListApp(List<ReportDutyApp> reportDutyList) {
		return reportDutyAppDao.insertInfo("batchAddReportsDutyByListApp", reportDutyList);
	}

	/**
	 * 查看近一周全市每一项监察下每一个责任区下责任人列表， 2017-04-17
	 */
	public List<ReportDutyApp> selectSupervisePeopleDetailsByBeanApp(ReportDutyApp reportDutyApp) {
		return reportDutyAppDao.getInfoListByBean("selectSupervisePeopleDetailsByBeanApp", reportDutyApp);
	}

}
