package com.czz.hwy.dao.manager;

import com.czz.hwy.base.BaseDao;
import com.czz.hwy.bean.manager.Evaluations;

public interface EvaluationsDao extends BaseDao<Evaluations> {
	public Evaluations getInfoByBean(Evaluations evaluations);
}
