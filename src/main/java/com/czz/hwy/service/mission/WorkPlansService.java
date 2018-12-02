package com.czz.hwy.service.mission;

import java.util.List;
import java.util.Map;

import com.czz.hwy.bean.mission.UserArea;
import com.czz.hwy.bean.mission.WorkPlans;


/**
 * 新的排班计划功能的service接口
 * 功能描述
 * @author 张咏雪
 * @company chnegzhongzhi
 * @createDate 2016-09-29
 */
public interface WorkPlansService{

	/**
	 * 根据责任区Id和员工Id获取排班计划列表，2016-09-29
	 * @param workPlans
	 * @return
	 */
	List<WorkPlans> getWorkPlansListByBean(WorkPlans workPlans);

	/**
	 * 根据责任区和责任点删除排班计划。2016-09-30
	 * @param deleteMap
	 * @return
	 */
	int deleteWorkPlansByMap(Map<String, Object> deleteMap);

	/**
	 * 批量新增排班计划，2016-09-30
	 * @param plansList
	 * @return
	 */
	int batchAddWorkPlans(List<WorkPlans> plansList);

	/**
	 * 获取排班计划总条数，按责任点查询  2016-10-01
	 * @param workPlans
	 * @return
	 */
	int getWorkPlansCount(WorkPlans workPlans);

	/**
	 * 查询排班计划集合，按责任点分页 2016-10-01
	 * @param workPlans
	 * @return
	 */
	List<WorkPlans> getWorkPlansList(WorkPlans workPlans);

	/**
	 * 获取某个责任区下的所有责任点列表,2016-10-01
	 * @param areaId
	 * @return
	 */
	List<Map<String, Object>> getPointListByAreaId(String areaId);

	/**
	 * 根据责任区ID和责任点ID集合，删除旧系统排班计划表数据,2016-10-09
	 * @param deleteMap
	 * @return
	 */
	int deleteDutyForBanciByMap(Map<String, Object> deleteMap);

	/**
	 * 根据责任区ID和责任点ID集合，删除旧系统排班计划表数据,2016-10-09
	 * @param deleteMap
	 * @return
	 */
	int deleteDutyPlansByMap(Map<String, Object> deleteMap);

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
	 * 根据班组ID，查询出排班计划集合，2016-10-10
	 * @param banzuId
	 * @return
	 */
	List<WorkPlans> selectWorkPlansListByBanZuId(String banzuId);

	/**
	 * 查询出【当前日期前30分钟，当前日期】需要换班的排班计划，2016-10-11
	 * @param map
	 * @return
	 */
	List<WorkPlans> getWorkPlansListByMap(Map<String, Object> map);

	/**
	 * 获取某人员的排班计划集合，2016-10-12
	 * @param employeeId
	 * @return
	 */
	List<WorkPlans> getWorkPlansListByEmployeeId(String employeeId);

	/**
	 * 根据员工ID集合，删除新系统排班计划对应的数据，2016-10-12
	 * @param deleteEmployeeIds
	 */
	int deleteWorPlansByEmployeeIds(String deleteEmployeeIds);

	/**
	 * 根据员工ID集合，删除旧系统排班计划对应的数据，2016-10-12,目前设置为逻辑删除,以后要改成物理删除,现在已经改成物理删除
	 * @param deleteEmployeeIds
	 * @return
	 */
	int deleteDutyForBanciByIds(Map<String, Object> deleteMap);

	/**
	 * 根据员工ID集合，删除旧系统排班计划对应的数据，2016-10-12,目前设置为逻辑删除,以后要改成物理删除,现在已经改成物理删除
	 * @param deleteEmployeeIds
	 * @return
	 */
	int deleteDutyPlansByIds(Map<String, Object> deleteMap);

