package com.czz.hwy.service.mission.impl.watch;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.mission.watch.WorkPlans;
import com.czz.hwy.bean.mission.watch.DutyPlans;
import com.czz.hwy.dao.mission.watch.DutyPlansWatchDao;
import com.czz.hwy.service.mission.watch.DutyPlansWatchService;
@Service
/**
 * 获取应出勤时间业务层实现类
 * 
 * @author 张咏雪 2016-09-28
 * @version V1.0
 */
public class DutyPlansWatchServiceImpl implements DutyPlansWatchService {
    
    @Autowired
    DutyPlansWatchDao dutyPlansWatchDao;
    
    /**
     * 获取员工ID获取对应的手表通讯录信息,2016-10-26
     * @param employeeId 员工ID
     * @return
     */
    public List<DutyPlans> getAddressBookByEmployeeId (int employeeId) {
        DutyPlans bean = new DutyPlans();
        bean.setEmployeeId(employeeId);
        return dutyPlansWatchDao.getInfoListByBean("getAddressBookByEmpIdWatch", bean);
    }
    
    /***
     * 获得排班计划中不重复的开始与结束时间
     */
    public List<DutyPlans> getDutyPlansByIdTimeList(int employeeId,
            Date attendanceDate) {
        DutyPlans dutyPlans = new DutyPlans();
        dutyPlans.setEmployeeId(employeeId);
        dutyPlans.setCheckTime(attendanceDate);
        return dutyPlansWatchDao.getInfoListByBean("getTimeListByBeanByDateWatch", dutyPlans);
    }
    
    /**
     * 根据员工ID获取员工排班时间用于闹钟,2016-10-26
     * @param employeeId 员工ID
     * @return
     */
    public List<Map<String,Object>> getAlarmClockByEmployeeId (int employeeId) {
        DutyPlans bean = new DutyPlans();
        bean.setEmployeeId(employeeId);
        return dutyPlansWatchDao.getInfoListMapByBean("getAlarmClockByEmpIdWatch", bean);
    }
    
    public List<DutyPlans> getDutyPlansByIdList(int employeeId,
            Date attendanceDate) {
        DutyPlans bean = new DutyPlans();
        bean.setEmployeeId(employeeId);
        bean.setCheckTime(attendanceDate);
        return dutyPlansWatchDao.getInfoListByBean("getInfoListByBeanByDateWatch", bean);
    }

    /**
     * 根据员工ID和角色ID获取排班计划中责任区责任点不重复列表，2016-11-18
     */
    public List<WorkPlans> getAreaPointListByBeanWatch(WorkPlans workPlans) {
        return dutyPlansWatchDao.getAreaPointListByBeanWatch(workPlans);
    }
    
    /**
     * 根据代班人ID和代班人角色，获取对应请假人排班计划中责任区责任点不重复列表，2016-12-05
     */
    public List<WorkPlans> getAreaPointListByBeanForDBWatch(WorkPlans workPlans) {
        return dutyPlansWatchDao.getAreaPointListByBeanForDBWatch(workPlans);
    }    
}
