package com.czz.hwy.service.mission.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.mission.Banci;
import com.czz.hwy.bean.mission.DutyForBanci;
import com.czz.hwy.bean.mission.DutyPlans;
import com.czz.hwy.bean.user.AttendanceForPlans;
import com.czz.hwy.dao.mission.DutyForBanciDao;
import com.czz.hwy.service.mission.BanciService;
import com.czz.hwy.service.mission.DutyForBanciService;
import com.czz.hwy.service.mission.DutyPlansService;

/**
 * 改造后的管理排班计划的service实现类
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-09-28
 */
@Service
public class DutyForBanciServiceImpl implements DutyForBanciService {

	@Autowired
	private DutyForBanciDao dutyForBanciDao;
	
	@Autowired
	private DutyPlansService dutyPlansService;
	
	@Autowired
	private BanciService banciService;
	
	/**
	 * 插入一条排班计划
	 */
	public int insertDuty(DutyForBanci bean) {
		//在向新的排班表中插入数据时,同时还应该向以前的排班表添加数据,补齐记录信息
		//定义排班对象
		DutyPlans dutyPlans = new DutyPlans();
		
		dutyPlans.setDistributorId(bean.getDistributorId());
		
		dutyPlans.setDutyAreaId(bean.getDutyAreaId());
		
		dutyPlans.setDutyFromDate(bean.getDutyFromDate());
		
		dutyPlans.setDutyToDate(bean.getDutyToDate());
		
		dutyPlans.setDutyPointId(bean.getDutyPointId());
		
		int dutyNumber = bean.getDutyNumber();
		
		Banci banci = new Banci();
		banci.setDutyNumber(dutyNumber);
		//根据班次序号取出班次的详细信息
		Banci banci2 = banciService.getBanciByBean(banci);
		
		dutyPlans.setDutyOffTime(banci2.getDutyOfftime());
		
		dutyPlans.setDutyOnTime(banci2.getDutyOntime());
		
		dutyPlans.setDutyType(bean.getDutyType());
		
		dutyPlans.setEmployeeId(bean.getEmployeeId());
		
		dutyPlans.setSeq(bean.getSeq());
		
		List<DutyPlans> list = dutyPlansService.getDutyPlansByBean(dutyPlans);
		
		dutyPlans.setRoles_value(bean.getRolesValue());
		
		dutyPlans.setPlateNum(bean.getPlateNum());
		
		dutyPlans.setCreateAt(new Date());
		
		dutyPlans.setStatus(1);
		
		dutyPlans.setCreateId(bean.getCreatId());

		if (list.size() == 0 || list == null) {
			dutyPlansService.addDutyPlans(dutyPlans);
		}
		int i = dutyForBanciDao.insertInfo("insertDuty", bean);
		return i;
	}

	/**
	 * 根据条件获取一条排班计划
	 */
	public DutyForBanci getDutyByBean(DutyForBanci bean) {
		return dutyForBanciDao.getInfoByBean("getDutyByBean", bean);
	}

	/**
	 * 根据条件获取排班计划的列表
	 */
	public List<DutyForBanci> getDutyListByBean(DutyForBanci bean) {
		return dutyForBanciDao.getInfoListByBean("getDutyListByBean", bean);
	}

	/**
	 * 根据条件获取排班计划的总数
	 */
	public int getDutyCountByBean(DutyForBanci bean) {
		return dutyForBanciDao.getInfoCount("getDutyCountByBean", bean);
	}

