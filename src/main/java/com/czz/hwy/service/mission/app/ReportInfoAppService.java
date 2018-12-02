package com.czz.hwy.service.mission.app;

import java.util.List;

import com.czz.hwy.bean.mission.app.ReportInfoApp;



/**
 * 新的举报功能的service接口，用于app接口
 * 功能描述
 * @author 张咏雪
 * @createDate 2017-04-24
 */
public interface ReportInfoAppService{

	/**
	 * 获取30分钟内该考核类型的举报记录列表(未处理的)，2017-04-24
	 * @param reportInfoApp
	 * @return
	 */
	List<ReportInfoApp> getExitsReportInfoAppByBeanApp(ReportInfoApp reportInfoApp);

	/**
	 * 新增举报信息，并将新增数据的主键ID返回,2017-04-25
	 * @param reportInfoApp
	 * @return
	 */
	int insertReportInfoAppByBeanApp(ReportInfoApp reportInfoApp);

	/**
	 * 环卫工：查看一段时间内自己举报的举报记录列表,2017-04-26
	 * @param reportInfoApp
	 * @return
	 */
	List<ReportInfoApp> getReportInfoListByBeanAppForHWG(ReportInfoApp reportInfoApp);

	/**
	 * 督察员：查看一段时间内所有的举报记录列表,2017-04-26
	 * @param reportInfoApp
	 * @return
	 */
	List<ReportInfoApp> getReportInfoListByBeanAppForDCY(ReportInfoApp reportInfoApp);

	/**
	 * 检测员：查看一段时间内自己举报的举报记录以及自己责任区环卫工举报的举报记录列表
	 * @param reportInfoApp
	 * @return
	 */
	List<ReportInfoApp> getReportInfoListByBeanAppForCJY(ReportInfoApp reportInfoApp);

	/**
	 * 考核员：查看一段时间内自己举报的举报记录以及自己负责的责任区下环卫工或检测员举报的季报记录列表,2017-04-26
	 * @param reportInfoApp
	 * @return
	 */
	List<ReportInfoApp> getReportInfoListByBeanAppForKHY(ReportInfoApp reportInfoApp);

	/**
	 * 根据查询条件查询举报信息，2017-04-26
	 * @param reportInfoApp
	 * @return
	 */
	ReportInfoApp getReportInfoAppByBeanApp(ReportInfoApp reportInfoApp);

	/**
	 * 根据bean更新举报信息，2017-04-26
	 * @param reportInfoApp
	 * @return
	 */
	int updateReportInfoAppByBeanApp(ReportInfoApp reportInfoApp);

	/**
	 * 处理完成：根据bean更新举报信息，2017-04-27
	 * @param reportInfoApp
	 * @return
	 */
	int updateReportInfoAppByBeanAppForManage(ReportInfoApp reportInfoApp);

	/**
	 * 查看近一周全市违规排污举报人详细信息， 2017-04-27
	 * @param reportInfoApp
	 * @return
	 */
	List<ReportInfoApp> selectReportInfoListByBeanApp(ReportInfoApp reportInfoApp);

	

}
