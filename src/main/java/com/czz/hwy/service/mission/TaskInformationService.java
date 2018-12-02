package com.czz.hwy.service.mission;

import java.util.List;

import com.czz.hwy.bean.mission.TaskInformation;

/**
 * 任务管理功能的service接口，用于pc端
 * 功能描述
 * @author 张咏雪
 * @company chnegzhongzhi
 * @createDate 2016-11-14
 */
public interface TaskInformationService{

	/**
	 * 查询任务信息记录总条数，2016-11-14
	 * @param taskInformation
	 * @return
	 */
	int getTaskInformationCount(TaskInformation taskInformation);

	/**
	 * 查询任务信息记录集合，2016-11-14，分页
	 * @param taskInformation
	 * @return
	 */
	List<TaskInformation> getTaskInformationList(TaskInformation taskInformation);


	
}
