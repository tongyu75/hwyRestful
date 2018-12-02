package com.czz.hwy.service.manager.impl.app;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.manager.app.SubTopicsApp;
import com.czz.hwy.dao.manager.app.SubTopicsAppDao;
import com.czz.hwy.service.manager.app.SubTopicsAppService;
/**
 * 获取订阅信息业务层实现类,用于app接口
 * 
 * @author 张咏雪 2016-12-20
 * @version V1.0
 */
@Service
public class SubTopicsAppServiceImp implements SubTopicsAppService {
	//订阅信息持久层接口
	@Autowired
	private SubTopicsAppDao subTopicsAppDao;
	
	/*
	 * 通过userId获得当前订阅信息，2016-12-20
	 */
	public SubTopicsApp getSubTopicsAppByBeanApp(SubTopicsApp subTopicsApp) {
		return subTopicsAppDao.getInfoByBean("getSubTopicsAppByBeanApp", subTopicsApp);
	}


}
