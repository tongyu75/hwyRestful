package com.czz.hwy.service.manager;

import java.util.List;

import com.czz.hwy.bean.manager.OneClickAlarm;


/**
 * 一键报警的service接口
 * @author 张咏雪 2016-5-17
 */
public interface OneClickAlarmService {

    /***
     * 根据bean查询报警信息条数
     */
    public int getAlarmCountByBean(OneClickAlarm oneClickAlarm);

    /**
     * 根据bean查找报警信息详情(带分页)
     */
    public List<OneClickAlarm> getAlarmInfosByBean(OneClickAlarm oneClickAlarm);
    
    /**
     * 报警处理
     */
    int updateAlarmInfo(OneClickAlarm oneClickAlarm);
}
