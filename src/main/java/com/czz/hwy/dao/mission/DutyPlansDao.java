package com.czz.hwy.dao.mission;


import java.util.List;
import java.util.Map;

import com.czz.hwy.base.BaseDao;
import com.czz.hwy.bean.mission.DutyPlans;

public interface DutyPlansDao extends BaseDao<DutyPlans> {
	
	/**
	 * 批量插入排班数据 主要是为考核员的排班计划
	 * @param dPlans
	 */
	void insertDutyPlanss(List<DutyPlans> dPlans);
	
	List<DutyPlans> getListEmployeeIdsFromDutyPlans(Map<String, Object> map);
	
	List<DutyPlans> getRecentlyDutyPlansByEmployeeid(int employeeId);
	
	/**
	 * 批量更显排班计划
	 * @param list
	 * @return
	 */
	public int beatchUpdateForDutyPlans(List<DutyPlans> list);

	/**
	 *  批量删除排班计划，即，批量更新排班计划状态为2，2016-09-28，又改成：即批量物理删除排版计划，2016-10-28
	 * @param list
	 */
	int beatchDeleteForDutyPlans(List<DutyPlans> list);
	

	/*List<DutyPlans> getDutyPlansForGramByEmployeeIdList(int emplyeeId, Date checkTime);

	List<DutyPlans> getInfoListByBean(DutyPlans dutyPlans);

	List<DutyPlans> getInfoListByBean(int employeeId, Date attendanceDate);

	*//**
	 * @param dutyPlans
	 * @return
	 *//*
	int getCountByDutyPlans(DutyPlans dutyPlans);

	*//**
	 * @param dutyPlans
	 * @return
	 *//*
	List<DutyPlans> getListDutyPlans(DutyPlans dutyPlans);

	*//**
	 * @param map
	 * @return
	 *//*
	

	*//**
	 * @param employeeId
	 * @return
	 *//*
	

	
	
	

	 *//**
     * 获取空闲的员工
     * @param employeeId
     *//*
	List<DutyPlans> getDutyPlansCoverWorkName(int employeeId);
	
    *//**
     * 更新代班人员到排班计划表
     * @param dutyPlans
     *//*
    int updateDutyPlansByCoverWork(DutyPlans dutyPlans);*/
}
