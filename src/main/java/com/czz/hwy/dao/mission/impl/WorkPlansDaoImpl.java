package com.czz.hwy.dao.mission.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.czz.hwy.base.BaseDaoImpl;
import com.czz.hwy.bean.mission.UserArea;
import com.czz.hwy.bean.mission.WorkPlans;
import com.czz.hwy.dao.mission.WorkPlansDao;

/**
 * 新的排班计划功能dao的接口实现类
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-09-29
 */
@Repository
public class WorkPlansDaoImpl extends BaseDaoImpl<WorkPlans> implements WorkPlansDao {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	/**
	 * 批量新增排班计划，2016-09-30
	 */
	public int batchAddWorkPlans(List<WorkPlans> plansList) {
		return sqlSessionTemplate.insert("batchAddWorkPlans", plansList);
	}

	/**
	 * 获取某个责任区下的所有责任点列表,2016-10-01
	 */
	public List<Map<String, Object>> getPointListByAreaId(String areaId) {
		return sqlSessionTemplate.selectList("getPointListByAreaId", areaId);
	}

	/**
	 * 根据责任区ID和责任点ID，角色ID，往旧系统排班计划表添加数据。2016-10-10
	 */
	public int batchAddDutyPlansByMap(Map<String, Object> deleteMap) {
		return sqlSessionTemplate.insert("batchAddDutyPlansByMap", deleteMap);
	}

	/**
	 * 根据责任区ID和责任点ID，角色ID，往旧系统排班计划表添加数据。2016-10-10
	 */
	public int batchAddDutyForBanciByMap(Map<String, Object> deleteMap) {
		return sqlSessionTemplate.insert("batchAddDutyForBanciByMap", deleteMap);
	}

	/**
	 * 根据员工ID集合，查询出新系统对应的排班计划集合，并将这些排班计划新增到旧系统排班计划表中去。2016-10-12
	 */
	public int batchAddDutyForBanciByIds(String changeEmployeeIds) {
		return sqlSessionTemplate.insert("batchAddDutyForBanciByIds", changeEmployeeIds);
	}

	/**
	 * 根据员工ID集合，查询出新系统对应的排班计划集合，并将这些排班计划新增到旧系统排班计划表中去。2016-10-12
	 */
	public int batchAddDutyPlansByIds(String changeEmployeeIds) {
		return sqlSessionTemplate.insert("batchAddDutyPlansByIds", changeEmployeeIds);
	}

	/**
	 * 查询本责任区且不是替班人员的用户信息集合总数，2016-10-13
	 */
	public List<UserArea> selectEmployeeList(WorkPlans workPlans) {
		return sqlSessionTemplate.selectList("selectEmployeeList", workPlans);
	}

	/**
	 * 批量更新检测员的轮班频率，2016-10-14
	 */
	public int batchaUpdateJCTradeRate(WorkPlans workPlans) {
		return sqlSessionTemplate.update("batchaUpdateJCTradeRate", workPlans);
	}

	/**
	 * 根据责任区ID集合，角色ID，往旧系统排班计划表添加数据。2016-10-14
	 */
	public int batchAddJCDutyForBanciByMap(Map<String, Object> deleteMap) {
		return sqlSessionTemplate.insert("batchAddJCDutyForBanciByMap", deleteMap);
	}

	/**
	 * 根据责任区ID集合，角色ID，往旧系统排班计划表添加数据。2016-10-14
	 */
	public int batchAddJCDutyPlansByMap(Map<String, Object> deleteMap) {
		return sqlSessionTemplate.insert("batchAddJCDutyPlansByMap", deleteMap);
	}

	/**
	 * 查询检测员总数，2016-11-10
	 */
	public int selectJCEmployeeCount(WorkPlans workPlans) {
		return sqlSessionTemplate.selectOne("selectJCEmployeeCount", workPlans);
	}

	/**
	 * 查询检测员集合，2016-11-10
	 */
	public List<UserArea> selectJCEmployeeList(WorkPlans workPlans) {
		return sqlSessionTemplate.selectList("selectJCEmployeeList", workPlans);
	}


}
