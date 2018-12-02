package com.czz.hwy.dao.mission.app;

import java.util.List;
import java.util.Map;

import com.czz.hwy.base.BaseDao;
import com.czz.hwy.bean.mission.UserArea;
import com.czz.hwy.bean.mission.app.WorkInfoApp;
import com.czz.hwy.bean.mission.app.WorkPlansApp;
import com.czz.hwy.bean.user.app.AttendanceForPlansApp;

/**
 * 新的排班计划功能的dao接口
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-10-18
 */
public interface WorkPlansAppDao extends BaseDao<WorkPlansApp>{
	
	/**
	 * 查询本责任区且不是替班人员的用户信息集合总数，2016-10-18
	 * @param workPlans
	 * @return
	 */
	List<UserArea> selectEmployeeList(WorkPlansApp workPlansApp);

	/**
	 * 批量新增排班计划，2016-10-18
	 * @param plansList
	 * @return
	 */
	int batchAddWorkPlans(List<WorkPlansApp> plansList);
	
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
	 * @param workInfoApp
	 * @return
	 */
	List<WorkPlansApp> getCurWorkPlansListByBeanApp(WorkInfoApp workInfoApp);

	/**
	 * 获取某员工上下班时间不重复的排班计划集合，2016-11-11 
	 * @param employeeId
	 * @return
	 */
	List<Map<String, Object>> getWorkPlansListByEmployeeIdApp(String employeeId);

	/**
	 * 获取某些员工上下班时间不重复的排班计划集合，只有时间集合，2016-12-12
	 * @param employeeIds
	 * @return
	 */
	List<Map<String, Object>> getDistinctWorkPlansListByEmployeeIdApp(String employeeIds);

	/**
	 * 查询出某个责任区下每个责任点针对当前时间的上一个班次有哪些员工，以及这些员工的上班时间集合。2016-12-29
	 * @param workPlansApp
	 * @return
	 */
	List<AttendanceForPlansApp> selectLastBanciWorkPlansList(WorkPlansApp workPlansApp);

}
