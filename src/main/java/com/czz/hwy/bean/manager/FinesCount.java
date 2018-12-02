package com.czz.hwy.bean.manager;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
  * 环卫云项目启用
  * @author 李宇宁 16-七月-2015
  * @version V0.1
  */
public class FinesCount extends PageReqParamDTO  implements Serializable {

    private static final long serialVersionUID = -8406630388176944195L;

    // ID
    private int id;
    // 员工号
    private int employeeId;
    // 部门号
    private int deptId;
    // 罚款金额
    private double amount;
    // 罚款次数
    private int count;
    // 创建时间
    private Date createAt;
    // 修改时间
    private Date updateAt;
    // 根据月份统计
    private Date month;
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
	  * 设定罚款次数
	  * @param count
      */
	public void setCount(int count) {
	   this.count = count;
	}
	/**
	  * 获取罚款次数
	  * @return 罚款次数
      */
	public int getCount() {
	   return count;
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
	  * 设定月份信息
	  * @param month
     */
	public Date getMonth() {
		return month;
	}
	/**
	  * 获取月份信息
	  * @return 月份信息
     */
	public void setMonth(Date month) {
		this.month = month;
	}
	private String deptName;
	private String showname;
	private String months;
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getShowname() {
		return showname;
	}
	public void setShowname(String showname) {
		this.showname = showname;
	}
	public String getMonths() {
		return months;
	}
	public void setMonths(String months) {
		this.months = months;
	}
	
}