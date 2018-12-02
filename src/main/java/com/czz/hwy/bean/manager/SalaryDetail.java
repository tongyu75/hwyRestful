package com.czz.hwy.bean.manager;

import java.io.Serializable;

import com.czz.hwy.base.PageReqParamDTO;

/**
  * 实发工资详细信息
  * @author 张咏雪 2016-5-7
  * @version V0.1
  */
public class SalaryDetail extends PageReqParamDTO  implements Serializable {

    private static final long serialVersionUID = -8406630388176944195L;
    
    private int id;//主键ID
    
    private String employeeId;//员工
    
    private String createId;//创建人ID
    
    private String createAt;//创建时间
    
    private String updateId;//修改人ID
    
    private String updateAt;//修改时间
    
    private String remark;//备注
    
    private String status;//状态： 1 生成 2 发布
    
    private String month;//年月
    
    private String defaultSalary;//应发工资
    
    private String fineSalary;//罚款总额
    
    private String minusAmount;//当月扣除额
    
    private String increaseAmount;//当月增加额
    
    private String realSalary;//实发工资，实发工资=应发工资-罚款总额-当月扣除额+当月增加额
    
    
    //以下不是表字段
    private String empNum;//每月员工个数
    private String employeeName;//员工名称
    private String createName;//创建人名称
    private String updateName;//修改人名称

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
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

	public String getFineSalary() {
		return fineSalary;
	}

	public void setFineSalary(String fineSalary) {
		this.fineSalary = fineSalary;
	}

	public String getMinusAmount() {
		return minusAmount;
	}

	public void setMinusAmount(String minusAmount) {
		this.minusAmount = minusAmount;
	}

	public String getIncreaseAmount() {
		return increaseAmount;
	}

	public void setIncreaseAmount(String increaseAmount) {
		this.increaseAmount = increaseAmount;
	}

	public String getRealSalary() {
		return realSalary;
	}

	public void setRealSalary(String realSalary) {
		this.realSalary = realSalary;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public String getCreateAt() {
		return createAt;
	}

	public void setCreateAt(String createAt) {
		this.createAt = createAt;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEmpNum() {
		return empNum;
	}

	public void setEmpNum(String empNum) {
		this.empNum = empNum;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
    
}