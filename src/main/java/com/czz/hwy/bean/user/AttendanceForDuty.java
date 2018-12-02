package com.czz.hwy.bean.user;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

public class AttendanceForDuty extends PageReqParamDTO implements Serializable,Cloneable{

	private static final long serialVersionUID = -1594099608032805013L;

	//信息id
	private int id;
	//员工号
	private int employeeId;
	//上班日期
	private Date dutyDate;
	//应上班时间
	private Date dutyOnTime;
	//上班记录时间
	private Date recordOnTime;
	//应下班时间
	private Date dutyOffTime;
	//记录下班时间
	private Date recordOffTime;
	//上班状态
	private String goOnStatus;
	//下班状态
	private String getOffStatus;
	//创建时间
	private Date createAt;
	//修改时间
	private Date updateAt;
	//记录月份
	private Date month;
	//判定人员当前日期数据是否录入 false标识未录入 需要做新增操作 true做修改操作
	private boolean insert_flag;
	//是否更新
	private boolean update_flag;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public Date getDutyDate() {
		return dutyDate;
	}

	public void setDutyDate(Date dutyDate) {
		this.dutyDate = dutyDate;
	}

	public Date getDutyOnTime() {
		return dutyOnTime;
	}

	public void setDutyOnTime(Date dutyOnTime) {
		this.dutyOnTime = dutyOnTime;
	}

	public Date getRecordOnTime() {
		return recordOnTime;
	}

	public void setRecordOnTime(Date recordOnTime) {
		this.recordOnTime = recordOnTime;
	}

	public Date getDutyOffTime() {
		return dutyOffTime;
	}

	public void setDutyOffTime(Date dutyOffTime) {
		this.dutyOffTime = dutyOffTime;
	}

	public Date getRecordOffTime() {
		return recordOffTime;
	}

	public void setRecordOffTime(Date recordOffTime) {
		this.recordOffTime = recordOffTime;
	}

	public String getGoOnStatus() {
		return goOnStatus;
	}

	public void setGoOnStatus(String goOnStatus) {
		this.goOnStatus = goOnStatus;
	}

	public String getGetOffStatus() {
		return getOffStatus;
	}

	public void setGetOffStatus(String getOffStatus) {
		this.getOffStatus = getOffStatus;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Date getMonth() {
		return month;
	}

	public void setMonth(Date month) {
		this.month = month;
	}
	//员工名称
	private String showname;
	//部门名称
	private String deptName;

	public String getShowname() {
		return showname;
	}

	public void setShowname(String showname) {
		this.showname = showname;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	public boolean isUpdate_flag() {
		return update_flag;
	}

	public void setUpdate_flag(boolean update_flag) {
		this.update_flag = update_flag;
	}

	public boolean isInsert_flag() {
		return insert_flag;
	}

	public void setInsert_flag(boolean insert_flag) {
		this.insert_flag = insert_flag;
	}
	
	public AttendanceForDuty clone(){
		
		AttendanceForDuty obj = null;
		
		try {
			obj = (AttendanceForDuty)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return obj;
	}
}
