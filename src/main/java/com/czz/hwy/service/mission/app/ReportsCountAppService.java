package com.czz.hwy.service.mission.app;

import java.util.List;

import com.czz.hwy.bean.mission.app.ReportsCountApp;



/**
 * 监督举报统计的service接口
 * 功能描述
 * @author 张咏雪
 * @createDate 2017-04-17
 */
public interface ReportsCountAppService{

	/**
	 * 查看近一周全市违规排污举报统计情况， 2017-04-17
	 * @param reportsCountApp
	 * @return
	 */
	List<ReportsCountApp> selectReportStatisticsByBeanApp(ReportsCountApp reportsCountApp);

	/**
	 * 查看近一周全市劳动纪律督察统计情况， 2017-04-17
	 * @param reportsCountApp
	 * @return
	 */
	List<ReportsCountApp> selectSuperviseStatisticsByBeanApp(ReportsCountApp reportsCountApp);

	/**
	 * 查看近一周全市未按规定路线作业细化表，即近一周每种监察分类关于每个责任区不合格统计次数， 2017-04-17
	 * @param reportsCountApp
	 * @return
	 */
	List<ReportsCountApp> selectSuperviseAreaDetailsByBeanApp(ReportsCountApp reportsCountApp);

	
}
