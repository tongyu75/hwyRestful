package com.czz.hwy.dao.mission.watch;


import java.util.List;

import com.czz.hwy.base.BaseDao;
import com.czz.hwy.bean.mission.watch.WorkPlans;
import com.czz.hwy.bean.mission.watch.DutyPlans;

public interface DutyPlansWatchDao extends BaseDao<DutyPlans> {
    
    /**
     * 根据员工ID和角色ID获取排班计划中责任区责任点不重复列表，2016-11-18
     * @param workPlans
     * @return
     */
    List<WorkPlans> getWorkPlansListByLeaveWatch(WorkPlans workPlans);
    
    /**
     * 根据员工ID和角色ID获取排班计划中责任区责任点不重复列表，2016-11-18
     * @param workPlans
     * @return
     */
    List<WorkPlans> getAreaPointListByBeanWatch(WorkPlans workPlans);
    
    /**
     * 根据角色ID和责任区ID获取排班计划，2016-11-03
     * @param workPlans
     * @return
     */
    List<WorkPlans> getWorkPlansListByLeaveRoles(WorkPlans workPlans);
    
    /**
     * 根据代班人ID和代班人角色，获取对应请假人排班计划中责任区责任点不重复列表，2016-12-05
     * @param workPlans
     * @return
     */
    List<WorkPlans> getAreaPointListByBeanForDBWatch(WorkPlans workPlans);    
}
