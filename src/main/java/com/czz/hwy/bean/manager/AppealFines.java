package com.czz.hwy.bean.manager;

import java.util.Date;

/**
 * 申诉处罚的bean对象
 * 功能描述
 * @author 万仁义 wanrenyi@chengzhongzhi.com
 * @company chnegzhongzhi
 * @createDate 2016年4月27日 上午9:35:05
 */
public class AppealFines {
	
	private int id; //当前申诉的主键
	private int finesId; //罚款的id
	private int employeeId; //申诉人的id
	private String appealReason; //申诉理由
	private int status; //申诉状态 0 表示创建一个申诉 1 表示驳回申诉 2表示撤销处罚
	private int dropUser; //撤销处罚人的id
	private String dropReason; //撤销理由
	private Date createat; //创建时间
	private Date updateat; //修改时间
	
	
	//以下不是表字段
	private String employeeUser;//申诉人名称
	private String dropUserName;//申诉处理人名称
	private String remark;//处罚详情
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFinesId() {
		return finesId;
	}
	public void setFinesId(int finesId) {
		this.finesId = finesId;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public String getAppealReason() {
		return appealReason;
	}
	public void setAppealReason(String appealReason) {
		this.appealReason = appealReason;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getDropUser() {
		return dropUser;
	}
	public void setDropUser(int dropUser) {
		this.dropUser = dropUser;
	}
	public String getDropReason() {
		return dropReason;
	}
	public void setDropReason(String dropReason) {
		this.dropReason = dropReason;
	}
	public Date getCreateat() {
		return createat;
	}
	public void setCreateat(Date createat) {
		this.createat = createat;
	}
	public Date getUpdateat() {
		return updateat;
	}
	public void setUpdateat(Date updateat) {
		this.updateat = updateat;
	}
	public String getEmployeeUser() {
		return employeeUser;
	}
	public void setEmployeeUser(String employeeUser) {
		this.employeeUser = employeeUser;
	}
	public String getDropUserName() {
		return dropUserName;
	}
	public void setDropUserName(String dropUserName) {
		this.dropUserName = dropUserName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
