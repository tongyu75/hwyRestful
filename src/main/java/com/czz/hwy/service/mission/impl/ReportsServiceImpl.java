package com.czz.hwy.service.mission.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.mission.ReportInfo;
import com.czz.hwy.dao.mission.ReportInfoDao;
import com.czz.hwy.service.mission.ReportInfoService;

/**
 * 新的举报功能service实现层，2017-04-26
 * @author 张咏雪
 */
@Service
public class ReportsServiceImpl implements ReportInfoService {

    @Autowired
    private ReportInfoDao reportInfoDao;

    /**
     * 根据条件查询记录的总条数,2017-04-26
     */
	public int getAllReportInfoCountByBean(ReportInfo reportInfo) {
		return reportInfoDao.getInfoCount("getAllReportInfoCountByBean", reportInfo);
	}

	/**
	 * 根据条件查询举报记录列表，分页,2017-04-26
	 */
	public List<ReportInfo> getReportInfoListByBean(ReportInfo reportInfo) {
		return reportInfoDao.getInfoListByBean("getReportInfoListByBean", reportInfo);
	}
    
   
    
}
