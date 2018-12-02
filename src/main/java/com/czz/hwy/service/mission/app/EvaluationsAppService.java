package com.czz.hwy.service.mission.app;

import java.util.List;

import com.czz.hwy.bean.mission.app.EvaluationsApp;


public interface EvaluationsAppService {
	
	public List<EvaluationsApp> getEvaluationsByListAndAreaId(EvaluationsApp evaluationsApp);
	
	public Integer getCountByListAndAreaId(EvaluationsApp evaluationsApp);

	/**
	 * 根据责任区Id，责任点ID和考核类型值，获取考核标准信息。2016-12-20
	 * @param evaluationsApp
	 * @return
	 */
	public EvaluationsApp getEvaluationsByBeanApp(EvaluationsApp evaluationsApp);
	
}
