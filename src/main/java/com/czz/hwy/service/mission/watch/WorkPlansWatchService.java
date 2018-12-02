package com.czz.hwy.service.mission.watch;

import java.util.List;

import com.czz.hwy.bean.mission.watch.WorkPlans;

/**
 * 新的排班计划功能的service接口
 * 功能描述
 * @author 张咏雪
 * @company chnegzhongzhi
 * @createDate 2016-09-29
 */
public interface WorkPlansWatchService{

    /***
     * 获得排班计划中的排班时间
     */
    public List<WorkPlans> getWorkPlansByIdTimeList(int employeeId);
}
