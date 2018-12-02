package com.czz.hwy.dao.mission.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.czz.hwy.base.BaseDaoImpl;
import com.czz.hwy.bean.mission.DutyPlans;
import com.czz.hwy.dao.mission.DutyPlansDao;
@Repository
public class DutyPlansDaoImpl extends BaseDaoImpl<DutyPlans> implements DutyPlansDao {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	/**
	 * 批量更新排班计划
	 */
	public int beatchUpdateForDutyPlans(List<DutyPlans> list) {
		return sqlSessionTemplate.update("beatchUpdateForDutyPlans", list);
	}
	
	public void insertDutyPlanss(List<DutyPlans> dPlans) {
		sqlSessionTemplate.insert("insertDutyPlanss", dPlans);
	}
	
	public List<DutyPlans> getListEmployeeIdsFromDutyPlans(
			Map<String, Object> map) {
		return sqlSessionTemplate.selectList("getListEmployeeIdsFromDutyPlans", map);
	}
	
	public List<DutyPlans> getRecentlyDutyPlansByEmployeeid(int employeeId) {
		return sqlSessionTemplate.selectList("getRecentlyDutyPlansByEmployeeid", employeeId);
	}
	
	/**
	 * 批量进行删除，即批量更新状态 为 2 ,2016-09-28，又改成：即批量物理删除排版计划，2016-10-28
	 */
	public int beatchDeleteForDutyPlans(List<DutyPlans> list) {
//		return sqlSessionTemplate.update("beatchDeleteForDutyPlans", list);
		return sqlSessionTemplate.delete("beatchDeleteForDutyPlans", list);
	}

}
