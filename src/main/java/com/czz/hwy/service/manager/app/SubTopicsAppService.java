package com.czz.hwy.service.manager.app;

import org.springframework.stereotype.Service;

import com.czz.hwy.bean.manager.app.SubTopicsApp;
@Service
/**
 * 订阅信息业务层接口,用于app接口
 * @author  张咏雪 2016-12-20
 * @version V1.0
 */
public interface SubTopicsAppService {
	/*
	 * 通过userId获得当前订阅信息，2016-12-20
	 */
	public SubTopicsApp getSubTopicsAppByBeanApp(SubTopicsApp subTopicsApp);

}
