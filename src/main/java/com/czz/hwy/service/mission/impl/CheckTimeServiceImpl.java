package com.czz.hwy.service.mission.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.mission.CheckTime;
import com.czz.hwy.dao.mission.CheckTimeDao;
import com.czz.hwy.service.mission.CheckTimeService;

/**
 * 五克五分钟不合格人员功能service的接口实现类
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-09-28
 */
@Service
public class CheckTimeServiceImpl implements CheckTimeService {

    @Autowired
    private CheckTimeDao checkTimeDao;

    /**
     * 管理人员尽职监督 近一周五分钟道路测量地点（领导APP端）
     * @param employeeId 管理人员ID
     * @return
     */
    public List<CheckTime> getlatLngForLeadApp(int employeeId) {
        CheckTime bean = new CheckTime();
        bean.setEmployeeId(employeeId);
        return checkTimeDao.getInfoListByBean("getCheTimLatLngForLeadApp", bean);
    }
    
    /**
     * 获取五分钟考勤信息记录条数。2016-11-14
     * @param reports
     * @return
     */
    public int getCheckTimeHistoryCount(CheckTime bean) {
        return checkTimeDao.getInfoCount("getCheckTimeHistoryCount", bean);
    }
    
    /**
     * 获取五分钟考勤信息记录。2016-11-14
     * @param reports
     * @return
     */
    public List<Map<String, Object>> getCheckTimeHistory(CheckTime bean){
        return checkTimeDao.getInfoListMapByBean("getCheckTimeHistory", bean);
    }   
}
