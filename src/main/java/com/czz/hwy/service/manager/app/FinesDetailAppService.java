package com.czz.hwy.service.manager.app;

import org.springframework.stereotype.Service;

import com.czz.hwy.bean.manager.app.FinesDetailApp;

@Service
/**
 * 罚款详情业务层接口,用于app接口
 * @author  张咏雪 2016-12-20
 * @version V1.0
 */
public interface FinesDetailAppService {

	/**
	 * 新增罚款详情，2016-12-20
	 * @param finesDetailApp
	 * @return
	 */
	int inertFinesDetailAppByBeanApp(FinesDetailApp finesDetailApp);
	
}
