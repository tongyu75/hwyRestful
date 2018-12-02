package com.czz.hwy.service.mission.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.mission.UserArea;
import com.czz.hwy.bean.mission.WorkPlans;
import com.czz.hwy.dao.mission.WorkPlansDao;
import com.czz.hwy.service.mission.WorkPlansService;

/**
 *新的排班计划功能的service实现类
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-09-29
 */
@Service
public class WorkPlansServiceImpl implements WorkPlansService {

	@Autowired
	private WorkPlansDao workPlansDao;

	/**
	 * 根据责任区Id、责任点Id和员工Id获取排班计划列表，2016-09-29
	 */
	public List<WorkPlans> getWorkPlansListByBean(WorkPlans workPlans) {
		return workPlansDao.getInfoListByBean("getWorkPlansListByBean", workPlans);
	}

	/**
	 * 根据责任区和责任点删除排班计划。2016-09-30
	 */
	public int deleteWorkPlansByMap(Map<String, Object> deleteMap) {
		return workPlansDao.deleteInfoByMap("deleteWorkPlansByMap", deleteMap);
	}

	/**
	 * 批量新增排班计划，2016-09-30
	 */
	public int batchAddWorkPlans(List<WorkPlans> plansList) {
		return workPlansDao.batchAddWorkPlans(plansList);
	}

	/**
	 * 获取排班计划总条数，按责任点查询  2016-10-01
	 */
	public int getWorkPlansCount(WorkPlans workPlans) {
		return workPlansDao.getInfoCount("getWorkPlansCount", workPlans);
	}

	/**
	 * 查询排班计划集合，按责任点分页 2016-10-01
	 */
	public List<WorkPlans> getWorkPlansList(WorkPlans workPlans) {
		return workPlansDao.getInfoListByBean("getWorkPlansList", workPlans);
	}

	/**
	 * 获取某个责任区下的所有责任点列表,2016-10-01
	 */
	public List<Map<String, Object>> getPointListByAreaId(String areaId) {
		return workPlansDao.getPointListByAreaId(areaId);
	}

	/**
	 * 根据责任区ID和责任点ID集合，删除旧系统排班计划表数据,2016-10-09
	 */
	public int deleteDutyForBanciByMap(Map<String, Object> deleteMap) {
		return workPlansDao.deleteInfoByMap("deleteDutyForBanciByMap", deleteMap);
	}

	/**
	 * 根据责任区ID和责任点ID集合，删除旧系统排班计划表数据,2016-10-09
	 */
	public int deleteDutyPlansByMap(Map<String, Object> deleteMap) {
		return workPlansDao.deleteInfoByMap("deleteDutyPlansByMap", deleteMap);
	}

	/**
	 * 根据责任区ID和责任点ID，角色ID，往旧系统排班计划表添加数据。2016-10-10
	 */
	public int batchAddDutyPlansByMap(Map<String, Object> deleteMap) {
		return workPlansDao.batchAddDutyPlansByMap(deleteMap);
	}
	/**
	 * 根据责任区ID和责任点ID，角色ID，往旧系统排班计划表添加数据。2016-10-10
	 */
	public int batchAddDutyForBanciByMap(Map<String, Object> deleteMap) {
		return workPlansDao.batchAddDutyForBanciByMap(deleteMap);
	}

	/**
	 * 根据班组ID，查询出排班计划集合，2016-10-10
	 */
	public List<WorkPlans> selectWorkPlansListByBanZuId(String banzuId) {
		return workPlansDao.getInfoListById("selectWorkPlansListByBanZuId", banzuId);
	}

	/**
	 * 查询出【当前日期前30分钟，当前日期】需要换班的排班计划，2016-10-11
	 */
	public List<WorkPlans> getWorkPlansListByMap(Map<String, Object> map) {
		return workPlansDao.getInfoListTByMap("getWorkPlansListByMap", map);
	}
	
	/**
	 * 获取某人员的排班计划集合，2016-10-12
	 */
	public List<WorkPlans> getWorkPlansListByEmployeeId(String employeeId) {
		return workPlansDao.getInfoListById("getWorkPlansListByEmployeeId", employeeId);
	}

	/**
	 * 根据员工ID集合，删除新系统排班计划对应的数据，2016-10-12
	 */
	public int deleteWorPlansByEmployeeIds(String deleteEmployeeIds) {
		return workPlansDao.deleteInfoByPk("deleteWorPlansByEmployeeIds", deleteEmployeeIds);
	}

	/**
	 * 根据员工ID集合，删除旧系统排班计划对应的数据，2016-10-12,目前设置为逻辑删除,以后要改成物理删除,现在已经改成物理删除
	 */
	public int deleteDutyForBanciByIds(Map<String, Object> deleteMap) {
//		return workPlansDao.updateInfoByMap("deleteDutyForBanciByIds", deleteMap);
		return workPlansDao.deleteInfoByMap("deleteDutyForBanciByIds", deleteMap);
	}

	/**
	 * 根据员工ID集合，删除旧系统排班计划对应的数据，2016-10-12,目前设置为逻辑删除,以后要改成物理删除,现在已经改成物理删除
	 */
	public int deleteDutyPlansByIds(Map<String, Object> deleteMap) {
		return workPlansDao.updateInfoByMap("deleteDutyPlansByIds", deleteMap);
//		return workPlansDao.deleteInfoByMap("deleteDutyPlansByIds", deleteMap);
	}

