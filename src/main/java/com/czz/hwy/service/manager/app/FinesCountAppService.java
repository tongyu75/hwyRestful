package com.czz.hwy.service.manager.app;

import org.springframework.stereotype.Service;

import com.czz.hwy.bean.manager.app.FinesCountApp;

@Service
/**
 * 罚款总计业务层接口,用于app接口
 * @author  张咏雪 2016-12-20
 * @version V1.0
 */
public interface FinesCountAppService {

	/**
	 * 根据bean获取罚款总计信息，2016-12-20
	 * @param finesCountApp
	 * @return
	 */
	FinesCountApp getFinesCountAppByBeanApp(FinesCountApp finesCountApp);

	/**
	 * 更新罚款总计信息，2016-12-20
	 * @param finesCountApp
	 * @return
	 */
	int updateFinesCountByBeanApp(FinesCountApp finesCountApp);

	/**
	 * 新增罚款总计记录，2016-12-20
	 * @param finesCountApp
	 * @return
	 */
	int insertFinesAppByBeanApp(FinesCountApp finesCountApp);

}
