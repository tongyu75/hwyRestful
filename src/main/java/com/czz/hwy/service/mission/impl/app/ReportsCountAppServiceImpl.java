package com.czz.hwy.service.mission.impl.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.mission.app.ReportsCountApp;
import com.czz.hwy.dao.mission.app.ReportsCountAppDao;
import com.czz.hwy.service.mission.app.ReportsCountAppService;

/**
 * 监督举报统计的service实现类
 * 功能描述
 * @author 张咏雪
 * @createDate 2017-04-17
 */
@Service
public class ReportsCountAppServiceImpl implements ReportsCountAppService {

	@Autowired
	private ReportsCountAppDao reportsCountAppDao;

	/**
	 * 查看近一周全市违规排污举报统计情况， 2017-04-17
	 */
	public List<ReportsCountApp> selectReportStatisticsByBeanApp(ReportsCountApp reportsCountApp) {
		return reportsCountAppDao.getInfoListByBean("selectReportStatisticsByBeanApp", reportsCountApp);
	}

	/**
	 * 查看近一周全市劳动纪律督察统计情况， 2017-04-17
	 */
	public List<ReportsCountApp> selectSuperviseStatisticsByBeanApp(ReportsCountApp reportsCountApp) {
		return reportsCountAppDao.getInfoListByBean("selectSuperviseStatisticsByBeanApp", reportsCountApp);
	}

	/**
	 * 查看近一周全市未按规定路线作业细化表，即近一周每种监察分类关于每个责任区不合格统计次数， 2017-04-17
	 */
	public List<ReportsCountApp> selectSuperviseAreaDetailsByBeanApp(ReportsCountApp reportsCountApp) {
		return reportsCountAppDao.getInfoListByBean("selectSuperviseAreaDetailsByBeanApp", reportsCountApp);
	}

	
	
}