	/**
	 * 更新排班计划
	 */
	public int updateDutyByBean(DutyForBanci bean) {
		//定义排班对象
		DutyPlans dutyPlans = new DutyPlans();
		
		dutyPlans.setDistributorId(bean.getDistributorId());
		
		dutyPlans.setDutyAreaId(bean.getDutyAreaId());
		
		dutyPlans.setDutyFromDate(bean.getDutyFromDate());
		
		dutyPlans.setDutyToDate(bean.getDutyToDate());
		
		dutyPlans.setDutyPointId(bean.getDutyPointId());
		
		int dutyNumber = bean.getDutyNumber();
		
		Banci banci = new Banci();
		banci.setDutyNumber(dutyNumber);
		//根据班次序号取出班次的详细信息
		Banci banci2 = banciService.getBanciByBean(banci);
		
		dutyPlans.setDutyOffTime(banci2.getDutyOfftime());
		
		dutyPlans.setDutyOnTime(banci2.getDutyOntime());
		
		dutyPlans.setDutyType(bean.getDutyType());
		
		dutyPlans.setEmployeeId(bean.getEmployeeId());
		
		dutyPlans.setSeq(bean.getSeq());
		
		dutyPlans.setRoles_value(bean.getRolesValue());
		
		dutyPlans.setPlateNum(bean.getPlateNum());
		
		dutyPlans.setCreateAt(new Date());
		
		dutyPlans.setStatus(bean.getStatus());
		
		dutyPlans.setUpdateAt(new Date());
		
		dutyPlans.setUpdateId(bean.getUpdateId());
		
		dutyPlansService.updateDutyPlans(dutyPlans);
		
		return dutyForBanciDao.updateInfoByBean("updateDutyByBean", bean);
	}

	/**
	 * 删除排班计划
	 */
	public int deleteDutyByBean(DutyForBanci bean) {
		
		//定义排班对象
		DutyPlans dutyPlans = new DutyPlans();
		
		dutyPlans.setSeq(bean.getSeq());
		
		dutyPlans.setUpdateAt(new Date());
		
		dutyPlans.setUpdateId(bean.getUpdateId());
		
		dutyPlansService.deleteDutyPlans(dutyPlans);
		
		return dutyForBanciDao.deleteInfoByBean("deleteDutyByBean", bean);
	}

