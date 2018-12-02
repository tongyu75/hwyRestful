package com.czz.hwy.service.mission;

import java.util.List;

import com.czz.hwy.bean.mission.DutyForBanci;
import com.czz.hwy.bean.mission.DutyPlans;
import com.czz.hwy.bean.user.AttendanceForPlans;

/**
 * 改造后的排班计划的service接口
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-09-28
 */
public interface DutyForBanciService{

	/**
	 * 插入一条排班计划
	 * @param bean
	 * @return
	 */
	public int insertDuty(DutyForBanci bean);
	
	/**
	 * 根据条件获取一条新的排班计划
	 * @param bean
	 * @return
	 */
	public DutyForBanci getDutyByBean(DutyForBanci bean);
	
	/**
	 * 根据条件获取排班计划的列表
	 * @param bean
	 * @return
	 */
	public List<DutyForBanci> getDutyListByBean(DutyForBanci bean);
	
	/**
	 * 根据条件获取排班计划的总数
	 * @param bean
	 * @return
	 */
	public int getDutyCountByBean(DutyForBanci bean);
	
	/**
	 * 更新排班计划
	 * @param bean
	 * @return
	 */
	public int updateDutyByBean(DutyForBanci bean);
	
	/**
	 * 删除排班计划
	 * @param bean
	 * @return
	 */
	public int deleteDutyByBean(DutyForBanci bean);
	
	/**
	 * 调换班次
	 */
	public int changeBanci(int updateId);

	/**
	 * 根据seq获取排班计划
	 * @param dutyplans
	 * @return
	 */
	public DutyPlans getDutyPlansByBean(DutyPlans dutyplans);

	/**
	 * 根据员工ID,责任区ID，责任点ID和日期查询出员工的考勤记录
	 * @param dutyplans
	 * @return
	 */
	public List<AttendanceForPlans> getAttendanceForPlansByBean(DutyPlans dutyplans);

	/**
	 * 若考勤记录不为空，根据员工ID,责任区ID,责任点ID和当前日期查询出环卫工的排班计划列表
	 * @param attendanceForPlans
	 * @return
	 */
	public List<DutyPlans> getDutyPlansListByBean(AttendanceForPlans attendanceForPlans);

	/**
	 * 根据员工ID，当前日期，责任点ID，责任区ID，删除考勤记录
	 * @param attendanceForPlans
	 */
	public int deleteAttendanceByBean(AttendanceForPlans attendanceForPlans);

	/**
	 * 5 更新那条不一致的考勤记录
	 * @param attendanceForPlans
	 * @return
	 */
	public int updateAttendanceForPlansByBean(AttendanceForPlans attendanceForPlans);

	/**
	 * 根据bean删除多余的考勤记录
	 * @param attendanceForPlans
	 * @return
	 */
	public int deleteAttendanceForPlansByBean(AttendanceForPlans attendanceForPlans);

	/**
	 * 根据班次获取排班计划信息，2016-09-28
	 * @param string
	 * @return
	 */
	public List<DutyForBanci> getDutyForBanciListByNumber(String string);

	/**
	 * 批量删除排班计划，即，批量更新排班计划状态为2，2016-09-28，又改成：即批量物理删除排版计划，2016-10-28
	 * @param dutyForBanciList
	 */
	public int beatchDeleteForDutyForBanci(List<DutyForBanci> dutyForBanciList);

}
