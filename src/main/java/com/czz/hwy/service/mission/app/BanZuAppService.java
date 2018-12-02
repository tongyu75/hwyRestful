package com.czz.hwy.service.mission.app;

import java.util.List;

import com.czz.hwy.bean.mission.BanZu;

/**
 * 班组维护功能业务层接口
 * 
 * @author 张咏雪 2016-10-18
 * @version V1.0
 */
public interface BanZuAppService {

	/**
	 * 查询所有被班次绑定的班组记录总条数，2016-10-18
	 * @param banZu
	 * @return
	 */
	public int selectBanZuForBanciCountByBean();

	/**
	 * 查询所有被班次绑定的班组记录集合，2016-10-18，不分页
	 * @param banZu
	 * @return
	 */
	public List<BanZu> selectBanZuForBanciListByBean();
}
