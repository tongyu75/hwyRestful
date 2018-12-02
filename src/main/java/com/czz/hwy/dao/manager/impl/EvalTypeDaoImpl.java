package com.czz.hwy.dao.manager.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.czz.hwy.base.BaseDaoImpl;
import com.czz.hwy.bean.manager.EvalTypes;
import com.czz.hwy.dao.manager.EvalTypeDao;
/**
 * 考核类型
 * @author 刘新洲
 *
 */
@Repository
public class EvalTypeDaoImpl extends BaseDaoImpl<EvalTypes> implements EvalTypeDao {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	/**
	 *  获取考核类型
	 */
	public List<EvalTypes> getEvalTypeList() {
		return sqlSessionTemplate.selectList("selectAllEvalType");
	}
	public List<EvalTypes> getEvalTypeList(String options) {
		return sqlSessionTemplate.selectList("selectAllEvalTypeByOptions",options);
	}
	public List<EvalTypes> getEvalTypeList(int roleId) {
		if(roleId==1)
		{
			return sqlSessionTemplate.selectList("selectAllEvalTypeByRole1");
		}
		else if(roleId==2)
		{
			return sqlSessionTemplate.selectList("selectAllEvalTypeByRole2");
		}
		else if(roleId==3)
		{
			return sqlSessionTemplate.selectList("selectAllEvalTypeByRole3");
		}
		else
		{
			return sqlSessionTemplate.selectList("selectAllEvalTypeByRole4");
		}
	}
	public EvalTypes getEvalTypeInfo(EvalTypes evalType) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne("getEvalTypeInfo",evalType);
	}
}
