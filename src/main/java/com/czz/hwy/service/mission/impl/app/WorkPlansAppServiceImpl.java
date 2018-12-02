package com.czz.hwy.service.mission.impl.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.mission.UserArea;
import com.czz.hwy.bean.mission.app.WorkInfoApp;
import com.czz.hwy.bean.mission.app.WorkPlansApp;
import com.czz.hwy.bean.user.app.AttendanceForPlansApp;
import com.czz.hwy.dao.mission.app.WorkPlansAppDao;
import com.czz.hwy.service.mission.app.WorkPlansAppService;

/**
 *新的排班计划功能的service实现类
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-10-18
 */
@Service
public class WorkPlansAppServiceImpl implements WorkPlansAppService {

	@Autowired
	private WorkPlansAppDao workPlansAppDao;
	
	/**
	 * 查询本责任区且不是替班人员的用户信息集合总数，2016-10-18
	 */
	public List<UserArea> selectEmployeeList(WorkPlansApp workPlansApp) {
		return workPlansAppDao.selectEmployeeList(workPlansApp);
	}

	/**
	 * 查询本责任区且不是替班人员的用户信息集合，2016-10-18
	 */
	public int selectEmployeeCount(WorkPlansApp workPlansApp) {
		return workPlansAppDao.getInfoCount("selectEmployeeCountApp", workPlansApp);
	}
	
	/**
	 * 查询某责任区某责任点某角色的排班计划记录总条数,2016-10-18
	 */
	public int getWorkPlansCountBySel(WorkPlansApp workPlansApp) {
		return workPlansAppDao.getInfoCount("getWorkPlansCountBySelApp", workPlansApp);
	}

	/**
	 * 查询某责任区某责任点某角色的排班计划记录集合，2016-10-18
	 */
	public List<WorkPlansApp> getWorkPlansListBySel(WorkPlansApp workPlansApp) {
		return workPlansAppDao.getInfoListByBean("getWorkPlansListBySelApp", workPlansApp);
	}
	
	/**
	 * 根据责任区和责任点删除排班计划。2016-10-18
	 */
	public int deleteWorkPlansByMap(Map<String, Object> deleteMap) {
		return workPlansAppDao.deleteInfoByMap("deleteWorkPlansByMapApp", deleteMap);
	}
	
	/**
	 * 获取本责任区的轮班频率，2016-10-18
	 */
	public WorkPlansApp getSelectRateByBean(Map<String, Object> deleteMap) {
		return workPlansAppDao.getInfoByMap("getSelectRateByBeanApp", deleteMap);
	}
	
	/**
	 * 批量新增排班计划，2016-10-18
	 */
	public int batchAddWorkPlans(List<WorkPlansApp> plansList) {
		return workPlansAppDao.batchAddWorkPlans(plansList);
	}
	
	/**
	 * 根据责任区ID和责任点ID和角色ID，删除旧系统排班计划表数据,2016-10-19,暂时逻辑删除，之后改成物理删除
	 */
	public int deleteDutyForBanciByMap(Map<String, Object> deleteMap) {
		return workPlansAppDao.updateInfoByMap("deleteDutyForBanciByMapApp", deleteMap);
		//TODO 根据责任区ID和责任点ID和角色ID，删除旧系统排班计划表数据,2016-10-19,暂时逻辑删除，之后改成物理删除
//		return workPlansDao.deleteInfoByMap("deleteDutyForBanciByMap", deleteMap);
	}
	
	/**
	 * 根据责任区ID和责任点ID和角色ID，删除旧系统排班计划表数据,2016-10-19,暂时逻辑删除，之后改成物理删除
	 */
	public int deleteDutyPlansByMap(Map<String, Object> deleteMap) {
		return workPlansAppDao.updateInfoByMap("deleteDutyPlansByMapApp", deleteMap);
		//TODO 根据责任区ID和责任点ID和角色ID，删除旧系统排班计划表数据,2016-10-19,暂时逻辑删除，之后改成物理删除
//		return workPlansDao.deleteInfoByMap("deleteDutyPlansByMap", deleteMap);
	}
	
	/**
	 * 根据责任区ID和责任点ID，角色ID，往旧系统排班计划表添加数据。2016-10-18
	 */
	public int batchAddDutyForBanciByMap(Map<String, Object> deleteMap) {
		return workPlansAppDao.batchAddDutyForBanciByMap(deleteMap);
	}

	/**
	 * 根据责任区ID和责任点ID，角色ID，往旧系统排班计划表添加数据。2016-10-19
	 */
	public int batchAddDutyPlansByMap(Map<String, Object> deleteMap) {
		return workPlansAppDao.batchAddDutyPlansByMap(deleteMap);
	}

	/**
	 * 获取某个责任区责任点下正在上班的员工列表，2016-11-08
	 */
	public List<WorkPlansApp> getCurWorkPlansListByBeanApp(WorkInfoApp workInfoApp) {
		return workPlansAppDao.getCurWorkPlansListByBeanApp(workInfoApp);
	}

