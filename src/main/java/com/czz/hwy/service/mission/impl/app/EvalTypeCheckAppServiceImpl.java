package com.czz.hwy.service.mission.impl.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.czz.hwy.bean.mission.app.EvalTypeCheckApp;
import com.czz.hwy.dao.mission.app.EvalTypeCheckAppDao;
import com.czz.hwy.service.mission.app.EvalTypeCheckAppService;
/**
 * 获取考核类型
 * @author Administrator
 *
 */
@Service
@Transactional
public class EvalTypeCheckAppServiceImpl implements EvalTypeCheckAppService {
	
	@Autowired
	private EvalTypeCheckAppDao evalTypeCheckAppDao;
	
	/**
	 * 查询evalTypeCheck
	 */
	public List<Integer> selectEvalTypeCheck(Integer type) {
		return evalTypeCheckAppDao.getInfoListIntegerByBean("selectEvalTypeCheckByType", type);
	}

	/**
	 * 根据考核类型ID获取考核类型详细信息，2016-12-20
	 */
	public EvalTypeCheckApp getEvalTypeCheckByBeanApp(EvalTypeCheckApp evalTypeCheckApp) {
		return evalTypeCheckAppDao.getInfoByBean("getEvalTypeCheckByBeanApp", evalTypeCheckApp);
	}

}
