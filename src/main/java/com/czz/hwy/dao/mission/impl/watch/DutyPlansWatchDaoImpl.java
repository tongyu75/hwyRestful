package com.czz.hwy.dao.mission.impl.watch;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.czz.hwy.base.BaseDaoImpl;
import com.czz.hwy.bean.mission.watch.WorkPlans;
import com.czz.hwy.bean.mission.watch.DutyPlans;
import com.czz.hwy.dao.mission.watch.DutyPlansWatchDao;
@Repository
public class DutyPlansWatchDaoImpl extends BaseDaoImpl<DutyPlans> implements DutyPlansWatchDao {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    
    /**
     * 根据员工ID和角色ID获取排班计划中责任区责任点不重复列表，2016-11-18
     */
    public List<WorkPlans> getWorkPlansListByLeaveWatch(WorkPlans workPlans) {
        return sqlSessionTemplate.selectList("getWorkPlansListByLeaveWatch", workPlans);
    }      
    
    /**
     * 根据员工ID和角色ID获取排班计划中责任区责任点不重复列表，2016-11-18
     */
    public List<WorkPlans> getAreaPointListByBeanWatch(WorkPlans workPlans) {
        return sqlSessionTemplate.selectList("getAreaPointListByBeanWatch", workPlans);
    }          
    
    /**
     * 根据角色ID和责任区ID获取排班计划，2016-11-03
     */
    public List<WorkPlans> getWorkPlansListByLeaveRoles(WorkPlans workPlans) {
        return sqlSessionTemplate.selectList("getWorkPlansListByLeaveRolesWatch", workPlans);
    }    
    
    /**
     * 根据代班人ID和代班人角色，获取对应请假人排班计划中责任区责任点不重复列表，2016-12-05
     */
    public List<WorkPlans> getAreaPointListByBeanForDBWatch(WorkPlans workPlans) {
        return sqlSessionTemplate.selectList("getAreaPointListByBeanForDBWatch", workPlans);
    }          
}
