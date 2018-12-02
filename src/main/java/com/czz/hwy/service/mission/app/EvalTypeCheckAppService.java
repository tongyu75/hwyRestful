package com.czz.hwy.service.mission.app;

import java.util.List;

import com.czz.hwy.bean.mission.app.EvalTypeCheckApp;


/**
 * 获取考核类型
 * @author Administrator
 *
 */
public interface EvalTypeCheckAppService {
	
	public List<Integer> selectEvalTypeCheck(Integer type);

	/**
	 * 根据考核类型ID获取考核类型详细信息，2016-12-20
	 * @param evalTypeCheckApp
	 * @return
	 */
	public EvalTypeCheckApp getEvalTypeCheckByBeanApp(EvalTypeCheckApp evalTypeCheckApp);
	
}
