package com.czz.hwy.service.mission.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.mission.Reports;
import com.czz.hwy.dao.mission.ReportsDao;
import com.czz.hwy.service.mission.ReportsService;

/**
 * 监督举报功能业务层接口
 * 
 * @author 佟士儒 2016-11-09
 */
@Service
public class ReportInfoServiceImpl implements ReportsService {

    @Autowired
    private ReportsDao reportsDao;
    
    /**
     * 获取监督举报记录条数。2016-11-09
     * @param userArea
     * @return
     */
    public int getReportsHistoryCount(Reports reports) {
        return reportsDao.getInfoCount("getReportsHistoryCount", reports);
    }
    
    /**
     * 获取监督举报记录。2016-11-09
     * @param userArea
     * @return
     */
    public List<Reports> getReportsHistory(Reports reports) {
        return reportsDao.getInfoListByBean("getReportsHistory", reports);
    }

    /**
     * 获取监察记录条数。2016-11-14
     * @param reports
     * @return
     */
    public int getReportsHistoryJCCount(Reports reports) {
        return reportsDao.getInfoCount("getReportsHistoryJCCount", reports);
    }
    
    /**
     * 获取监察举报记录。2016-11-14
     * @param reports
     * @return
     */
    public List<Map<String, Object>> getReportsHistoryJC(Reports reports) {
        return reportsDao.getInfoListMapByBean("getReportsHistoryJC", reports);
    }
    
}
