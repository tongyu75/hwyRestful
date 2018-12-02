package com.czz.hwy.service.mission.impl.watch;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.mission.watch.WorkPlans;
import com.czz.hwy.dao.mission.watch.WorkPlansWatchDao;
import com.czz.hwy.service.mission.watch.WorkPlansWatchService;

/**
 *新的排班计划功能的service实现类
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-09-29
 */
@Service
public class WorkPlansWatchServiceImpl implements WorkPlansWatchService {

	@Autowired
	private WorkPlansWatchDao workPlansWatchDao;

	/***
     * 获得排班计划中的排班时间
     */
    public List<WorkPlans> getWorkPlansByIdTimeList(int employeeId) {
        WorkPlans bean = new WorkPlans();
        bean.setEmployeeId(employeeId);
        return workPlansWatchDao.getInfoListByBean("getWorkPlansTimeWatch", bean);
    }
}
