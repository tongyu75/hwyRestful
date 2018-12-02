package com.czz.hwy.service.mission;

import java.util.List;

import com.czz.hwy.bean.mission.Banci;

/**
 * 班次管理的service接口
 * 功能描述
 * @author 张咏雪
 * @company chnegzhongzhi
 * @createDate 2016-09-28
 */
public interface BanciService{

	/**
	 * 新增一条班次记录,2016-09-28
	 * @param bean
	 * @return
	 */
	public int insertBanci(Banci bean);
	
	/**
	 * 更新班次信息
	 * @param bean
	 * @return
	 */
	public int upddateBanciByBean(Banci bean);
	
	/**
	 * 根据条件获取一条班次信息
	 * @param bean
	 * @return
	 */
	public Banci getBanciByBean(Banci bean);
	
	/**
	 * 根据条件获取班次列表
	 * @param bean
	 * @return
	 */
	public List<Banci> getBanciList(Banci bean);
	
	/**
	 * 根据条件获取班次列表
	 * @param bean
	 * @return
	 */
	public List<Banci> getAllBanciList(Banci bean);
	
	/**
	 * 根据条件获取班次总数
	 * @param bean
	 * @return
	 */
	public int getBanciCount(Banci bean);

	/**
	 * 根据班组ID，查询班次列表，2016-09-28
	 * @param banzuId
	 * @return
	 */
	public List<Banci> selectBanciListByBanZuId(String banzuId);

	//----------------------------------------
	
	/**
	 * 根据班次序号获取班次集合，2016-09-28
	 * @param dutyNumber
	 * @return
	 */
	public List<Banci> getBanciListByNumber(String dutyNumber);

	/**
	 * 根据班组Id查询班次集合,2016-09-28
	 * @param string
	 * @return
	 */
	public List<Banci> getBanciListByBanZuId(String banZuId);

	/**
	 * 更新一条班次信息，2016-09-28
	 * @param banci
	 * @return
	 */
	public int updateBanci(Banci banci);

	/**
	 * 根据班次Id获取班次信息，2016-09-28
	 * @param banciId
	 * @return
	 */
	public Banci getBanciById(String banciId);

	/**
	 * 逻辑删除一条班次记录，2016-09-28
	 * @param banci
	 * @return
	 */
	public int updateBanciForDelete(Banci banci);

	/**
	 * 物理删除一条班次记录，2016-10-28
	 * @param banci
	 * @return
	 */
	public int deleteBanciByBean(Banci banci);
}
