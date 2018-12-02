package com.czz.hwy.service.manager.impl.app;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.manager.app.FinesDetailApp;
import com.czz.hwy.dao.manager.app.FinesDetailAppDao;
import com.czz.hwy.service.manager.app.FinesDetailAppService;
/**
 * 罚款详情业务层实现类,用于app接口
 * @author 张咏雪 2016-12-20
 * @version V1.0
 */
@Service
public class FinesDetailAppServiceImp implements FinesDetailAppService {
	
	@Autowired
	private FinesDetailAppDao finesDetailAppDao;//罚款详情数据层

	/**
	 * 新增罚款详情，2016-12-20
	 */
	public int inertFinesDetailAppByBeanApp(FinesDetailApp finesDetailApp) {
		return finesDetailAppDao.insertInfo("inertFinesDetailAppByBeanApp", finesDetailApp);
	}
	


}
