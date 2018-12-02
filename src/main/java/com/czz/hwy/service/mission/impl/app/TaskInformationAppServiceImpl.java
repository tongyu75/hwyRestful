package com.czz.hwy.service.mission.impl.app;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.mission.app.TaskInformationApp;
import com.czz.hwy.dao.mission.app.TaskInformationAppDao;
import com.czz.hwy.service.mission.app.TaskInformationAppService;

/**
 * 工作调度功能的service接口，用于app接口
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-11-09
 */
@Service
public class TaskInformationAppServiceImpl implements TaskInformationAppService {

	@Autowired
	private TaskInformationAppDao taskInformationAppDao;

	/**
	 * 获取某个人在某个时间正在出任务的列表，2016-11-09
	 */
	public List<TaskInformationApp> getTaskInformationListByBeanApp(TaskInformationApp taskInformationApp) {
		return taskInformationAppDao.getInfoListByBean("getTaskInformationListByBeanApp", taskInformationApp);
	}

	/**
	 * 依据员工ID以及明日日期，查询出明日出任务记录。2016-12-09
	 */
	public List<TaskInformationApp> getTaskInformationListByMapApp(Map<String, Object> selectMap) {
		return taskInformationAppDao.getInfoListTByMap("getTaskInformationListByMapApp", selectMap);
	}
	
    /**
     * 根据当日时间，查询任务人信息用于定时生成考勤状态,2016-11-28
     * @return
     */
    public List<TaskInformationApp> getTaskForAttendance() {
        return taskInformationAppDao.getInfoList("getTaskForAttendanceApp");
    }
}
