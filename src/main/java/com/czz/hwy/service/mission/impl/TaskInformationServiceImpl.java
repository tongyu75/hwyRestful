package com.czz.hwy.service.mission.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.czz.hwy.bean.mission.TaskInformation;
import com.czz.hwy.dao.mission.TaskInformationDao;
import com.czz.hwy.service.mission.TaskInformationService;

/**
 * 任务管理功能的service实现类，用于pc端
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-11-14
 */
@Service
public class TaskInformationServiceImpl implements TaskInformationService {

	@Autowired
	private TaskInformationDao taskInformationDao;

	/**
	 * 查询任务信息记录总条数，2016-11-14
	 */
	public int getTaskInformationCount(TaskInformation taskInformation) {
		return taskInformationDao.getInfoCount("getTaskInformationCount", taskInformation);
	}

	/**
	 * 查询任务信息记录集合，2016-11-14，分页
	 */
	public List<TaskInformation> getTaskInformationList(TaskInformation taskInformation) {
		return taskInformationDao.getInfoListByBean("getTaskInformationList", taskInformation);
	}

	
}
