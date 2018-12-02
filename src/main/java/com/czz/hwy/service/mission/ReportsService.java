package com.czz.hwy.service.mission;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.czz.hwy.bean.mission.Reports;

@Service
/**
 * 监督举报功能业务层接口
 * 
 * @author 佟士儒 2016-11-09
 */
public interface ReportsService {

    /**
     * 获取监督举报记录条数。2016-11-09
     * @param reports
     * @return
     */
    public int getReportsHistoryCount(Reports reports);
    
    /**
     * 获取监督举报记录。2016-11-09
     * @param reports
     * @return
     */
    public List<Reports> getReportsHistory(Reports reports);
    
    /**
     * 获取监察记录条数。2016-11-14
     * @param reports
     * @return
     */
    public int getReportsHistoryJCCount(Reports reports);
    
    /**
     * 获取监察举报记录。2016-11-14
     * @param reports
     * @return
     */
    public List<Map<String, Object>> getReportsHistoryJC(Reports reports);
}
