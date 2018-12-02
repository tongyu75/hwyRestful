package com.czz.hwy.service.mission.watch;

import java.util.List;

import com.czz.hwy.bean.mission.watch.TaskInformation;
import com.czz.hwy.bean.mission.watch.TaskInformationPojo;

/**
 * 任务管理service
 * 功能描述
 * @author 佟士儒
 * @company chnegzhongzhi
 */
public interface TaskInformationWatchService {

    /**
     * 根据扩展类对象获取任务列表
     */
    public List<TaskInformation> getTaskInformationByPojo(TaskInformationPojo bean);
}
