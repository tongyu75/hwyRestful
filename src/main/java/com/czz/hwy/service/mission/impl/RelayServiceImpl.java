package com.czz.hwy.service.mission.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.mission.Relay;
import com.czz.hwy.dao.mission.RelayDao;
import com.czz.hwy.service.mission.RelayService;

/**
 * 替班人员的service实现类
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-09-29
 */
@Service
public class RelayServiceImpl implements RelayService {

	@Autowired
	private RelayDao relayDao;

	/**
	 * 根据员工ID和责任区ID，获取替班人员信息。2016-09-29
	 */
	public Relay getRelayByBean(Relay relay) {
		return relayDao.getInfoByBean("getRelayByBean", relay);
	}

	/**
	 * 新增一条替班人员记录,2016-09-29
	 */
	public int insertRelayByBean(Relay relay) {
		return relayDao.insertInfo("insertRelayByBean", relay);
	}

	/**
	 * 根据ID获取替班人员信息，2016-09-29
	 */
	public Relay getRelayById(String id) {
		return relayDao.getInfoById("getRelayById", id);
	}

	/**
	 * 物理删除一条替班人员记录，2016-09-29
	 */
	public int deleteRelayById(String id) {
		return relayDao.deleteInfoByPk("deleteRelayById", id);
	}

	/**
	 * 查询替班人员记录总条数，2016-09-29
	 */
	public int getRelayCountByBean(Relay relay) {
		return relayDao.getInfoCount("getRelayCountByBean", relay);
	}

	/**
	 * 查询替班人员记录集合，2016-09-29，分页
	 */
	public List<Relay> getRelayListByBean(Relay relay) {
		return relayDao.getInfoListByBean("getRelayListByBean", relay);
	}

	/**
	 * 查询替班人员记录集合，2016-09-29，不分页
	 */
	public List<Relay> getAllRelayListByBean(Relay relay) {
		return relayDao.getInfoListByBean("getAllRelayListByBean", relay);
	}

	/**
	 * 查询所有没有排班计划，且又不再替班表中的员工总条数，2016-09-30
	 */
	public int getNotRelayCountByBean(Relay relay) {
		return relayDao.getInfoCount("getNotRelayCountByBean", relay);
	}

	/**
	 * 查询所有没有排班计划，且又不再替班表中的员工信息集合，2016-09-30，不分页
	 */
	public List<Relay> getAllNotRelayListByBean(Relay relay) {
		return relayDao.getInfoListByBean("getAllNotRelayListByBean", relay);
	}
	
    /**
     * 根据责任区ID，获取替班人员列表信息。2016-10-17
     */
    public List<Relay> getRelayListByAreaId(Relay relay) {
        return relayDao.getInfoListByBean("getRelayByBean", relay);
    }
}
