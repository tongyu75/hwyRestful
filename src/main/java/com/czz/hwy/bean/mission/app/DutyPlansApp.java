package com.czz.hwy.bean.mission.app;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
  * 环卫云项目启用
  * @author 李宇宁 16-七月-2015
  * @version V0.1
  */
public class DutyPlansApp extends PageReqParamDTO implements Serializable {

    private static final long serialVersionUID = 6508453065565473207L;

    // 分配者ID
    private int distributorId;
    // 应出勤责任区ID
    private int dutyAreaId;
    // 应出勤责任区ID
    private int dutyPointId;
    // 1：机械2：人力
    private int dutyType;
    // 应出勤开始日期
    private Date dutyFromDate;
    // 应出勤结束日期
    private Date dutyToDate;
    // 应出勤时间
    private Date dutyOnTime;
    // 应签退时间
    private Date dutyOffTime;
    // 应出勤者ID
    private int employeeId;
    // 创建时间
    private Date createAt;
    // 修改时间
    private Date updateAt;
    // 出勤车辆
    private String plateNum;
    // 
    private Date checkTime;
    private int roleValue;//角色值
   private String pointName;
   private String areaStr;//责任区ID字符串
   
   private String taskStatus;//用于保存任务状态，不是表字段
   private int dutyNumber;//用于存放班次号
   
	public int getDutyNumber() {
	return dutyNumber;
}
public void setDutyNumber(int dutyNumber) {
	this.dutyNumber = dutyNumber;
}
	public String getTaskStatus() {
	return taskStatus;
}
public void setTaskStatus(String taskStatus) {
	this.taskStatus = taskStatus;
}
	public String getAreaStr() {
	return areaStr;
}
public void setAreaStr(String areaStr) {
	this.areaStr = areaStr;
}
	public String getPointName() {
	return pointName;
}
public void setPointName(String pointName) {
	this.pointName = pointName;
}
	public int getRoleValue() {
		return roleValue;
	}
	public void setRoleValue(int roleValue) {
		this.roleValue = roleValue;
	}
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	public int getDutyAreaId() {
		return dutyAreaId;
	}
	public void setDutyAreaId(int dutyAreaId) {
		this.dutyAreaId = dutyAreaId;
	}

	/**
	  * 设定分配者ID
	  * @param distributorId
      */
	public void setDistributorId(int distributorId) {
	   this.distributorId = distributorId;
	}
	/**
	  * 获取分配者ID
	  * @return 分配者ID
      */
	public int getDistributorId() {
	   return distributorId;
	}
	/**
	  * 设定应出勤责任区ID
	  * @param dutyAreaId
      */
	public void setDutyPointId(int dutyPointId) {
	   this.dutyPointId = dutyPointId;
	}
	/**
	  * 获取应出勤责任区ID
	  * @return 应出勤责任区ID
      */
	public int getDutyPointId() {
	   return dutyPointId;
	}
	/**
	  * 设定1：机械2：人力
	  * @param dutyType
      */
	public void setDutyType(int dutyType) {
	   this.dutyType = dutyType;
	}
	/**
	  * 获取1：机械2：人力
	  * @return 1：机械2：人力
      */
	public int getDutyType() {
	   return dutyType;
	}
	/**
	  * 设定应出勤开始日期
	  * @param dutyFromDate
      */
	public void setDutyFromDate(Date dutyFromDate) {
	   this.dutyFromDate = dutyFromDate;
	}
	/**
	  * 获取应出勤开始日期
	  * @return 应出勤开始日期
      */
	public Date getDutyFromDate() {
	   return dutyFromDate;
	}
	/**
	  * 设定应出勤结束日期
	  * @param dutyToDate
      */
	public void setDutyToDate(Date dutyToDate) {
	   this.dutyToDate = dutyToDate;
	}
	/**
	  * 获取应出勤结束日期
	  * @return 应出勤结束日期
      */
	public Date getDutyToDate() {
	   return dutyToDate;
	}
	/**
	  * 设定应出勤时间
	  * @param dutyOnTime
      */
	public void setDutyOnTime(Date dutyOnTime) {
	   this.dutyOnTime = dutyOnTime;
	}
	/**
	  * 获取应出勤时间
	  * @return 应出勤时间
      */
	public Date getDutyOnTime() {
	   return dutyOnTime;
	}
	/**
	  * 设定应签退时间
	  * @param dutyOffTime
      */
	public void setDutyOffTime(Date dutyOffTime) {
	   this.dutyOffTime = dutyOffTime;
	}
	/**
	  * 获取应签退时间
	  * @return 应签退时间
      */
	public Date getDutyOffTime() {
	   return dutyOffTime;
	}
	/**
	  * 设定应出勤者ID
	  * @param dutyId
      */
	public void setEmployeeId(int employeeId) {
	   this.employeeId = employeeId;
	}
	/**
	  * 获取应出勤者ID
	  * @return 应出勤者ID
      */
	public int getEmployeeId() {
	   return employeeId;
	}
	/**
	  * 设定创建时间
	  * @param createAt
      */
	public void setCreateAt(Date createAt) {
	   this.createAt = createAt;
	}
	/**
	  * 获取创建时间
	  * @return 创建时间
      */
	public Date getCreateAt() {
	   return createAt;
	}
	/**
	  * 设定修改时间
	  * @param updateAt
      */
	public void setUpdateAt(Date updateAt) {
	   this.updateAt = updateAt;
	}
	/**
	  * 获取修改时间
	  * @return 修改时间
      */
	public Date getUpdateAt() {
	   return updateAt;
	}
	/**
	  * 设定出勤车辆
	  * @param plateNum
      */
	public void setPlateNum(String plateNum) {
	   this.plateNum = plateNum;
	}
	/**
	  * 获取出勤车辆
	  * @return 出勤车辆
      */
	public String getPlateNum() {
	   return plateNum;
	}
}