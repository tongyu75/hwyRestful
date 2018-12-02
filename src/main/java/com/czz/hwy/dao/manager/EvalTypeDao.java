package com.czz.hwy.dao.manager;

import java.util.List;

import com.czz.hwy.base.BaseDao;
import com.czz.hwy.bean.manager.EvalTypes;
/**
 * 考核类型维护
 * @author 刘新洲
 *
 */
public interface EvalTypeDao extends BaseDao<EvalTypes>{
	// 获取考核类型
	List<EvalTypes> getEvalTypeList();

	/**
	 * @param options
	 * @return
	 */
	List<EvalTypes> getEvalTypeList(String options);
	List<EvalTypes> getEvalTypeList(int roleId);

	EvalTypes getEvalTypeInfo(EvalTypes evalType);
}
