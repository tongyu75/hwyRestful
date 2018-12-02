package com.czz.hwy.service.mission.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.mission.DutyPlans;
import com.czz.hwy.dao.area.DutyPointDao;
import com.czz.hwy.dao.mission.DutyPlansDao;
import com.czz.hwy.service.mission.DutyPlansService;
@Service
/**
 * 获取应出勤时间业务层实现类
 * 
 * @author 张咏雪 2016-09-28
 * @version V1.0
 */
public class DutyPlansServiceImpl implements DutyPlansService {
	//应出勤计划表dao层接口
	@Autowired
	DutyPlansDao dutyPlansDao;
	//责任点dao层
	@Autowired
	private DutyPointDao dutyPointDao;
	/*
	 * 通过userId获取应出勤计划对象
	 */
	public List<DutyPlans> getDutyPlans(int employeeId)
	{
		DutyPlans dutyPlans=new DutyPlans();
		dutyPlans.setEmployeeId(employeeId);
		return dutyPlansDao.getInfoListByBean("getDutyPlansByBean", dutyPlans);
	}
	public List<DutyPlans> getDutyPlansForGramByPointIdList(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	public List<DutyPlans> getDutyPlansForGramByIdList(int emplyeeId,Date checkTime) {
		DutyPlans  dp = new DutyPlans();
		dp.setCheckTime(checkTime);
		dp.setEmployeeId(emplyeeId);
		return dutyPlansDao.getInfoListByBean("getDutyPlansForGramByEmployeeIdList", dp);
	}
	public List<DutyPlans> getDutyPlansForGramByBeanList(DutyPlans dutyPlans) {
		return dutyPlansDao.getInfoListByBean("getDutyPlansByBean", dutyPlans);
	}
	public List<DutyPlans> getDutyPlansByIdList(int employeeId,
			Date attendanceDate) {
		DutyPlans dutyPlans = new DutyPlans();
		dutyPlans.setEmployeeId(employeeId);
		dutyPlans.setCheckTime(attendanceDate);
		return dutyPlansDao.getInfoListByBean("getInfoListByBeanByDate", dutyPlans);
	}
	public List<DutyPlans> getDutyPlansByBean(DutyPlans bean) {
		return dutyPlansDao.getInfoListByBean("getDutyPlansByBean", bean);
	}
	public void addDutyPlans(DutyPlans bean) {
		
		//由于排班计划的人员角色不同，环卫工、检测员,办公人员，司机都有负责对应的责任区及责任点，而考核员则对应整个责任区，所以考核员要做所有责任点的添加
		int roles_value = bean.getRoles_value();
		
		if(roles_value==1||roles_value==2 || roles_value == 8 || roles_value == 9){//非考核员
			
			dutyPlansDao.insertInfo("insertDutyPlans", bean);
		}
		if(roles_value==3){//考核员 service层处理保证事物一致
			
			//获取该责任区的所有的责任点信息
			List dutyPoints = null;
			
			dutyPoints = dutyPointDao.getDutyPointsById(bean.getDutyAreaId());
			
			List<DutyPlans> dPlans = new ArrayList<DutyPlans>();
			
			for(Map dp:(List<Map>)dutyPoints){
				
				DutyPlans temp = bean.clone();
				
				temp.setDutyPointId((Integer)dp.get("id"));
				
				dPlans.add(temp);
			}
			//批量插入数据
			dutyPlansDao.insertDutyPlanss(dPlans);
		}
	}
	public int updateDutyPlans(DutyPlans bean) {
		return dutyPlansDao.updateInfoByBean("updateDutyPlans", bean);
	}
	public int deleteDutyPlans(DutyPlans bean) {
		return dutyPlansDao.deleteInfoByBean("deleteDutyPlans", bean);
	}
	public int getCountByDutyPlans(DutyPlans dutyPlans) {
		return dutyPlansDao.getInfoCount("getCountByDutyPlans", dutyPlans);
	}
	public List<DutyPlans> getListDutyPlans(DutyPlans dutyPlans) {
		return dutyPlansDao.getInfoListByBean("getListDutyPlans", dutyPlans);
	}
	/**
	 * 获取某责任区-责任点-考核点的相关负责人
	 */
	public List<DutyPlans> getListEmployeeIdsFromDutyPlans(int areaId,
			int pointId) {
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("areaId", areaId);
		
		map.put("pointId", pointId);
		
		return dutyPlansDao.getListEmployeeIdsFromDutyPlans(map);
	}
	public List<DutyPlans> getRecentlyDutyPlansByEmployeeid(int employeeId) {
		return dutyPlansDao.getRecentlyDutyPlansByEmployeeid(employeeId);
	}
	/**
	 * 批量更新排班计划
	 */
	public int beatchUpdateForDutyPlans(List<DutyPlans> list) {
		return dutyPlansDao.beatchUpdateForDutyPlans(list);
	}
	
	/**
	 * 批量删除排班计划，即，批量更新排班计划状态为2，2016-09-28，又改成：即批量物理删除排版计划，2016-10-28
	 */
	public int beatchDeleteForDutyPlans(List<DutyPlans> list) {
		return dutyPlansDao.beatchDeleteForDutyPlans(list);
	}
}