	/**
	 * 调换班次
	 */
	public int changeBanci(int updateId) {
		//将班次信息取出存入map中便于更新dutyplans表
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		Banci banci = new Banci();
		banci.setDutyRoles(1);
		//根据班次序号取出班次的详细信息
		List<Banci> banciList = banciService.getAllBanciList(banci);
		for (Banci banci2 : banciList) {
			if (!map.containsKey(banci2.getDutyNumber())) { //初始化map集合
				map.put(banci2.getDutyNumber(), banci2);
			}
		}
		
		//定义dutyplans对象
		DutyPlans dutyPlans = null;
		//1.获取所有环卫工的班次
		DutyForBanci bean = new DutyForBanci();
		bean.setRolesValue(1); //表示只查询出环卫工的班次信息
		List<DutyForBanci> changeList = new ArrayList<DutyForBanci>();
		List<DutyPlans> dutyPlansList = new ArrayList<DutyPlans>();
		List<DutyForBanci> list = dutyForBanciDao.getInfoListByBean("getDutyForBanciList", bean);
		
		//2.遍历班次,将班次进行对调(1换成2 3换成 4 2换成1 4换成3 )
		for (DutyForBanci dutyForBanci : list) {
			int number = dutyForBanci.getDutyNumber();
			dutyForBanci.setUpdateId(updateId);
			if (number == 1) {
				dutyForBanci.setDutyNumber(2);
				changeList.add(dutyForBanci);
			}else if (number == 2) {
				dutyForBanci.setDutyNumber(1);
				changeList.add(dutyForBanci);
			}else if (number == 3) {
				dutyForBanci.setDutyNumber(4);
				changeList.add(dutyForBanci);
			}else if (number == 4) {
				dutyForBanci.setDutyNumber(3);
				changeList.add(dutyForBanci);
			}else if (number == 5) {
				dutyForBanci.setDutyNumber(6);
				changeList.add(dutyForBanci);
			}else if (number == 6) {
				dutyForBanci.setDutyNumber(5);
				changeList.add(dutyForBanci);
			}else if (number == 8) {
				dutyForBanci.setDutyNumber(9);
				changeList.add(dutyForBanci);
			}else if (number == 9) {
				dutyForBanci.setDutyNumber(8);
				changeList.add(dutyForBanci);
			}else if (number == 10) { // 有三个责任点比较特殊需换到12班次
				int dutyPointId = dutyForBanci.getDutyPointId();
				if (dutyPointId == 224 || dutyPointId == 225 || dutyPointId == 226) {
					dutyForBanci.setDutyNumber(12);
					changeList.add(dutyForBanci);
				}else {
				dutyForBanci.setDutyNumber(11);
				changeList.add(dutyForBanci);
				}
			}else if (number == 11) {
				dutyForBanci.setDutyNumber(10);
				changeList.add(dutyForBanci);
			}else if ( number == 12) {
				dutyForBanci.setDutyNumber(10);
				changeList.add(dutyForBanci);
			}
			//生成dutyplans数据信息
			dutyPlans = new DutyPlans();
			
			//从map集合中获取计划上下班时间
			int banciNumber = dutyForBanci.getDutyNumber();
			
			Banci banciinfo = (Banci) map.get(banciNumber);
			if(banciinfo != null){
				dutyPlans.setDutyOffTime(banciinfo.getDutyOfftime());
				
				dutyPlans.setDutyOnTime(banciinfo.getDutyOntime());
				
				dutyPlans.setUpdateId(dutyForBanci.getUpdateId());
				
				dutyPlans.setSeq(dutyForBanci.getSeq());
				
				dutyPlans.setUpdateAt(new Date());
				
				dutyPlans.setUpdateId(updateId);
				
				dutyPlansList.add(dutyPlans);
			}
		}
		int i =0;
		try {
			//批量处理dutyplans
			dutyPlansService.beatchUpdateForDutyPlans(dutyPlansList);
			//3.调用dao层方法进行批量更新排班计划
			i = dutyForBanciDao.beatchUpdateForDuty(changeList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	/**
	 * 根据seq获取排班计划
	 */
	public DutyPlans getDutyPlansByBean(DutyPlans dutyplans) {
		return dutyForBanciDao.getDutyPlansByBean(dutyplans);
	}

	/**
	 * 根据员工ID,责任区ID，责任点ID和日期查询出员工的考勤记录
	 */
	public List<AttendanceForPlans> getAttendanceForPlansByBean(
			DutyPlans dutyplans) {
		return dutyForBanciDao.getAttendanceForPlansByBean(dutyplans);
	}

	/**
	 * 若考勤记录不为空，根据员工ID,责任区ID,责任点ID和当前日期查询出环卫工的排班计划列表
	 */
	public List<DutyPlans> getDutyPlansListByBean(
			AttendanceForPlans attendanceForPlans) {
		return dutyForBanciDao.getDutyPlansListByBean(attendanceForPlans);
	}

	/**
	 * 根据员工ID，当前日期，责任点ID，责任区ID，删除考勤记录
	 */
	public int deleteAttendanceByBean(AttendanceForPlans attendanceForPlans) {
		return dutyForBanciDao.deleteAttendanceByBean(attendanceForPlans);
	}

	/**
	 *  更新那条不一致的考勤记录
	 */
	public int updateAttendanceForPlansByBean(AttendanceForPlans attendanceForPlans) {
		return dutyForBanciDao.updateAttendanceForPlansByBean(attendanceForPlans);
	}

	/**
	 * 根据bean删除多余的考勤记录
	 */
	public int deleteAttendanceForPlansByBean(AttendanceForPlans attendanceForPlans) {
		return dutyForBanciDao.deleteAttendanceForPlansByBean(attendanceForPlans);
	}

	/**
	 * 根据班次获取排班计划信息，2016-09-28
	 */
	public List<DutyForBanci> getDutyForBanciListByNumber(String dutynumber) {
		return dutyForBanciDao.getInfoListById("getDutyForBanciListByNumber", dutynumber);
	}

	/**
	 * 批量删除排班计划，即，批量更新排班计划状态为2，2016-09-28，即批量物理删除排版计划，2016-10-28，又改成：即批量物理删除排版计划，2016-10-28
	 */
	public int beatchDeleteForDutyForBanci(List<DutyForBanci> dutyForBanciList) {
		return dutyForBanciDao.beatchDeleteForDutyForBanci(dutyForBanciList);
	}


}
