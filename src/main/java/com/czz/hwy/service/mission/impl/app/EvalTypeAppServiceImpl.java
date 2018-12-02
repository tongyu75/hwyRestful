package com.czz.hwy.service.mission.impl.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.mission.app.EvalTypeApp;
import com.czz.hwy.dao.mission.app.EvalTypeAppDao;
import com.czz.hwy.service.mission.app.EvalTypeAppService;

/**
 * 考核分类的service实现类,用于app接口
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-11-09
 */
@Service
public class EvalTypeAppServiceImpl implements EvalTypeAppService {

	@Autowired
	private EvalTypeAppDao evalTypeAppDao;

	/**
	 * 根据考核类型ID获取考核类型详细信息，2016-11-09
	 */
	public EvalTypeApp getEvalTypeByBeanApp(EvalTypeApp evalTypeApp) {
		return evalTypeAppDao.getInfoByBean("getEvalTypeByBeanApp", evalTypeApp);
	}

	/**
	 * 根据type值，获取五克，五分钟，举报，监督对应的考核分类集合总条数，2016-11-09
	 */
	public int getEvalTypeCountByBeanApp(EvalTypeApp evalTypeApp) {
		return evalTypeAppDao.getInfoCount("getEvalTypeCountByBeanApp", evalTypeApp);
	}

	/**
	 * 根据type值，获取五克，五分钟，举报，监督对应的考核分类集合,不分页，2016-11-09
	 */
	public List<EvalTypeApp> getEvalTypeListByBeanApp(EvalTypeApp evalTypeApp) {
		return evalTypeAppDao.getInfoListByBean("getEvalTypeListByBeanApp", evalTypeApp);
	}
	
}
