package com.czz.hwy.service.manager.app;

import java.util.List;
import java.util.Map;

import com.czz.hwy.bean.manager.app.LeaveApp;

/**
 * 请假功能业务层接口，用于app接口
 * 
 * @author 张咏雪 2016-11-08
 * @version V1.0
 */
public interface LeaveAppService {

	/**
	 * 获取某个人包含某个时间的请假记录，2016-11-08
	 * @param leaveApp
	 * @return
	 */
	List<LeaveApp> getLeaveListByBeanApp(LeaveApp leaveApp);

    /**
     * 根据当日时间，查询请假人信息用于定时生成考勤状态,2016-11-28
     */
    public List<LeaveApp> getLeaveForAttendance();

	/**
	 * 依据员工ID以及明日日期，查询出明日请假记录。2016-12-09
	 * @param selectMap
	 * @return
	 */
	List<LeaveApp> getLeaveListByMapApp(Map<String, Object> selectMap);
	
	/**
	 * 根据时间和请假人ID获取请假记录中的代班人集合，2016-12-14
	 * @param selectMap
	 * @return
	 */
	List<Map<String, Object>> getCoverWorkDutyPeopleListByMapApp(Map<String, Object> selectMap);

}
