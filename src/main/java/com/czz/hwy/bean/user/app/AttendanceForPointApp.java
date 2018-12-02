package com.czz.hwy.bean.user.app;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
 * 记录每天每人每时间段上班的责任点
 * @author 张咏雪
 * @createDate 2017-01-10
 */
public class AttendanceForPointApp extends PageReqParamDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int id; //主键ID
	private int employeeId; //当前用户的员工号
	private String employeeName;//员工名称
	private int roleId;//角色Id
	private int areaId; //责任区的id
	private int areaName; //责任区名称
	private int pointId; //责任点的id
	private String pointName; //责任点名称
	private Date dutyOnTime; //计划上班时间
	private Date dutyOffTime; //计划下班时间
	private Date recordDate; //记录的时间
	private int isCoverwork; // 是否是代班责任点  1 是 2 不是
	private Date createAt;//创建时间，主要是用于查看责任点记录的创建时间
	private Date updateAt;//更新时间，主要是用于查看什么时候is_coverwork字段变为1
	
	//以下不是表段
	private String roleName;//角色名称
	private int leaveId;//请假人ID，标识代班人代的谁的班
	

	
	public int getLeaveId() {
		return leaveId;
	}

	public void setLeaveId(int leaveId) {
		this.leaveId = leaveId;
	}

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

	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	public int getAreaName() {
		return areaName;
	}

	public void setAreaName(int areaName) {
		this.areaName = areaName;
	}

	public int getPointId() {
		return pointId;
	}

	public void setPointId(int pointId) {
		this.pointId = pointId;
	}

	public String getPointName() {
		return pointName;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
	}

	public Date getDutyOnTime() {
		return dutyOnTime;
	}

	public void setDutyOnTime(Date dutyOnTime) {
		this.dutyOnTime = dutyOnTime;
	}

	public Date getDutyOffTime() {
		return dutyOffTime;
	}

	public void setDutyOffTime(Date dutyOffTime) {
		this.dutyOffTime = dutyOffTime;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public int getIsCoverwork() {
		return isCoverwork;
	}

	public void setIsCoverwork(int isCoverwork) {
		this.isCoverwork = isCoverwork;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
