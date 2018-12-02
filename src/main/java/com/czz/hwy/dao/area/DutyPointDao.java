package com.czz.hwy.dao.area;


import java.util.List;

import com.czz.hwy.base.BaseDao;
import com.czz.hwy.bean.area.DutyPoint;

public interface DutyPointDao extends BaseDao<DutyPoint> {
	
	public List<DutyPoint> getDutyPointsById(int id);
	
	/*
	public int getInfoCount(DutyPoint dutyPoint);

	public List<DutyPoint> getAllInfo(DutyPoint dutyPoint);
	
	public int insertDutyPointInfo(DutyPoint bean);
	
	public List<DutyPoint> getAllInfoByBean(DutyPoint dutyPoint);

	*//**
	 * @param q
	 * @return
	 *//*
	public List<Map> getDutyPointByLike(String q);*/
	
}
