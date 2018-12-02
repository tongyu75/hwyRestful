package com.czz.hwy.bean.user.app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.czz.hwy.base.PageReqParamDTO;

/**
 * 返回查岗考勤记录实体类，用于app接口
 * @author 张咏雪， 2016-12-29
 *
 */
public class DutyRecodeApp extends PageReqParamDTO implements Serializable {

	private static final long serialVersionUID = 1597023096156371609L;
	
	private int id;
	private String username;//员工名称
	private int roleId;//角色ID
	private String roleName ;//角色名称
	private double lat;//纬度
	private double lng;//经度
	private String status ; //上班状态或下班状态
	private Date recordTime ;//
	private List<Integer> rolesList = new ArrayList<Integer>();
	private int areaId;//责任区ID
	private int point; //责任点ID
	private int employeeId;//员工ID
	private String areaName;//责任区名称
	private String pointName;//用于保存责任点名称字符串，以逗号分隔
	private String reason;//修改上班状态或下班状态的理由
	private String pointIds;//用于保存责任点字符串，以逗号分隔
	private Date dutyOnTime;//上班时间
	private Date dutyOffTime;//下班时间
	
	//2017-04-21
	private double mobileDistance;//上班时间内的移动距离，以千米为单位，保留两位小数
	
	public double getMobileDistance() {
		return mobileDistance;
	}
	public void setMobileDistance(double mobileDistance) {
		this.mobileDistance = mobileDistance;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getPointIds() {
		return pointIds;
	}
	public void setPointIds(String pointIds) {
		this.pointIds = pointIds;
	}
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
