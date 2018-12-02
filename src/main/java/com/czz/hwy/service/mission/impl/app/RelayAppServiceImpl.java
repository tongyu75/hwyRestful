package com.czz.hwy.service.mission.impl.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.mission.app.RelayApp;
import com.czz.hwy.dao.mission.app.RelayAppDao;
import com.czz.hwy.service.mission.app.RelayAppService;

/**
 * 替班人员的service实现类
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-10-20
 */
@Service
public class RelayAppServiceImpl implements RelayAppService {

	@Autowired
	private RelayAppDao relayAppDao;

	/**
	 * 查询替班人员记录总条数，2016-10-20
	 */
	public int getRelayCountByBean(RelayApp relayApp) {
		return relayAppDao.getInfoCount("getRelayCountByBeanApp", relayApp);
	}

	/**
	 * 查询替班人员记录集合，2016-10-20，不分页
	 */
	public List<RelayApp> getAllRelayListByBean(RelayApp relayApp) {
		return relayAppDao.getInfoListByBean("getAllRelayListByBeanApp", relayApp);
	}
	
}
