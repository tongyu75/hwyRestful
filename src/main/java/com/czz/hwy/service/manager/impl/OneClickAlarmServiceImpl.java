package com.czz.hwy.service.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.manager.OneClickAlarm;
import com.czz.hwy.dao.manager.OneClickAlarmDao;
import com.czz.hwy.service.manager.OneClickAlarmService;

/**
 * 一键报警servie接口的实现类
 * @author 张咏雪 2016-5-17
 */
@Service
public class OneClickAlarmServiceImpl implements OneClickAlarmService {

	@Autowired
	private OneClickAlarmDao oneClickAlarmDao;
	
    /***
     * 根据bean查询报警信息条数
     */
    public int getAlarmCountByBean(OneClickAlarm oneClickAlarm) {
        return oneClickAlarmDao.getInfoCount("getAlarmCountByBean", oneClickAlarm);
    }

    /**
     * 根据bean查找报警信息详情(带分页)
     */
    public List<OneClickAlarm> getAlarmInfosByBean(OneClickAlarm oneClickAlarm) {
        return oneClickAlarmDao.getInfoListByBean("getAlarmInfosByBean", oneClickAlarm);
    }
    
    /**
     * 报警处理
     */
    public int updateAlarmInfo(OneClickAlarm oneClickAlarm) {
        return oneClickAlarmDao.updateInfoByBean("updateAlarmInfo", oneClickAlarm);
    }
}
