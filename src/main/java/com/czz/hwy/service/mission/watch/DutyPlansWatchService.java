package com.czz.hwy.service.mission.watch;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.czz.hwy.bean.mission.watch.WorkPlans;
import com.czz.hwy.bean.mission.watch.DutyPlans;
@Service
/**
 * 出勤计划业务层接口
 * @author 张咏雪 2016-09-28
 * @version V1.0
 */
public interface DutyPlansWatchService {
    
    /**
     * 根据员工ID获取对应的手表通讯录信息,2016-10-26
     * @param employeeId 员工ID
     * @return
     */
    public List<DutyPlans> getAddressBookByEmployeeId (int employeeId);
    
    /**
     * 获得排班计划中不重复的开始与结束时间
     * @param employeeId
     * @param attendanceDate
     * @return
     */
    public List<DutyPlans> getDutyPlansByIdTimeList(int employeeId,
            Date attendanceDate);
    
    /**
     * 根据员工ID获取员工排班时间用于闹钟,2016-10-26
     * @param employeeId 员工ID
     * @return
     */
    public List<Map<String,Object>> getAlarmClockByEmployeeId (int employeeId);    
    
    public List<DutyPlans> getDutyPlansByIdList(int employeeId,
            Date attendanceDate);
    
    /**
     * 根据员工ID和角色ID获取排班计划中责任区责任点不重复列表，2016-11-18
     * @param workPlans
     * @return
     */
    public List<WorkPlans> getAreaPointListByBeanWatch(WorkPlans workPlans);
    
    /**
     * 根据代班人ID和代班人角色，获取对应请假人排班计划中责任区责任点不重复列表，2016-12-05
     * @param workPlans
     * @return
     */
    public List<WorkPlans> getAreaPointListByBeanForDBWatch(WorkPlans workPlans);
    
}
