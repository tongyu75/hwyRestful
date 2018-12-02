package com.czz.hwy.service.mission.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.mission.Banci;
import com.czz.hwy.dao.mission.BanciDao;
import com.czz.hwy.service.mission.BanciService;

/**
 * 管理班次的service实现类
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-09-28
 */
@Service
public class BanciServiceImpl implements BanciService {

	@Autowired
	private BanciDao banciDao;
	
	/**
	 * 新增一条班次记录,2016-09-28
	 */
	public int insertBanci(Banci bean) {
		return banciDao.insertInfo("insertBanci", bean);
	}

	/**
	 * 更新班次信息
	 */
	public int upddateBanciByBean(Banci bean) {
		return banciDao.updateInfoByBean("updateBanciByBean", bean);
	}

	/**
	 * 根据条件获取一个班次信息
	 */
	public Banci getBanciByBean(Banci bean) {
		return banciDao.getInfoByBean("getBanciByBean", bean);
	}

	/**
	 * 根据条件获取班次列表
	 */
	public List<Banci> getBanciList(Banci bean) {
		return banciDao.getInfoListByBean("getBanciList", bean);
	}
	
	/**
	 * 根据条件获取班次列表
	 */
	public List<Banci> getAllBanciList(Banci bean) {
		return banciDao.getInfoListByBean("getAllBanciList", bean);
	}

	/**
	 * 根据条件获取班次总数
	 */
	public int getBanciCount(Banci bean) {
		return banciDao.getInfoCount("getBanciCount", bean);
	}
	
	/**
	 * 根据班组ID，查询班次列表，2016-09-28
	 */
	public List<Banci> selectBanciListByBanZuId(String banzuId) {
		return banciDao.getInfoListById("selectBanciListByBanZuId", banzuId);
	}

	/**
	 * 根据班次序号获取班次集合，2016-09-28
	 */
	public List<Banci> getBanciListByNumber(String dutyNumber) {
		return banciDao.getInfoListById("getBanciListByNumber", dutyNumber);
	}

	/**
	 * 根据班组Id查询班次集合,2016-09-28
	 */
	public List<Banci> getBanciListByBanZuId(String banZuId) {
		return banciDao.getInfoListById("getBanciListByBanZuId", banZuId);
	}

	/**
	 * 更新一条班次信息，2016-09-28
	 */
	public int updateBanci(Banci banci) {
		return banciDao.updateInfoByBean("updateBanci", banci);
	}

	/**
	 * 根据班次Id获取班次信息，2016-09-28
	 */
	public Banci getBanciById(String banciId) {
		return banciDao.getInfoById("getBanciById", banciId);
	}

	/**
	 * 逻辑删除一条班次记录，2016-09-28
	 */
	public int updateBanciForDelete(Banci banci) {
		return banciDao.updateInfoByBean("updateBanciForDelete", banci);
	}

	/**
	 * 物理删除一条班次记录，2016-10-28
	 */
	public int deleteBanciByBean(Banci banci) {
		return banciDao.deleteInfoByBean("deleteBanciByBean", banci);
	}

}
