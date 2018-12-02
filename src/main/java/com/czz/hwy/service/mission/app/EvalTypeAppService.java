package com.czz.hwy.service.mission.app;

import java.util.List;

import com.czz.hwy.bean.mission.app.EvalTypeApp;


/**
 * 考核分类的service接口，用于app接口
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-11-09
 */
public interface EvalTypeAppService{

	/**
	 * 根据考核类型ID获取考核类型详细信息，2016-11-09
	 * @param evalTypeApp
	 * @return
	 */
	EvalTypeApp getEvalTypeByBeanApp(EvalTypeApp evalTypeApp);

	/**
	 * 根据type值，获取五克，五分钟，举报，监督对应的考核分类集合总条数，2016-11-09
	 * @param evalTypeApp
	 * @return
	 */
	int getEvalTypeCountByBeanApp(EvalTypeApp evalTypeApp);

	/**
	 * 根据type值，获取五克，五分钟，举报，监督对应的考核分类集合,不分页，2016-11-09
	 * @param evalTypeApp
	 * @return
	 */
	List<EvalTypeApp> getEvalTypeListByBeanApp(EvalTypeApp evalTypeApp);
	
	
}
