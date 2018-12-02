package com.czz.hwy.service.mission;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.czz.hwy.bean.mission.DutyPlans;
@Service
/**
 * 出勤计划业务层接口
 * @author 张咏雪 2016-09-28
 * @version V1.0
 */
public interface DutyPlansService
{
	/*
	 * 获取应出勤时间信息
	 */
	public List<DutyPlans> getDutyPlans(int employeeId);

	public List<DutyPlans> getDutyPlansForGramByPointIdList(int id);

	public List<DutyPlans> getDutyPlansForGramByIdList(int emplyeeId, Date checkTime);
	public List<DutyPlans> getDutyPlansForGramByBeanList(DutyPlans dutyPlans);

	public List<DutyPlans> getDutyPlansByIdList(int employeeId,
			Date attendanceDate);
	
	public List<DutyPlans> getDutyPlansByBean(DutyPlans bean);
	
	/**
	 * 新增排班计划
	 * @author 张纪才
	 */
	public void addDutyPlans(DutyPlans dutyPlans);
	/**
	 * 修改排班计划
	 * @author 张纪才
	 */
	public int updateDutyPlans(DutyPlans dutyPlans);
	/**
	 * 删除排班计划
	 * @author 张纪才
	 */
	public int deleteDutyPlans(DutyPlans dutyPlans);

	/**
	 * 获取数据总量
	 * @author 张纪才
	 * @param dutyPlans
	 * @return
	 */
	public int getCountByDutyPlans(DutyPlans dutyPlans);

	/**
	 * @param dutyPlans
	 * @return
	 */
	public List<DutyPlans> getListDutyPlans(DutyPlans dutyPlans);

	/**
	 * @param areaId
	 * @param pointId
	 * @return
	 */
	public List<DutyPlans> getListEmployeeIdsFromDutyPlans(int areaId,
			int pointId);

	/**
	 * @param employeeId
	 * @return
	 */
	public List<DutyPlans> getRecentlyDutyPlansByEmployeeid(int employeeId);
	
	/**
	 * 批量更新排班计划
	 * @param list
	 * @return
	 */
	public int beatchUpdateForDutyPlans(List<DutyPlans> list);

	/**
	 * 批量删除排班计划，即，批量更新排班计划状态为2，2016-09-28，又改成：即批量物理删除排版计划，2016-10-28
	 * @param list
	 */
	public int beatchDeleteForDutyPlans(List<DutyPlans> list);

}
