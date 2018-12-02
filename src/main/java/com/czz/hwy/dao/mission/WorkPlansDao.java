package com.czz.hwy.dao.mission;

import java.util.List;
import java.util.Map;

import com.czz.hwy.base.BaseDao;
import com.czz.hwy.bean.mission.UserArea;
import com.czz.hwy.bean.mission.WorkPlans;

/**
 * 新的排班计划功能的dao接口
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-09-29
 */
public interface WorkPlansDao extends BaseDao<WorkPlans>{

	/**
	 * 批量新增排班计划，2016-09-30
	 * @param plansList
	 * @return
	 */
	int batchAddWorkPlans(List<WorkPlans> plansList);

	/**
	 * 获取某个责任区下的所有责任点列表，2016-10-01
	 * @param areaId
	 * @return
	 */
	List<Map<String, Object>> getPointListByAreaId(String areaId);

	/**
	 * 根据责任区ID和责任点ID，角色ID，往旧系统排班计划表添加数据。2016-10-10
	 * @param deleteMap
	 * @return
	 */
	int batchAddDutyPlansByMap(Map<String, Object> deleteMap);

	/**
	 * 根据责任区ID和责任点ID，角色ID，往旧系统排班计划表添加数据。2016-10-10
	 * @param deleteMap
	 * @return
	 */
	int batchAddDutyForBanciByMap(Map<String, Object> deleteMap);

	/**
	 * 根据员工ID集合，查询出新系统对应的排班计划集合，并将这些排班计划新增到旧系统排班计划表中去。2016-10-12
	 * @param changeEmployeeIds
	 * @return
	 */
	int batchAddDutyForBanciByIds(String changeEmployeeIds);

	/**
	 * 根据员工ID集合，查询出新系统对应的排班计划集合，并将这些排班计划新增到旧系统排班计划表中去。2016-10-12
	 * @param changeEmployeeIds
	 * @return
	 */
	int batchAddDutyPlansByIds(String changeEmployeeIds);

	/**
	 * 查询本责任区且不是替班人员的用户信息集合总数，2016-10-13
	 * @param workPlans
	 * @return
	 */
	List<UserArea> selectEmployeeList(WorkPlans workPlans);

	/**
	 * 批量更新检测员的轮班频率，2016-10-14
	 * @param workPlans
	 * @return
	 */
	int batchaUpdateJCTradeRate(WorkPlans workPlans);

	/**
	 * 根据责任区ID集合，角色ID，往旧系统排班计划表添加数据。2016-10-14
	 * @param deleteMap
	 * @return
	 */
	int batchAddJCDutyForBanciByMap(Map<String, Object> deleteMap);

	/**
	 * 根据责任区ID集合，角色ID，往旧系统排班计划表添加数据。2016-10-14
	 * @param deleteMap
	 * @return
	 */
	int batchAddJCDutyPlansByMap(Map<String, Object> deleteMap);

	/**
	 * 查询检测员总数，2016-11-10
	 * @param workPlans
	 * @return
	 */
	int selectJCEmployeeCount(WorkPlans workPlans);

	/**
	 * 查询检测员集合，2016-11-10
	 * @param workPlans
	 * @return
	 */
	List<UserArea> selectJCEmployeeList(WorkPlans workPlans);

	
	
}
