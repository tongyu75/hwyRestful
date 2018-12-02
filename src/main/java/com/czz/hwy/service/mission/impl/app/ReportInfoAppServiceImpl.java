package com.czz.hwy.service.mission.impl.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.mission.app.ReportInfoApp;
import com.czz.hwy.dao.mission.app.ReportInfoAppDao;
import com.czz.hwy.service.mission.app.ReportInfoAppService;

/**
 * 新的举报功能的service实现类，用于app接口
 * 功能描述
 * @author 张咏雪
 * @createDate 2017-04-24
 */
@Service
public class ReportInfoAppServiceImpl implements ReportInfoAppService {
	
	@Autowired
	private ReportInfoAppDao reportInfoAppDao;//新的举报数据层

	/**
	 * 获取30分钟内该考核类型的举报记录列表(未处理的)，2017-04-24
	 */
	public List<ReportInfoApp> getExitsReportInfoAppByBeanApp(ReportInfoApp reportInfoApp) {
		return reportInfoAppDao.getInfoListByBean("getExitsReportInfoAppByBeanApp", reportInfoApp);
	}

	/**
	 * 新增举报信息，并将新增数据的主键ID返回,2017-04-25
	 */
	public int insertReportInfoAppByBeanApp(ReportInfoApp reportInfoApp) {
		return reportInfoAppDao.insertInfo("insertReportInfoAppByBeanApp", reportInfoApp);
	}

	/**
	 * 环卫工：查看一段时间内自己举报的举报记录列表,2017-04-26
	 */
	public List<ReportInfoApp> getReportInfoListByBeanAppForHWG(ReportInfoApp reportInfoApp) {
		return reportInfoAppDao.getInfoListByBean("getReportInfoListByBeanAppForHWG", reportInfoApp);
	}

	/**
	 * 督察员：查看一段时间内所有的举报记录列表,2017-04-26
	 */
	public List<ReportInfoApp> getReportInfoListByBeanAppForDCY(ReportInfoApp reportInfoApp) {
		return reportInfoAppDao.getInfoListByBean("getReportInfoListByBeanAppForDCY", reportInfoApp);
	}

	/**
	 * 检测员：查看一段时间内自己举报的举报记录以及自己责任区环卫工举报的举报记录列表,2017-04-26
	 */
	public List<ReportInfoApp> getReportInfoListByBeanAppForCJY(ReportInfoApp reportInfoApp) {
		return reportInfoAppDao.getInfoListByBean("getReportInfoListByBeanAppForCJY", reportInfoApp);
	}

	/**
	 * 考核员：查看一段时间内自己举报的举报记录以及自己负责的责任区下环卫工或检测员举报的季报记录列表,2017-04-26
	 */
	public List<ReportInfoApp> getReportInfoListByBeanAppForKHY(ReportInfoApp reportInfoApp) {
		return reportInfoAppDao.getInfoListByBean("getReportInfoListByBeanAppForKHY", reportInfoApp);
	}

	/**
	 * 根据查询条件查询举报信息，2017-04-26
	 */
	public ReportInfoApp getReportInfoAppByBeanApp(ReportInfoApp reportInfoApp) {
		return reportInfoAppDao.getInfoByBean("getReportInfoAppByBeanApp", reportInfoApp);
	}

	/**
	 * 根据bean更新举报信息，2017-04-26
	 */
	public int updateReportInfoAppByBeanApp(ReportInfoApp reportInfoApp) {
		return reportInfoAppDao.updateInfoByBean("updateReportInfoAppByBeanApp", reportInfoApp);
	}

	/**
	 * 处理完成：根据bean更新举报信息，2017-04-27
	 */
	public int updateReportInfoAppByBeanAppForManage(ReportInfoApp reportInfoApp) {
		return reportInfoAppDao.updateInfoByBean("updateReportInfoAppByBeanAppForManage", reportInfoApp);
	}

	/**
	 * 查看近一周全市违规排污举报人详细信息， 2017-04-27
	 */
	public List<ReportInfoApp> selectReportInfoListByBeanApp(ReportInfoApp reportInfoApp) {
		return reportInfoAppDao.getInfoListByBean("selectReportInfoListByBeanApp", reportInfoApp);
	}

	
}
