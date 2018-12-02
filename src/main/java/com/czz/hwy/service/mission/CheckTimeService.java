package com.czz.hwy.service.mission;

import java.util.List;
import java.util.Map;

import com.czz.hwy.bean.mission.CheckTime;

/**
 * 五克五分钟检查信息service的接口实现类
 * @author 张咏雪
 * @createDate 2016-09-29
 */
public interface CheckTimeService{

	/**
	 * 管理人员尽职监督 近一周五分钟道路测量地点（领导APP端）
	 * @param employeeId 管理人员ID
	 * @return
	 */
	public List<CheckTime> getlatLngForLeadApp(int employeeId);

    /**
     * 获取五分钟考勤信息记录条数。2016-11-14
     * @param reports
     * @return
     */
    public int getCheckTimeHistoryCount(CheckTime bean);
    
    /**
     * 获取五分钟考勤信息记录。2016-11-14
     * @param reports
     * @return
     */
    public List<Map<String, Object>> getCheckTimeHistory(CheckTime bean);	
}
