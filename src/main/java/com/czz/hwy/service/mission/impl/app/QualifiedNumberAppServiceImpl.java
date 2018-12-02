package com.czz.hwy.service.mission.impl.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.mission.app.QualifiedNumberApp;
import com.czz.hwy.dao.mission.app.QualifiedNumberAppDao;
import com.czz.hwy.service.mission.app.QualifiedNumberAppService;

/**
 *五克五分钟次数统计的service实现类
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-11-09
 */
@Service
public class QualifiedNumberAppServiceImpl implements QualifiedNumberAppService {

	@Autowired
	private QualifiedNumberAppDao qualifiedNumberAppDao;

	/**
	 * 根据bean获取五分钟统计记录，c
	 */
	public QualifiedNumberApp getQualifiedNumberByBeanApp(QualifiedNumberApp qualifiedNumberApp) {
		return qualifiedNumberAppDao.getInfoByBean("getQualifiedNumberByBeanApp", qualifiedNumberApp);
	}

	/**
	 * 新增五分钟统计次数，2016-11-09
	 */
	public int insertQulifiedNumberByBeanApp(QualifiedNumberApp qualifiedNumberApp) {
		return qualifiedNumberAppDao.insertInfo("insertQulifiedNumberByBeanApp", qualifiedNumberApp);
	}

	/**
	 * 更新五分钟统计次数，2016-11-09
	 */
	public int updateQulifiedNumberByBeanApp(QualifiedNumberApp qualifiedNumberApp) {
		return qualifiedNumberAppDao.updateInfoByBean("updateQulifiedNumberByBeanApp", qualifiedNumberApp);
	}
	
	
}
