package com.czz.hwy.bean.manager;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
  * 环卫云项目启用
  * @author 李宇宁 16-七月-2015
  * @version V0.1
  */
public class FinesDetail extends PageReqParamDTO  implements Serializable {

    private static final long serialVersionUID = -7608326728538919792L;

    // ID
    private int id;
    // 员工号
    private int employeeId;
    // 部门号
    private int deptId;
    // 罚款金额
    private double amount;
    // 罚款标准ID
    private int standId;
    // 考核类型ID
    private int evalId;
    // 工作内容ID
    private int workId ;
    // 检查ID
    private int checkId;
    // 创建时间
    private Date createAt;
    // 修改时间
    private Date updateAt;
    // 处罚者
    private int checkUser;
    // 处罚时间
    private Date checkTime;
    // 备注
    private String remark;
	/**
	  * 设定ID
	  * @param id
      */
	public void setId(int id) {
	   this.id = id;
	}
	/**
	  * 获取ID
	  * @return ID
      */
	public int getId() {
	   return id;
	}
	/**
	  * 设定员工号
	  * @param employeeId
      */
	public void setEmployeeId(int employeeId) {
	   this.employeeId = employeeId;
	}
	/**
	  * 获取员工号
	  * @return 员工号
      */
	public int getEmployeeId() {
	   return employeeId;
	}
	/**
	  * 设定部门号
	  * @param deptId
      */
	public void setDeptId(int deptId) {
	   this.deptId = deptId;
	}
	/**
	  * 获取部门号
	  * @return 部门号
      */
	public int getDeptId() {
	   return deptId;
	}
	/**
	  * 设定罚款金额
	  * @param amount
      */
	public void setAmount(double amount) {
	   this.amount = amount;
	}
	/**
	  * 获取罚款金额
	  * @return 罚款金额
      */
	public double getAmount() {
	   return amount;
	}
	/**
	  * 设定罚款标准ID
	  * @param standId
      */
	public void setStandId(int standId) {
	   this.standId = standId;
	}
	/**
	  * 获取罚款标准ID
	  * @return 罚款标准ID
      */
	public int getStandId() {
	   return standId;
	}
	/**
	  * 设定考核类型ID
	  * @param evalId
      */
	public void setEvalId(int evalId) {
	   this.evalId = evalId;
	}
	/**
	  * 获取考核类型ID
	  * @return 考核类型ID
      */
	public int getEvalId() {
	   return evalId;
	}
	/**
	  * 设定工作内容ID
	  * @param workId 
      */
	public void setWorkId (int workId ) {
	   this.workId  = workId ;
	}
	/**
	  * 获取工作内容ID
	  * @return 工作内容ID
      */
	public int getWorkId () {
	   return workId ;
	}
	/**
	  * 设定检查ID
	  * @param checkId
      */
	public void setCheckId(int checkId) {
	   this.checkId = checkId;
	}
	/**
	  * 获取检查ID
	  * @return 检查ID
      */
	public int getCheckId() {
	   return checkId;
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
	  * 设定处罚者
	  * @param checkUser
      */
	public void setCheckUser(int checkUser) {
	   this.checkUser = checkUser;
	}
	/**
	  * 获取处罚者
	  * @return 处罚者
      */
	public int getCheckUser() {
	   return checkUser;
	}
	/**
	  * 设定处罚时间
	  * @param checkTime
      */
	public void setCheckTime(Date checkTime) {
	   this.checkTime = checkTime;
	}
	/**
	  * 获取处罚时间
	  * @return 处罚时间
      */
	public Date getCheckTime() {
	   return checkTime;
	}
	/**
	  * 设定备注
	  * @param remark
      */
	public void setRemark(String remark) {
	   this.remark = remark;
	}
	/**
	  * 获取备注
	  * @return 备注
      */
	public String getRemark() {
	   return remark;
	}
	private String employeeUser;
	private String checkedUser;
	private String deptName;
	private String evalName;
	private int appealStatus;//申诉状态
	public String getEmployeeUser() {
		return employeeUser;
	}
	public int getAppealStatus() {
		return appealStatus;
	}
	public void setAppealStatus(int appealStatus) {
		this.appealStatus = appealStatus;
	}
	public void setEmployeeUser(String employeeUser) {
		this.employeeUser = employeeUser;
	}
	public String getCheckedUser() {
		return checkedUser;
	}
	public void setCheckedUser(String checkedUser) {
		this.checkedUser = checkedUser;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getEvalName() {
		return evalName;
	}
	public void setEvalName(String evalName) {
		this.evalName = evalName;
	}
	
}