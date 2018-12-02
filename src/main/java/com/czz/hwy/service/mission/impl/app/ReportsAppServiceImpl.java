package com.czz.hwy.service.mission.impl.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.mission.app.ReportsApp;
import com.czz.hwy.dao.mission.app.ReportsAppDao;
import com.czz.hwy.service.mission.app.ReportsAppService;

/**
 * 监察举报考核项目的service实现类，用于app接口
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-12-22
 */
@Service
public class ReportsAppServiceImpl implements ReportsAppService {
	
	@Autowired
	private ReportsAppDao reportsAppDao;//监察举报数据层

	/**
	 * 新增监察考核任务，并将新增数据的主键ID返回,2016-12-22
	 */
	public int insertSuperviseByBeanApp(ReportsApp reportsApp) {
		return reportsAppDao.insertInfo("insertSuperviseByBeanApp", reportsApp);
	}

	/**
	 * 查询某个责任区某个时间段内的监察考核总条数，2016-12-23
	 */
	public int getSuperviseCountByBeanApp(ReportsApp reportsApp) {
		return reportsAppDao.getInfoCount("getSuperviseCountByBeanApp", reportsApp);
	}

	/**
	 * 查询某个责任区某个时间段内的监察考核集合，2016-12-23，不分页
	 */
	public List<ReportsApp> getSuperviseListByBeanApp(ReportsApp reportsApp) {
		return reportsAppDao.getInfoListByBean("getSuperviseListByBeanApp", reportsApp);
	}

	/**
	 * 获取30分钟内该考核类型的举报记录列表，2016-12-23
	 */
	public List<ReportsApp> getExitsReportsAppByBeanApp(ReportsApp reportsApp) {
		return reportsAppDao.getInfoListByBean("getExitsReportsAppByBeanApp", reportsApp);
	}

	/**
	 * 新增举报考核任务，并将新增数据的主键ID返回,2016-12-22
	 */
	public int insertReportsAppByBeanApp(ReportsApp reportsApp) {
		return reportsAppDao.insertInfo("insertSuperviseByBeanApp", reportsApp);
	}

	/**
	 * 查询某个时间段内的自己提交的举报记录集合，2016-12-23，不分页
	 */
	public List<ReportsApp> getReportsListByBeanApp(ReportsApp reportsApp) {
		return reportsAppDao.getInfoListByBean("getReportsListByBeanApp", reportsApp);
	}

	/**
	 * 查看近一周全市违规排污举报人详细信息， 2017-04-17
	 */
	public List<ReportsApp> selectReportDetailsByBeanApp(ReportsApp reportsApp) {
		return reportsAppDao.getInfoListByBean("selectReportDetailsByBeanApp", reportsApp);
	}

}
