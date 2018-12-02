package com.czz.hwy.bean.mission.app;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
 * 替班人员管理bean对象
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-09-29
 */
public class RelayApp extends PageReqParamDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int id;//主键id
	
	private int areaId;//责任区Id（外键）
	
	private int employeeId;//替班人ID（外键）
	
	private int createId;//创建人Id
	
	private Date createAt;//创建时间
	
	private int updateId;//更新人Id
	
	private Date updateAt;//更新时间
	
	private String areaName;//责任区名称
	
	private String employeeName;//员工名称
	
	private int roleId;//角色ID
	
	private String roleName;//角色名称
	
	//用于获取请假人可能选择的代班人的查询条件
	private int leaveId;//请假人ID
	
	//用于获取代班人的工作时间
	private String workTime;
	
	
	public String getWorkTime() {
		return workTime;
	}

	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}

	public int getLeaveId() {
		return leaveId;
	}

	public void setLeaveId(int leaveId) {
		this.leaveId = leaveId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public int getCreateId() {
		return createId;
	}

	public void setCreateId(int createId) {
		this.createId = createId;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public int getUpdateId() {
		return updateId;
	}

	public void setUpdateId(int updateId) {
		this.updateId = updateId;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}
}
