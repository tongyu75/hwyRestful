package com.czz.hwy.service.mission.app;

import java.util.List;

import com.czz.hwy.bean.mission.app.ReportsApp;


/**
 * 监察举报考核项目的service接口，用于app接口
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-12-22
 */
public interface ReportsAppService{

	/**
	 * 新增监察考核任务，并将新增数据的主键ID返回,2016-12-22
	 * @param reportsApp
	 * @return
	 */
	int insertSuperviseByBeanApp(ReportsApp reportsApp);

	/**
	 * 查询某个责任区某个时间段内的监察考核总条数，2016-12-23
	 * @param reportsApp
	 * @return
	 */
	int getSuperviseCountByBeanApp(ReportsApp reportsApp);

	/**
	 * 查询某个责任区某个时间段内的监察考核集合，2016-12-23，不分页
	 * @param reportsApp
	 * @return
	 */
	List<ReportsApp> getSuperviseListByBeanApp(ReportsApp reportsApp);

	/**
	 * 获取30分钟内该考核类型的举报记录列表，2016-12-23
	 * @param reportsApp
	 * @return
	 */
	List<ReportsApp> getExitsReportsAppByBeanApp(ReportsApp reportsApp);

	/**
	 * 新增举报考核任务，并将新增数据的主键ID返回,2016-12-22
	 * @param reportsApp
	 * @return
	 */
	int insertReportsAppByBeanApp(ReportsApp reportsApp);

	/**
	 * 查询某个时间段内的自己提交的举报记录集合，2016-12-23，不分页
	 * @param reportsApp
	 * @return
	 */
	List<ReportsApp> getReportsListByBeanApp(ReportsApp reportsApp);

	/**
	 * 查看近一周全市违规排污举报人详细信息， 2017-04-17
	 * @param reportsApp
	 * @return
	 */
	List<ReportsApp> selectReportDetailsByBeanApp(ReportsApp reportsApp);

}
