package com.czz.hwy.service.mission;

import java.util.List;

import org.springframework.stereotype.Service;

import com.czz.hwy.bean.mission.BanZu;

@Service
/**
 * 班组维护功能业务层接口
 * 
 * @author 张咏雪 2016-09-27
 * @version V1.0
 */
public interface BanZuService {

	/**
	 * 根据bean新增一条记录,2016-09-27
	 * @param banZu
	 * @return
	 */
	public int addBanZuByBean(BanZu banZu);

	/**
	 * 根据bean获取班组信息,2016-09-27
	 * @param banZu
	 * @return
	 */
	public BanZu getBanZuByBean(BanZu banZu);

	/**
	 * 根据bean更新一条班组记录，2016-09-27
	 * @param banZu
	 * @return
	 */
	public int updateBanZuByBean(BanZu banZu);
	
	/**
	 * 根据主键ID删除一条班组记录，2016-09-27,
	 * @param banZu
	 * @return
	 */
	public int deleteBanZuById(String banZuId);

	/**
	 * 查询班组记录总条数(所有被班次绑定和没有被班次绑定的班组记录)，2016-09-27
	 * @param banZu
	 * @return
	 */
	public int selectBanZuCountByBean(BanZu banZu);

	/**
	 * 查询班组记录集合(所有被班次绑定和没有被班次绑定的班组记录)，2016-09-27，分页
	 * @param banZu
	 * @return
	 */
	public List<BanZu> selectBanZuListByBean(BanZu banZu);

	/**
	 * 查询班组记录集合(所有被班次绑定和没有被班次绑定的班组记录)，2016-09-28，不分页
	 * @param banZu
	 * @return
	 */
	public List<BanZu> selectAllBanZuListByBean(BanZu banZu);

	/**
	 * 查询所有被班次绑定的班组记录总条数，2016-10-17
	 * @param banZu
	 * @return
	 */
	public int selectBanZuForBanciCountByBean(BanZu banZu);

	/**
	 * 查询所有被班次绑定的班组记录集合，2016-10-17，不分页
	 * @param banZu
	 * @return
	 */
	public List<BanZu> selectBanZuForBanciListByBean(BanZu banZu);
}
