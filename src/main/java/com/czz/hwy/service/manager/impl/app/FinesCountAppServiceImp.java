package com.czz.hwy.service.manager.impl.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.manager.app.FinesCountApp;
import com.czz.hwy.dao.manager.app.FinesCountAppDao;
import com.czz.hwy.service.manager.app.FinesCountAppService;
/**
 * 罚款总计业务层实现类,用于app接口
 * @author 张咏雪 2016-12-20
 * @version V1.0
 */
@Service
public class FinesCountAppServiceImp implements FinesCountAppService {
	
	@Autowired
	private FinesCountAppDao finesCountAppDao;//罚款总计数据层

	/**
	 * 根据bean获取罚款总计信息，2016-12-20
	 */
	public FinesCountApp getFinesCountAppByBeanApp(FinesCountApp finesCountApp) {
		return finesCountAppDao.getInfoByBean("getFinesCountAppByBeanApp", finesCountApp);
	}

	/**
	 * 更新罚款总计信息，2016-12-20
	 */
	public int updateFinesCountByBeanApp(FinesCountApp finesCountApp) {
		return finesCountAppDao.updateInfoByBean("updateFinesCountByBeanApp", finesCountApp);
	}

	/**
	 * 新增罚款总计记录，2016-12-20
	 */
	public int insertFinesAppByBeanApp(FinesCountApp finesCountApp) {
		return finesCountAppDao.insertInfo("insertFinesAppByBeanApp", finesCountApp);
	}



}
