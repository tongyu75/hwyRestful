package com.czz.hwy.service.mission;

import java.util.List;

import org.springframework.stereotype.Service;

import com.czz.hwy.bean.mission.ReportInfo;

@Service
/**
 * 新的举报功能service接口层，2017-04-26
 * @author 张咏雪
 */
public interface ReportInfoService {

	/**
	 * 根据条件查询记录的总条数,2017-04-26
	 * @param reportInfo
	 * @return
	 */
	int getAllReportInfoCountByBean(ReportInfo reportInfo);

	/**
	 * 根据条件查询举报记录列表，分页,2017-04-26
	 * @param reportInfo
	 * @return
	 */
	List<ReportInfo> getReportInfoListByBean(ReportInfo reportInfo);

   
}
