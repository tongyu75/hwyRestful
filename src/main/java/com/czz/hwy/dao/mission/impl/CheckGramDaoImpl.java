package com.czz.hwy.dao.mission.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.czz.hwy.base.BaseDaoImpl;
import com.czz.hwy.bean.mission.CheckGram;
import com.czz.hwy.bean.mission.KindInfo;
import com.czz.hwy.dao.mission.CheckGramDao;
@Repository
public class CheckGramDaoImpl extends BaseDaoImpl<CheckGram> implements CheckGramDao {
    
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public List<KindInfo> getKindCountInfo(CheckGram checkGram) {
		return sqlSessionTemplate.selectList("getcheckGramTypeInfo",checkGram);
	}
}
