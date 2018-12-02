package com.czz.hwy.bean.mission.watch;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
  * 环卫云项目启用
  * @author 李宇宁 16-七月-2015
  * @version V0.1
  */
public class DutyPlans extends PageReqParamDTO implements Serializable,Cloneable {

    private static final long serialVersionUID = 6508453065565473207L;

    // 分配者ID
    private int distributorId;
    // 分配者名字
    private String distributorName;
    // 应出勤责任区ID
    private int dutyAreaId;
    // 应出勤责任区名称
    private String dutyAreaName;
    // 应出勤责任点ID
    private int dutyPointId;
    // 应出勤责任点名称
    private String dutyPointName;
    // 1：机械2：人力
    private int dutyType;
    // 任务类型名称
    private String dutyTypename;
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
    //应出勤者姓名
    private String showname;
    // 创建时间
    private Date createAt;
    // 修改时间
    private Date updateAt;
    // 出勤车辆
    private String plateNum;
    // 
    private Date checkTime;
    //角色编码
    private int roles_value;
    //前后端通用唯一标识
    private String seq;
    //状态1启用2禁用
    private int status;
    //创建者id
    private int createId;
    //更新者id
    private int updateId;
    // 电话号码
    private String tel;
    // 角色值
    private int roleValue;
    
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
	  * @param dutyPointId
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
	public String getPlateNum() {
		return plateNum;
	}
	public void setPlateNum(String plateNum) {
		this.plateNum = plateNum;
	}
	public int getRoles_value() {
		return roles_value;
	}
	public void setRoles_value(int roles_value) {
		this.roles_value = roles_value;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getCreateId() {
		return createId;
	}
	public void setCreateId(int createId) {
		this.createId = createId;
	}
	public int getUpdateId() {
		return updateId;
	}
	public void setUpdateId(int updateId) {
		this.updateId = updateId;
	}
	public String getShowname() {
		return showname;
	}
	public void setShowname(String showname) {
		this.showname = showname;
	}
	public String getDutyTypename() {
		return dutyTypename;
	}
	public void setDutyTypename(String dutyTypename) {
		this.dutyTypename = dutyTypename;
	}
	public String getDistributorName() {
		return distributorName;
	}
	public void setDistributorName(String distributorName) {
		this.distributorName = distributorName;
	}
	public String getDutyAreaName() {
		return dutyAreaName;
	}
	public void setDutyAreaName(String dutyAreaName) {
		this.dutyAreaName = dutyAreaName;
	}
	public String getDutyPointName() {
		return dutyPointName;
	}
	public void setDutyPointName(String dutyPointName) {
		this.dutyPointName = dutyPointName;
	}
	
	public DutyPlans clone(){
		
		DutyPlans dp = null;
		
		try {
			dp = (DutyPlans)super.clone();
		} catch (CloneNotSupportedException e) {
			
			e.printStackTrace();
		}
		
		return dp;
	}
    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }
    public int getRoleValue() {
        return roleValue;
    }
    public void setRoleValue(int roleValue) {
        this.roleValue = roleValue;
    }
}