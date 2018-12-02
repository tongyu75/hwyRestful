package com.czz.hwy.dao.mission;

import java.util.List;

import com.czz.hwy.base.BaseDao;
import com.czz.hwy.bean.mission.DutyForBanci;
import com.czz.hwy.bean.mission.DutyPlans;
import com.czz.hwy.bean.user.AttendanceForPlans;

/**
 * 改造后的排班计划的到接口
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-09-28
 */
public interface DutyForBanciDao extends BaseDao<DutyForBanci>{

	/**
	 * 根据bean删除多余的考勤记录
	 * @param attendanceForPlans
	 * @return
	 */
	public int deleteAttendanceForPlansByBean(AttendanceForPlans attendanceForPlans);
	
	/**
	 * 更新那条不一致的考勤记录
	 * @param attendanceForPlans
	 * @return
	 */
	public int updateAttendanceForPlansByBean(AttendanceForPlans attendanceForPlans);
	
	/**
	 * 根据员工ID，当前日期，责任点ID，责任区ID，删除考勤记录
	 * @param attendanceForPlans
	 * @return
	 */
	public int deleteAttendanceByBean(AttendanceForPlans attendanceForPlans);
	
	/**
	 * 若考勤记录不为空，根据员工ID,责任区ID,责任点ID和当前日期查询出环卫工的排班计划列表
	 * @param attendanceForPlans
	 * @return
	 */
	public List<DutyPlans> getDutyPlansListByBean(AttendanceForPlans attendanceForPlans);
	
	/**
	 * 批量更新排班计划
	 * @param list
	 * @return
	 */
	public int beatchUpdateForDuty(List<DutyForBanci> list);
	
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
	 * 批量删除排班计划，即，批量更新排班计划状态为2，2016-09-28，又改成：即批量物理删除排版计划，2016-10-28
	 * @param dutyForBanciList
	 * @return
	 */
	public int beatchDeleteForDutyForBanci(List<DutyForBanci> dutyForBanciList);

	
}
