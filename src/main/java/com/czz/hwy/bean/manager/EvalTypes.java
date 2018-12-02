package com.czz.hwy.bean.manager;

import java.util.Date;

public class EvalTypes {
	// 主键
	private Integer id ;
	// 考核值
	private Integer evalType;
	
	private Integer evalValue;
	// 考核名称
	private String evalName;
	// 是否是举报任务
	private Integer role1;
	// 是否是监察
	private Integer role2;
	// 是否是监察
	private Integer role3;
	// 是否是监察
	private Integer role4;
	// 备注
	private String remark;
	// 创建时间
	private Date createAt;
	// 修改时间
	private Date updateAt;
	
	private Integer isjcy;
	
	private Integer iskhy;
	
	private Integer ishwg;
	
	private Integer type;
	
	public Integer getEvalValue() {
		return evalValue;
	}
	public void setEvalValue(Integer evalValue) {
		this.evalValue = evalValue;
	}
	public Integer getIsjcy() {
		return isjcy;
	}
	public void setIsjcy(Integer isjcy) {
		this.isjcy = isjcy;
	}
	public Integer getIskhy() {
		return iskhy;
	}
	public void setIskhy(Integer iskhy) {
		this.iskhy = iskhy;
	}
	public Integer getIshwg() {
		return ishwg;
	}
	public void setIshwg(Integer ishwg) {
		this.ishwg = ishwg;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getRole1() {
		return role1;
	}
	public void setRole1(Integer role1) {
		this.role1 = role1;
	}
	public Integer getRole2() {
		return role2;
	}
	public void setRole2(Integer role2) {
		this.role2 = role2;
	}
	public Integer getRole3() {
		return role3;
	}
	public void setRole3(Integer role3) {
		this.role3 = role3;
	}
	public Integer getRole4() {
		return role4;
	}
	public void setRole4(Integer role4) {
		this.role4 = role4;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getEvalType() {
		return evalType;
	}
	public void setEvalType(Integer evalType) {
		this.evalType = evalType;
	}
	public String getEvalName() {
		return evalName;
	}
	public void setEvalName(String evalName) {
		this.evalName = evalName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
}
