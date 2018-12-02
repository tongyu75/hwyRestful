package com.czz.hwy.bean.manager;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
 * 环卫工登录app状态统计
 * @author 张咏雪  2016-08-01
 *
 */
public class HwgLoginStatus extends PageReqParamDTO implements Serializable{

	private static final long serialVersionUID = -1594099608032805013L;
	
	private String employeeId;//员工ID
	
	private String employeeName;//员工名称
	
	private String areaId;//责任区ID
	
	private String areaName;//责任区名称
	
	private String loginStatus;//登录状态 1已登录 2未登录 
	
	private String createAt;//登录时间

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}

	public String getCreateAt() {
		return createAt;
	}

	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}
	
}
