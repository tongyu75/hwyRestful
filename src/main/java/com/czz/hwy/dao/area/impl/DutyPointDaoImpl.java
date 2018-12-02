package com.czz.hwy.dao.area.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.czz.hwy.base.BaseDaoImpl;
import com.czz.hwy.bean.area.DutyPoint;
import com.czz.hwy.dao.area.DutyPointDao;

@Repository
public class DutyPointDaoImpl extends BaseDaoImpl<DutyPoint> implements DutyPointDao{
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	// 根据责任区域id获取相关的责任点的集合
	public List getDutyPointsById(int id) {
		return sqlSessionTemplate.selectList("getDutyPointsById", id);
	}

	/*public int insertInfo(DutyPoint bean) {
		return sqlSessionTemplate.insert("insertDutyPointGather", bean);

	}
	@Override
	public int insertDutyPointInfo(DutyPoint bean) {
		return sqlSessionTemplate.insert("insertDutyPoint", bean);

	}
	

	public int deleteInfo(DutyPoint bean) {
		return sqlSessionTemplate.delete("deleteDutyPoint", bean);

	}

	public int updateInfo(DutyPoint bean) {
		return sqlSessionTemplate.update("updateDutyPoint", bean);

	}

	public DutyPoint getInfoByBean(DutyPoint bean) {
		return sqlSessionTemplate.selectOne("getDutyPointByBean", bean);

	}

	public DutyPoint getInfoById(int id) {
		return sqlSessionTemplate.selectOne("getDutyPointById", id);

	}

	*//**
	 * 查询所有责任点信息
	 *//*
	@Override
	public int getInfoCount(DutyPoint bean) {
		return sqlSessionTemplate.selectOne("getAllDutyPointCount",bean);

	}

	public List getAllInfo() {
		return sqlSessionTemplate.selectList("getAllDutyPoint");

	}


	@Override
	public int getInfoCount(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<DutyPoint> getAllInfo(DutyPoint dutyPoint) {
		return sqlSessionTemplate.selectList("getAllDutyPoint",dutyPoint);

	}

	@Override
	public List<DutyPoint> getAllInfoByBean(DutyPoint dutyPoint) {
		return sqlSessionTemplate.selectList("getDutyPointByBean",dutyPoint);
	}
	*//**
	 * 责任点模糊查询
	 * @author 张纪才
	 *//*
	@Override
	public List<Map> getDutyPointByLike(String q) {
		return sqlSessionTemplate.selectList("getDutyPointByLike", q);
	}
	public int getInfoCount(String statement) {
		// TODO Auto-generated method stub
		return 0;
	}
	public int getInfoCount(String statement, DutyPoint bean) {
		// TODO Auto-generated method stub
		return 0;
	}
	public int getInfoCountById(String statement, String id) {
		// TODO Auto-generated method stub
		return 0;
	}
	public DutyPoint getInfoByBean(String statement, DutyPoint bean) {
		// TODO Auto-generated method stub
		return null;
	}
	public DutyPoint getInfoById(String statement, String id) {
		// TODO Auto-generated method stub
		return null;
	}
	public List<DutyPoint> getAllInfo(String statement) {
		// TODO Auto-generated method stub
		return null;
	}
	public List<DutyPoint> getInfoListByBean(String statement, DutyPoint bean) {
		// TODO Auto-generated method stub
		return null;
	}
	public List<DutyPoint> getInfoListById(String statement, String id) {
		// TODO Auto-generated method stub
		return null;
	}
	public List<Map<String, Object>> getInfoListMapByMap(String statement,
			Map<String, String> param) {
		// TODO Auto-generated method stub
		return null;
	}
	public List<Map<String, Object>> getInfoListMapByString(String statement,
			String param) {
		// TODO Auto-generated method stub
		return null;
	}
	public List<DutyPoint> getInfoListTByMap(String statement,
			Map<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}
	public List<String> getInfoListStringByBean(String statement, DutyPoint bean) {
		// TODO Auto-generated method stub
		return null;
	}
	public int insertInfo(String statement, DutyPoint bean) {
		// TODO Auto-generated method stub
		return 0;
	}
	public int deleteInfoByPk(String statement, String id) {
		// TODO Auto-generated method stub
		return 0;
	}
	public int deleteInfoByBean(String statement, DutyPoint bean) {
		// TODO Auto-generated method stub
		return 0;
	}
	public int updateInfoByBean(String statement, DutyPoint bean) {
		// TODO Auto-generated method stub
		return 0;
	}
	public int updateInfoByString(String statement, String param) {
		// TODO Auto-generated method stub
		return 0;
	}*/
}
