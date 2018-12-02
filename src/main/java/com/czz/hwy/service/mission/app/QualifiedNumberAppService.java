package com.czz.hwy.service.mission.app;

import com.czz.hwy.bean.mission.app.QualifiedNumberApp;


/**
 * 五克五分钟次数统计的service接口
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-11-09
 */
public interface QualifiedNumberAppService{

	/**
	 * 根据bean获取五分钟统计记录，2016-11-09
	 * @param qualifiedNumberApp
	 * @return
	 */
	QualifiedNumberApp getQualifiedNumberByBeanApp(QualifiedNumberApp qualifiedNumberApp);

	/**
	 * 新增五分钟统计次数，2016-11-09
	 * @param qualifiedNumberApp
	 */
	int insertQulifiedNumberByBeanApp(QualifiedNumberApp qualifiedNumberApp);

	/**
	 * 更新五分钟统计次数，2016-11-09
	 * @param qualifiedNumberApp
	 * @return
	 */
	int updateQulifiedNumberByBeanApp(QualifiedNumberApp qualifiedNumberApp);
	
	
	
}
