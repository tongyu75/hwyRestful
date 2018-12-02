package com.czz.hwy.service.mission.impl.watch;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.mission.watch.TaskInformation;
import com.czz.hwy.bean.mission.watch.TaskInformationPojo;
import com.czz.hwy.dao.mission.watch.TaskInformationWatchDao;
import com.czz.hwy.service.mission.watch.TaskInformationWatchService;

/**
 * 任务管理service的实现类
 * 功能描述
 * @author 佟士儒
 * @company chengzhongzhi
 */
@Service
public class TaskInformationWatchServiceImpl implements TaskInformationWatchService {

	@Autowired
	private TaskInformationWatchDao taskInformationDao;
	
    /**
     * 根据当前时间查询出任务列表
     */
    public List<TaskInformation> getTaskInformationByPojo(TaskInformationPojo bean) {
        return taskInformationDao.getInfoListByBean("getTaskInformationByPojoWatch", bean);
    }
}
