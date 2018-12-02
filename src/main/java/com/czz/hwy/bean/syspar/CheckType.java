package com.czz.hwy.bean.syspar;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

public class CheckType  extends PageReqParamDTO  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  int   id;
	private  String evalValue;
	private  String evalName;
	private  String remark;
	private  Date  createAt;
	private  Date  updateAt;
	private  int role1;
	private  int role2;
	private  int role3;
	private  int role4;
	private  int ishwg;
	private  int isjcy;
	private  int iskhy;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public int getRole1() {
		return role1;
	}
	public void setRole1(int role1) {
		this.role1 = role1;
	}
	public int getRole2() {
		return role2;
	}
	public void setRole2(int role2) {
		this.role2 = role2;
	}
	public int getRole3() {
		return role3;
	}
	public void setRole3(int role3) {
		this.role3 = role3;
	}
	public int getRole4() {
		return role4;
	}
	public void setRole4(int role4) {
		this.role4 = role4;
	}
	public int getIshwg() {
		return ishwg;
	}
	public void setIshwg(int ishwg) {
		this.ishwg = ishwg;
	}
	public int getIsjcy() {
		return isjcy;
	}
	public void setIsjcy(int isjcy) {
		this.isjcy = isjcy;
	}
	public int getIskhy() {
		return iskhy;
	}
	public void setIskhy(int iskhy) {
		this.iskhy = iskhy;
	}
	public String getEvalValue() {
		return evalValue;
	}
	public void setEvalValue(String evalValue) {
		this.evalValue = evalValue;
	}
	
	
	
	
	

}
