package com.czz.hwy.service.mission;

import java.util.List;

import com.czz.hwy.bean.mission.Relay;

/**
 * 替班人员的service接口
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-09-29
 */
public interface RelayService{

	/**
	 * 根据员工ID和责任区ID，获取替班人员信息。2016-09-29
	 * @param relay
	 * @return
	 */
	Relay getRelayByBean(Relay relay);

	/**
	 * 新增一条替班人员记录,2016-09-29
	 * @param relay
	 * @return
	 */
	int insertRelayByBean(Relay relay);

	/**
	 * 根据ID获取替班人员信息，2016-09-29
	 * @param id
	 * @return
	 */
	Relay getRelayById(String id);

	/**
	 * 物理删除一条替班人员记录，2016-09-29
	 * @param id
	 * @return
	 */
	int deleteRelayById(String id);

	/**
	 * 查询替班人员记录总条数，2016-09-29
	 * @param relay
	 * @return
	 */
	int getRelayCountByBean(Relay relay);

	/**
	 * 查询替班人员记录集合，2016-09-29，分页
	 * @param relay
	 * @return
	 */
	List<Relay> getRelayListByBean(Relay relay);

	/**
	 * 查询替班人员记录集合，2016-09-29，不分页
	 * @param relay
	 * @return
	 */
	List<Relay> getAllRelayListByBean(Relay relay);

	/**
	 * 查询所有没有排班计划，且又不再替班表中的员工总条数，2016-09-30
	 * @param relay
	 * @return
	 */
	int getNotRelayCountByBean(Relay relay);

	/**
	 * 查询所有没有排班计划，且又不再替班表中的员工信息集合，2016-09-30，不分页
	 * @param relay
	 * @return
	 */
	List<Relay> getAllNotRelayListByBean(Relay relay);

    /**
     * 根据责任区ID，获取替班人员列表信息。2016-10-17
     */
    public List<Relay> getRelayListByAreaId(Relay relay);
}