	/**
	 * 查询某个员工在【当前时间，当天24点之前】时间段内，需要换班的排班计划，2016-11-11
	 */
	public List<WorkPlansApp> getWorkPlansListByMapApp(Map<String, Object> map) {
		return workPlansAppDao.getInfoListTByMap("getWorkPlansListByMapApp", map);
	}

	/**
	 * 获取某员工上下班时间不重复的排班计划集合，2016-11-11 
	 */
	public List<Map<String, Object>> getWorkPlansListByEmployeeIdApp(String employeeId) {
		return workPlansAppDao.getWorkPlansListByEmployeeIdApp(employeeId);
	}

	/**
	 * 获取某个员工在【当前时间，当天24点之前】时间段内，作为换班人存在且进行换班的排班计划，2016-11-11
	 */
	public List<WorkPlansApp> getTradeWorkPlansListByMapApp(Map<String, Object> map) {
		return workPlansAppDao.getInfoListTByMap("getTradeWorkPlansListByMapApp", map);
	}

	/**
	 * 获取某些员工上下班时间不重复的排班计划集合，只有时间集合，2016-12-12
	 */
	public List<Map<String, Object>> getDistinctWorkPlansListByEmployeeIdApp(String employeeIds) {
		return workPlansAppDao.getDistinctWorkPlansListByEmployeeIdApp(employeeIds);
	}
	
    /**
     * 查询某个员工在【当前时间，当天24点之前】时间段内，需要换班的排班计划和责任点名称，2016-12-12
     * @param map
     * @return
     */
	public List<WorkPlansApp> getWorkPlansAndPointNameListByMapApp(Map<String, Object> map) {
        return workPlansAppDao.getInfoListTByMap("getWorkPlansAndPointNameListByMapApp", map);
    }

    /**
     * 获取某员工上下班时间不重复的排班计划和责任点名称集合，2016-12-12
     * @param employeeId
     * @return
     */
	public List<Map<String, Object>> getWorkPlansAndPointNameListByEmployeeIdApp(Map<String, Object> map) {
        return workPlansAppDao.getInfoListMapByMap("getWorkPlansAndPointNameListByEmployeeIdApp", map);
    }

    /**
     * 获取某个员工在【当前时间，当天24点之前】时间段内，作为换班人存在且进行换班的排班计划和责任点名称，2016-12-12
     * @param map
     * @return
     */
	public List<WorkPlansApp> getTradeWorkPlansAndPointNameListByMapApp(Map<String, Object> map) {
        return workPlansAppDao.getInfoListTByMap("getTradeWorkPlansAndPointNameListByMapApp", map);
    }

	/**
	 * 根据责任区ID和责任点Id,当前时间，获取责任人列表。责任人只能是环卫工，2016-12-14
	 */
	public List<Map<String, Object>> getDutyPeopleListByMapApp(Map<String, Object> selectMap) {
		return workPlansAppDao.getInfoListMapByMap("getDutyPeopleListByMapApp", selectMap);
	}

	/**
	 * 查询出某个责任区下每个责任点针对当前时间的上一个班次有哪些员工，以及这些员工的上班时间集合。2016-12-29
	 */
	public List<AttendanceForPlansApp> selectLastBanciWorkPlansList(WorkPlansApp workPlansApp) {
		return workPlansAppDao.selectLastBanciWorkPlansList(workPlansApp);
	}
	
    /**
     * 根据排班计划获取检测员或考核员负责的责任区，2017-02-21
     * @param employeeId
     * @return
     */
    public List<WorkPlansApp> getDutyAreaByEmployeeId(Integer employeeId) {
        return workPlansAppDao.getInfoListByIntegerId("getDutyAreaByEmployeeId", employeeId);
    }
    
    /**
     * 根据责任区ID获取当前责任区下的所有员工，2017-02-21
     * @param employeeId
     * @return
     */
    public List<Map<String, Object>> getEmployeeInfoByDutyAreaId(Integer areaId, Integer roleId) {
        Map<String, Object> mp = new HashMap<String, Object>();
        mp.put("areaId", areaId);
        mp.put("roleId", roleId);
        return workPlansAppDao.getInfoListMapByMap("getEmployeeInfoByDutyAreaId", mp);
    }

    /**
     * 根据员工ID和员工角色获取员工所在责任区，2017-04-25
     */
	public List<WorkPlansApp> getAreaListByBeanApp(WorkPlansApp workPlansApp) {
		return workPlansAppDao.getInfoListByBean("getAreaListByBeanApp", workPlansApp);
	}

	/**
	 * 根据角色ID和责任区ID集合获取负责人列表(获取检测员、考核员或督察员列表)，2017-04-25
	 */
	public List<String> getHeadPeopleListByMap(Map<String, Object> selectMap) {
		return workPlansAppDao.getInfoListStringByMap("getHeadPeopleListByMap", selectMap);
	}    
}