	/**
	 * 根据员工ID集合，查询出新系统对应的排班计划集合，并将这些排班计划新增到旧系统排班计划表中去。2016-10-12
	 */
	public int batchAddDutyForBanciByIds(String changeEmployeeIds) {
		return workPlansDao.batchAddDutyForBanciByIds(changeEmployeeIds);
	}

	/**
	 * 根据员工ID集合，查询出新系统对应的排班计划集合，并将这些排班计划新增到旧系统排班计划表中去。2016-10-12
	 */
	public int batchAddDutyPlansByIds(String changeEmployeeIds) {
		return workPlansDao.batchAddDutyPlansByIds(changeEmployeeIds);
	}

	/**
	 * 查询本责任区且不是替班人员的用户信息集合总数，2016-10-13
	 */
	public List<UserArea> selectEmployeeList(WorkPlans workPlans) {
		return workPlansDao.selectEmployeeList(workPlans);
	}

	/**
	 * 查询本责任区且不是替班人员的用户信息集合，2016-10-13
	 */
	public int selectEmployeeCount(WorkPlans workPlans) {
		return workPlansDao.getInfoCount("selectEmployeeCount", workPlans);
	}

	/**
	 * 批量更新某责任区环卫工的轮班频率，2016-10-13
	 */
	public int batchaUpdateTradeRate(WorkPlans workPlans) {
		return workPlansDao.updateInfoByBean("batchaUpdateTradeRate", workPlans);
	}

	/**
	 * 查询检测员排班计划记录总条数，2016-10-14
	 */
	public int getJCWorkPlansCount(WorkPlans workPlans) {
		return workPlansDao.getInfoCount("getJCWorkPlansCount", workPlans);
	}

	/**
	 * 查询检测员排班计划记录集合，2016-10-14，分页
	 */
	public List<WorkPlans> getJCWorkPlansList(WorkPlans workPlans) {
		return workPlansDao.getInfoListByBean("getJCWorkPlansList", workPlans);
	}

	/**
	 * 根据责任区删除检测员排班计划。2016-10-14
	 */
	public int deleteJCWorkPlansByMap(Map<String, Object> deleteMap) {
		return workPlansDao.deleteInfoByMap("deleteJCWorkPlansByMap", deleteMap);
	}

	/**
	 * 批量更新检测员的轮班频率，2016-10-14
	 */
	public int batchaUpdateJCTradeRate(WorkPlans workPlans) {
		return workPlansDao.batchaUpdateJCTradeRate(workPlans);
	}

	/**
	 * 根据责任区ID集合，删除旧系统排班计划表数据，2016-10-14
	 */
	public int deleteJCDutyForBanciByMap(Map<String, Object> deleteMap) {
		return workPlansDao.deleteInfoByMap("deleteJCDutyForBanciByMap", deleteMap);
	}

	/**
	 * 根据责任区ID集合，删除旧系统排班计划表数据，2016-10-14
	 */
	public int deleteJCDutyPlansByMap(Map<String, Object> deleteMap) {
		return workPlansDao.deleteInfoByMap("deleteJCDutyPlansByMap", deleteMap);
	}

	/**
	 * 根据责任区ID集合，角色ID，往旧系统排班计划表添加数据。2016-10-14
	 */
	public int batchAddJCDutyForBanciByMap(Map<String, Object> deleteMap) {
		return workPlansDao.batchAddJCDutyForBanciByMap(deleteMap);
	}

	/**
	 * 根据责任区ID集合，角色ID，往旧系统排班计划表添加数据。2016-10-14
	 */
	public int batchAddJCDutyPlansByMap(Map<String, Object> deleteMap) {
		return workPlansDao.batchAddJCDutyPlansByMap(deleteMap);
	}

	/**
	 * 根据查询条件，查询排班计划记录总条数,2016-10-17
	 */
	public int getAllWorkPlansCount(WorkPlans workPlans) {
		return workPlansDao.getInfoCount("getAllWorkPlansCount", workPlans);
	}

	/**
	 * 根据查询条件，查询排班计划记录集合，2016-10-17，分页
	 */
	public List<WorkPlans> getAllWorkPlansList(WorkPlans workPlans) {
		return workPlansDao.getInfoListByBean("getAllWorkPlansList", workPlans);
	}

	/**
	 * 查询考核员排班计划记录总条数,即考核员的总条数，2016-11-01
	 */
	public int getKHWorkPlansCount(WorkPlans workPlans) {
		return workPlansDao.getInfoCount("getKHWorkPlansCount", workPlans);
	}

	/**
	 * 查询检测员排班计划记录集合，2016-11-01，分页
	 */
	public List<WorkPlans> getKHWorkPlansList(WorkPlans workPlans) {
		return workPlansDao.getInfoListByBean("getKHWorkPlansList", workPlans);
	}

	/**
	 * 查询检测员总数，2016-11-10
	 */
	public int selectJCEmployeeCount(WorkPlans workPlans) {
		return workPlansDao.selectJCEmployeeCount(workPlans);
	}

	/**
	 * 查询检测员集合，2016-11-10
	 */
	public List<UserArea> selectJCEmployeeList(WorkPlans workPlans) {
		return workPlansDao.selectJCEmployeeList(workPlans);
	}
	
    /**
     * 获得员工在排班计划中的排班时间  2016-11-21
     * @param workPlans
     * @return
     */
    public List<Map<String, Object>> getWorkPlansByEmployeeId(WorkPlans workPlans) {
        return workPlansDao.getInfoListMapByBean("getWorkPlansByEmployeeId", workPlans);
    }	
}
