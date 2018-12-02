package com.czz.hwy.service.mission.impl.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.mission.BanZu;
import com.czz.hwy.dao.mission.app.BanZuAppDao;
import com.czz.hwy.service.mission.app.BanZuAppService;

/**
 * 班组维护功能业务层接口实现
 * 
 @ author 张咏雪 2016-09-27
 * @version V1.0
 */
@Service
public class BanZuAppServiceImpl implements BanZuAppService {

    @Autowired
    private BanZuAppDao banZuAppDao;

	/**
	 * 查询所有被班次绑定的班组记录集合，2016-10-18，不分页
	 */
	public int selectBanZuForBanciCountByBean() {
		return banZuAppDao.getInfoCount("selectBanZuForBanciCountByBeanApp");
	}

	/**
	 * 查询所有被班次绑定的班组记录集合，2016-10-18，不分页
	 */
	public List<BanZu> selectBanZuForBanciListByBean() {
		return banZuAppDao.getAllInfo("selectBanZuForBanciListByBeanApp");
	}
    
   
}
