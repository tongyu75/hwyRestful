package com.czz.hwy.service.manager.impl.app;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.manager.app.LeaveApp;
import com.czz.hwy.dao.manager.app.LeavekAppDao;
import com.czz.hwy.service.manager.app.LeaveAppService;

/**
 * 请假功能业务层接口实现类，用于app接口
 * 
 @ author 张咏雪 2016-11-08
 * @version V1.0
 */
@Service
public class LeaveAppServiceImpl implements LeaveAppService {

    @Autowired
    private LeavekAppDao leaveAppDao;

    /**
     * 获取某个人包含某个时间的请假记录，2016-11-08
     */
	public List<LeaveApp> getLeaveListByBeanApp(LeaveApp leaveApp) {
		return leaveAppDao.getInfoListByBean("getLeaveListByBeanApp", leaveApp);
	}

    /**
     * 根据当日时间，查询请假人信息用于定时生成考勤状态,2016-11-28
     * @return
     */
    public List<LeaveApp> getLeaveForAttendance() {
        return leaveAppDao.getInfoList("getLeaveForAttendanceApp");
    }

	/**
	 * 依据员工ID以及明日日期，查询出明日请假记录。2016-12-09
	 */
	public List<LeaveApp> getLeaveListByMapApp(Map<String, Object> selectMap) {
		return leaveAppDao.getInfoListTByMap("getLeaveListByMapApp", selectMap);
	}

	/**
	 * 根据时间和请假人ID获取请假记录中的代班人集合，2016-12-14
	 */
	public List<Map<String, Object>> getCoverWorkDutyPeopleListByMapApp(Map<String, Object> selectMap) {
		return leaveAppDao.getInfoListMapByMap("getCoverWorkDutyPeopleListByMapApp", selectMap);
	}
}
