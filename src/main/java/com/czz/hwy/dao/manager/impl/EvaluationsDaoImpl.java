package com.czz.hwy.dao.manager.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.czz.hwy.base.BaseDaoImpl;
import com.czz.hwy.bean.manager.Evaluations;
import com.czz.hwy.dao.manager.EvaluationsDao;
@Repository
public class EvaluationsDaoImpl extends BaseDaoImpl<Evaluations> implements EvaluationsDao {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	public int insertInfo(Evaluations bean) {
		return sqlSessionTemplate.insert("insertEvaluations",bean);

	}

	public int deleteInfo(Evaluations bean) {
		return sqlSessionTemplate.delete("deleteEvaluations",bean);

	}

	public int updateInfo(Evaluations bean) {
		return sqlSessionTemplate.update("updateEvaluations",bean);

	}

	public Evaluations getInfoByBean(Evaluations bean) {
		return sqlSessionTemplate.selectOne("getEvaluationInfoByBean", bean);

	}

	public Evaluations getInfoById(int id) {
		return sqlSessionTemplate.selectOne("getEvaluationsById",id);

	}

	public int getInfoCount(int id) {
		return sqlSessionTemplate.selectOne("getEvaluationsCountById",id);

	}

	public List getAllInfo() {
		return sqlSessionTemplate.selectList("getAllEvaluations");

	}

}
