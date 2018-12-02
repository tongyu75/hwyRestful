package com.czz.hwy.bean.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DutyRecode {

	private String username;
	private String roleName ;
	private String status ;
	private Date recordTime ;
	private List<Integer> rolesList = new ArrayList<Integer>();
	private int areaId;
	private int point;
	private int employeeId;
	private String areaName;
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getPointName() {
		return pointName;
	}
	public void setPointName(String pointName) {
		this.pointName = pointName;
	}
	private String pointName;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}
	public List<Integer> getRolesList() {
		return rolesList;
	}
	public void setRolesList(List<Integer> rolesList) {
		this.rolesList = rolesList;
	}
	public int getAreaId() {
		return areaId;
	}
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	
	

	
}
