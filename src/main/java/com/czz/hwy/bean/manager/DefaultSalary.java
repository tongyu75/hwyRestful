package com.czz.hwy.bean.manager;

import java.io.Serializable;

import com.czz.hwy.base.PageReqParamDTO;

/**
  * 默认工资
  * @author 张咏雪 2016-5-7
  * @version V0.1
  */
public class DefaultSalary extends PageReqParamDTO  implements Serializable {

    private static final long serialVersionUID = -8406630388176944195L;

    private int id;//主键ID
    
    private String employeeId;//员工ID
    
    private String defaultSalary;//应发工资
    
    private String updateId;//修改人ID
    
    private String status;//使用状态，1启用 2 作废
    
    private String updateAt;//修改时间
    
    //以下不是表字段
    private String employeeName;//员工名称
    private String initStatus;//初始化状态 1未初始化 2已初始化
    private String updateName;//修改人名称
    private String roleId;//角色ID
    private String roleName;//角色名称
    private String range;//指定默认工资范围  1所有的 2未初始化的 3已初始化的

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getDefaultSalary() {
		return defaultSalary;
	}

	public void setDefaultSalary(String defaultSalary) {
		this.defaultSalary = defaultSalary;
	}

	public String getUpdateId() {
		return updateId;
	}

	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}

	public String getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(String updateAt) {
		this.updateAt = updateAt;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
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

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}

	public String getInitStatus() {
		return initStatus;
	}

	public void setInitStatus(String initStatus) {
		this.initStatus = initStatus;
	}
	
}