	/**
	 * 根据员工ID集合，查询出新系统对应的排班计划集合，并将这些排班计划新增到旧系统排班计划表中去。2016-10-12
	 * @param changeEmployeeIds
	 * @return
	 */
	int batchAddDutyForBanciByIds(String changeEmployeeIds);

	/**
	 * 根据员工ID集合，查询出新系统对应的排班计划集合，并将这些排班计划新增到旧系统排班计划表中去。2016-10-12
	 * @param changeEmployeeIds
	 */
	int batchAddDutyPlansByIds(String changeEmployeeIds);

	/**
	 * 查询本责任区且不是替班人员的用户信息集合，2016-10-13
	 * @param workPlans
	 * @return
	 */
	List<UserArea> selectEmployeeList(WorkPlans workPlans);

	/**
	 * 查询本责任区且不是替班人员的用户信息集合总数，2016-10-13
	 * @param workPlans
	 * @return
	 */
	int selectEmployeeCount(WorkPlans workPlans);

	/**
	 * 批量更新某责任区环卫工的轮班频率，2016-10-13
	 * @param workPlans
	 * @return
	 */
	int batchaUpdateTradeRate(WorkPlans workPlans);

	/**
	 * 查询检测员排班计划记录总条数，2016-10-14
	 * @param workPlans
	 * @return
	 */
	int getJCWorkPlansCount(WorkPlans workPlans);

	/**
	 * 查询检测员排班计划记录集合，2016-10-14，分页
	 * @param workPlans
	 * @return
	 */
	List<WorkPlans> getJCWorkPlansList(WorkPlans workPlans);

	/**
	 * 根据责任区删除检测员排班计划。2016-10-14
	 * @param deleteMap
	 * @return
	 */
	int deleteJCWorkPlansByMap(Map<String, Object> deleteMap);

	/**
	 * 批量更新检测员的轮班频率，2016-10-14
	 * @param workPlans
	 */
	int batchaUpdateJCTradeRate(WorkPlans workPlans);

	/**
	 * 根据责任区ID集合，删除旧系统排班计划表数据，2016-10-14
	 * @param deleteMap
	 */
	int deleteJCDutyForBanciByMap(Map<String, Object> deleteMap);

	/**
	 * 根据责任区ID集合，删除旧系统排班计划表数据，2016-10-14
	 * @param deleteMap
	 * @return
	 */
	int deleteJCDutyPlansByMap(Map<String, Object> deleteMap);

	/**
	 * 根据责任区ID集合，角色ID，往旧系统排班计划表添加数据。2016-10-14
	 * @param deleteMap
	 * @return
	 */
	int batchAddJCDutyForBanciByMap(Map<String, Object> deleteMap);

	/**
	 * 根据责任区ID集合，角色ID，往旧系统排班计划表添加数据。2016-10-14
	 * @param deleteMap
	 */
	int batchAddJCDutyPlansByMap(Map<String, Object> deleteMap);

	/**
	 * 根据查询条件，查询排班计划记录总条数,2016-10-17
	 * @param workPlans
	 * @return
	 */
	int getAllWorkPlansCount(WorkPlans workPlans);

	/**
	 * 根据查询条件，查询排班计划记录集合，2016-10-17，分页
	 * @param workPlans
	 * @return
	 */
	List<WorkPlans> getAllWorkPlansList(WorkPlans workPlans);

	/**
	 * 查询考核员排班计划记录总条数,即考核员的总条数，2016-11-01
	 * @param workPlans
	 * @return
	 */
	int getKHWorkPlansCount(WorkPlans workPlans);

	/**
	 * 查询检测员排班计划记录集合，2016-11-01，分页
	 * @param workPlans
	 * @return
	 */
	List<WorkPlans> getKHWorkPlansList(WorkPlans workPlans);

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

    /**
     * 获得员工在排班计划中的排班时间  2016-11-21
     * @param workPlans
     * @return
     */
	List<Map<String, Object>> getWorkPlansByEmployeeId(WorkPlans workPlans);	
}
