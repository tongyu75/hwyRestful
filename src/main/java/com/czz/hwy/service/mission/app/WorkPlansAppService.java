package com.czz.hwy.service.mission.app;

import java.util.List;
import java.util.Map;

import com.czz.hwy.bean.mission.UserArea;
import com.czz.hwy.bean.mission.app.WorkInfoApp;
import com.czz.hwy.bean.mission.app.WorkPlansApp;
import com.czz.hwy.bean.user.app.AttendanceForPlansApp;

/**
 * 新的排班计划功能的service接口
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-10-18
 */
public interface WorkPlansAppService{
	
	/**
	 * 查询本责任区且不是替班人员的用户信息集合总数，2016-10-18
	 * @param workPlans
	 * @return
	 */
	int selectEmployeeCount(WorkPlansApp workPlansApp);
	
	/**
	 * 查询本责任区且不是替班人员的用户信息集合，2016-10-18
	 * @param workPlans
	 * @return
	 */
	List<UserArea> selectEmployeeList(WorkPlansApp workPlansApp);

	/**
	 * 查询某责任区某责任点某角色的排班计划记录总条数,2016-10-18
	 * @param workPlans
	 * @return
	 */
	int getWorkPlansCountBySel(WorkPlansApp workPlansApp);

	/**
	 * 查询某责任区某责任点某角色的排班计划记录集合，2016-10-18
	 * @param workPlans
	 * @return
	 */
	List<WorkPlansApp> getWorkPlansListBySel(WorkPlansApp workPlansApp);

	/**
	 * 根据责任区和责任点删除排班计划。2016-10-18
	 * @param deleteMap
	 * @return
	 */
	int deleteWorkPlansByMap(Map<String, Object> deleteMap);
	
	/**
	 * 获取本责任区的轮班频率，2016-10-18
	 * @param deleteMap
	 * @return
	 */
	WorkPlansApp getSelectRateByBean(Map<String, Object> deleteMap);

	/**
	 * 批量新增排班计划，2016-10-18
	 * @param plansList
	 * @return
	 */
	int batchAddWorkPlans(List<WorkPlansApp> plansList);
	
	/**
	 * 根据责任区ID和责任点ID和角色ID，删除旧系统排班计划表数据,2016-10-19,暂时逻辑删除，之后改成物理删除
	 * @param deleteMap
	 * @return
	 */
	int deleteDutyForBanciByMap(Map<String, Object> deleteMap);

	/**
	 * 根据责任区ID和责任点ID和角色ID，删除旧系统排班计划表数据,2016-10-19,暂时逻辑删除，之后改成物理删除
	 * @param deleteMap
	 * @return
	 */
	int deleteDutyPlansByMap(Map<String, Object> deleteMap);

	/**
	 * 根据责任区ID和责任点ID，角色ID，往旧系统排班计划表添加数据。2016-10-19
	 * @param deleteMap
	 * @return
	 */
	int batchAddDutyForBanciByMap(Map<String, Object> deleteMap);

	/**
	 * 根据责任区ID和责任点ID，角色ID，往旧系统排班计划表添加数据。2016-10-19
	 * @param deleteMap
	 * @return
	 */
	int batchAddDutyPlansByMap(Map<String, Object> deleteMap);

	/**
	 * 获取某个责任区责任点下正在上班的员工列表，2016-11-08
	 * @param workPlansApp
	 * @return
	 */
	List<WorkPlansApp> getCurWorkPlansListByBeanApp(WorkInfoApp workInfoApp);

	/**
	 * 查询某个员工在【当前时间，当天24点之前】时间段内，需要换班的排班计划，2016-11-11
	 * @param map
	 * @return
	 */
	List<WorkPlansApp> getWorkPlansListByMapApp(Map<String, Object> map);

	/**
	 * 获取某员工上下班时间不重复的排班计划集合，2016-11-11 
	 * @param employeeId
	 * @return
	 */
	List<Map<String, Object>> getWorkPlansListByEmployeeIdApp(String employeeId);

	/**
	 * 获取某个员工在【当前时间，当天24点之前】时间段内，作为换班人存在且进行换班的排班计划，2016-11-11
	 * @param map
	 * @return
	 */
	List<WorkPlansApp> getTradeWorkPlansListByMapApp(Map<String, Object> map);

	/**
	 * 获取某些员工上下班时间不重复的排班计划集合，只有时间集合，2016-12-12
	 * @param employeeIds
	 * @return
	 */
	List<Map<String, Object>> getDistinctWorkPlansListByEmployeeIdApp(String employeeIds);
	
    /**
     * 查询某个员工在【当前时间，当天24点之前】时间段内，需要换班的排班计划和责任点名称，2016-12-12
     * @param map
     * @return
     */
    List<WorkPlansApp> getWorkPlansAndPointNameListByMapApp(Map<String, Object> map);

    /**
     * 获取某员工上下班时间不重复的排班计划和责任点名称集合，2016-12-12
     * @param employeeId
     * @return
     */
    List<Map<String, Object>> getWorkPlansAndPointNameListByEmployeeIdApp(Map<String, Object> map);

    /**
     * 获取某个员工在【当前时间，当天24点之前】时间段内，作为换班人存在且进行换班的排班计划和责任点名称，2016-12-12
     * @param map
     * @return
     */
    List<WorkPlansApp> getTradeWorkPlansAndPointNameListByMapApp(Map<String, Object> map);

    /**
     * 根据责任区ID和责任点Id,当前时间，获取责任人列表。责任人只能是环卫工，2016-12-14
     * @param selectMap
     * @return
     */
	List<Map<String, Object>> getDutyPeopleListByMapApp(Map<String, Object> selectMap);

	/**
	 * 查询出某个责任区下每个责任点针对当前时间的上一个班次有哪些员工，以及这些员工的上班时间集合。2016-12-29
	 * @param workPlansApp
	 * @return
	 */
	List<AttendanceForPlansApp> selectLastBanciWorkPlansList(WorkPlansApp workPlansApp);
	
    /**
     * 根据排班计划获取检测员或考核员负责的责任区，2017-02-21
     * @param employeeId
     * @return
     */
    List<WorkPlansApp> getDutyAreaByEmployeeId(Integer employeeId);    	
    
    /**
     * 根据责任区ID获取当前责任区下的所有员工，2017-02-21
     * @param employeeId
     * @return
     */
    List<Map<String, Object>> getEmployeeInfoByDutyAreaId(Integer employeeId, Integer roleId);

    /**
     * 根据员工ID和员工角色获取员工所在责任区，2017-04-25
     * @param workPlansApp
     * @return
     */
	List<WorkPlansApp> getAreaListByBeanApp(WorkPlansApp workPlansApp);

	/**
	 * 根据角色ID和责任区ID集合获取负责人列表(获取检测员、考核员或督察员列表)，2017-04-25
	 * @param selectMap
	 * @return
	 */
	List<String> getHeadPeopleListByMap(Map<String, Object> selectMap);
}
