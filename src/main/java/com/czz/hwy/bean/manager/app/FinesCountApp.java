package com.czz.hwy.bean.manager.app;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
  * 罚款总计实体类，用于app接口
  * @author 张咏雪 2016-12-20
  * @version V0.1
  */
public class FinesCountApp extends PageReqParamDTO  implements Serializable {

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
    private String deptName;
	private String showname;
	private String months;
	
	
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
	public int getDeptId() {
		return deptId;
	}
	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	public Date getUpdateAt() {
		return updateAt;
	}
	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}
	public Date getMonth() {
		return month;
	}
	public void setMonth(Date month) {
		this.month = month;
	}
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