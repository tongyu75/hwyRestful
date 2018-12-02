package com.czz.hwy.dao.manager.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.czz.hwy.base.BaseDaoImpl;
import com.czz.hwy.bean.manager.EvalTypeCheck;
import com.czz.hwy.dao.manager.EvalTypeCheckDao;
/**
 * 考核参数
 * @author 陈禹霖
 *
 */
@Repository
public class EvalTypeCheckDaoImpl extends BaseDaoImpl<EvalTypeCheck> implements EvalTypeCheckDao {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
}
