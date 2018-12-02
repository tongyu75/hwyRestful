package com.czz.hwy.service.mission.app;

import java.util.List;

import com.czz.hwy.bean.mission.app.RelayApp;

/**
 * 替班人员的service接口
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-10-20
 */
public interface RelayAppService{

	/**
	 * 查询替班人员记录总条数，2016-10-20
	 * @param relay
	 * @return
	 */
	int getRelayCountByBean(RelayApp relayApp);

	/**
	 * 查询替班人员记录集合，2016-09-29，不分页
	 * @param relay
	 * @return
	 */
	List<RelayApp> getAllRelayListByBean(RelayApp relayApp);
}
