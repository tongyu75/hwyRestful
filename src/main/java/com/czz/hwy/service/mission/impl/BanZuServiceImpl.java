package com.czz.hwy.service.mission.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.mission.BanZu;
import com.czz.hwy.dao.mission.BanZuDao;
import com.czz.hwy.service.mission.BanZuService;

/**
 * 班组维护功能业务层接口
 * 
 @ author 张咏雪 2016-09-27
 * @version V1.0
 */
@Service
public class BanZuServiceImpl implements BanZuService {

    @Autowired
    private BanZuDao banZuDao;

    /**
     * 新增一条记录,2016-09-27
     */
	public int addBanZuByBean(BanZu banZu) {
		return banZuDao.insertInfo("addBanZuByBean", banZu);
	}

	/**
	 * 根据bean获取班组信息,2016-09-27
	 */
	public BanZu getBanZuByBean(BanZu banZu) {
		return banZuDao.getInfoByBean("getBanZuByBean", banZu);
	}

	/**
	 * 根据bean更新一条班组记录，2016-09-27
	 */
	public int updateBanZuByBean(BanZu banZu) {
		return banZuDao.updateInfoByBean("updateBanZuByBean", banZu);
	}

	/**
	 * 删除一条班组记录，2016-09-27
	 */
	public int deleteBanZuById(String banZuId) {
		return banZuDao.deleteInfoByPk("deleteBanZuById", banZuId);
	}

	/**
	 * 查询班组记录总条数(所有被班次绑定和没有被班次绑定的班组记录)，2016-09-27
	 */
	public int selectBanZuCountByBean(BanZu banZu) {
		return banZuDao.getInfoCount("selectBanZuCountByBean", banZu);
	}

	/**
	 * 查询班组记录集合(所有被班次绑定和没有被班次绑定的班组记录)，2016-09-27，分页
	 */
	public List<BanZu> selectBanZuListByBean(BanZu banZu) {
		return banZuDao.getInfoListByBean("selectBanZuListByBean", banZu);
	}

	/**
	 * 查询所有班组记录集合(所有被班次绑定和没有被班次绑定的班组记录)，2016-09-28，不分页 
	 */
	public List<BanZu> selectAllBanZuListByBean(BanZu banZu) {
		return banZuDao.getInfoListByBean("selectAllBanZuListByBean", banZu);
	}

	/**
	 * 查询所有被班次绑定的班组记录集合，2016-10-17，不分页
	 */
	public int selectBanZuForBanciCountByBean(BanZu banZu) {
		return banZuDao.getInfoCount("selectBanZuForBanciCountByBean", banZu);
	}

	/**
	 * 查询所有被班次绑定的班组记录集合，2016-10-17，不分页
	 */
	public List<BanZu> selectBanZuForBanciListByBean(BanZu banZu) {
		return banZuDao.getInfoListByBean("selectBanZuForBanciListByBean", banZu);
	}
    
   
}
