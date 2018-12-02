package com.czz.hwy.service.mission.impl.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.mission.app.EvaluationsApp;
import com.czz.hwy.dao.mission.app.EvaluationsAppDao;
import com.czz.hwy.service.mission.app.EvaluationsAppService;
/**
 * 考核类别业务层实现类
 * 
 * @author 陈禹霖
 * @version V1.0
 */
@Service
public class EvaluationsAppServiceImpl implements EvaluationsAppService {
	// 考核类别dao层接口
	@Autowired
	private EvaluationsAppDao evaluationsAppDao;
	
	public List<EvaluationsApp> getEvaluationsByListAndAreaId(EvaluationsApp evaluationsApp){
		return evaluationsAppDao.getInfoListByBean("getEvaluationsByListAndAreaId", evaluationsApp);
	}
	
	public Integer getCountByListAndAreaId(EvaluationsApp evaluationsApp){
		return evaluationsAppDao.getInfoCount("getCountByListAndAreaId", evaluationsApp);
	}

	/**
	 * 根据责任区Id，责任点ID和考核类型值，获取考核标准信息。2016-12-20
	 */
	public EvaluationsApp getEvaluationsByBeanApp(EvaluationsApp evaluationsApp) {
		return evaluationsAppDao.getInfoByBean("getEvaluationsByBeanApp", evaluationsApp);
	}
	
}
