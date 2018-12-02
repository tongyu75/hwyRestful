package com.czz.hwy.dao.mission.impl.app;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.czz.hwy.base.BaseDaoImpl;
import com.czz.hwy.bean.mission.UserArea;
import com.czz.hwy.bean.mission.app.WorkInfoApp;
import com.czz.hwy.bean.mission.app.WorkPlansApp;
import com.czz.hwy.bean.user.app.AttendanceForPlansApp;
import com.czz.hwy.dao.mission.app.WorkPlansAppDao;

/**
 * 新的排班计划功能dao的接口实现类
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-10-18
 */
@Repository
public class WorkPlansAppDaoImpl extends BaseDaoImpl<WorkPlansApp> implements WorkPlansAppDao {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	/**
	 * 批量新增排班计划，2016-10-18
	 */
	public int batchAddWorkPlans(List<WorkPlansApp> plansList) {
		return sqlSessionTemplate.insert("batchAddWorkPlansApp", plansList);
	}
	
	/**
	 * 根据责任区ID和责任点ID，角色ID，往旧系统排班计划表添加数据。2016-10-19
	 */
	public int batchAddDutyForBanciByMap(Map<String, Object> deleteMap) {
		return sqlSessionTemplate.insert("batchAddDutyForBanciByMapApp", deleteMap);
	}

	/**
	 * 根据责任区ID和责任点ID，角色ID，往旧系统排班计划表添加数据。2016-10-19
	 */
	public int batchAddDutyPlansByMap(Map<String, Object> deleteMap) {
		return sqlSessionTemplate.insert("batchAddDutyPlansByMapApp", deleteMap);
	}
	
	/**
	 * 查询本责任区且不是替班人员的用户信息集合总数，2016-10-18
	 */
	public List<UserArea> selectEmployeeList(WorkPlansApp workPlansApp) {
		return sqlSessionTemplate.selectList("selectEmployeeListApp", workPlansApp);
	}

	/**
	 * 获取某个责任区责任点下正在上班的员工列表，2016-11-08
	 */
	public List<WorkPlansApp> getCurWorkPlansListByBeanApp(WorkInfoApp workInfoApp) {
		return sqlSessionTemplate.selectList("getCurWorkPlansListByBeanApp", workInfoApp);
	}

	/**
	 * 获取某员工上下班时间不重复的排班计划集合，2016-11-11 
	 */
	public List<Map<String, Object>> getWorkPlansListByEmployeeIdApp(String employeeId) {
		return sqlSessionTemplate.selectList("getWorkPlansListByEmployeeIdApp", employeeId);
	}

	/**
	 *  获取某些员工上下班时间不重复的排班计划集合，只有时间集合，2016-12-12
	 */
	public List<Map<String, Object>> getDistinctWorkPlansListByEmployeeIdApp(String employeeIds) {
		return sqlSessionTemplate.selectList("getDistinctWorkPlansListByEmployeeIdApp", employeeIds);
	}

	/**
	 * 查询出某个责任区下每个责任点针对当前时间的上一个班次有哪些员工，以及这些员工的上班时间集合。2016-12-29
	 */
	public List<AttendanceForPlansApp> selectLastBanciWorkPlansList(WorkPlansApp workPlansApp) {
		return sqlSessionTemplate.selectList("selectLastBanciWorkPlansList", workPlansApp);
	}

}
