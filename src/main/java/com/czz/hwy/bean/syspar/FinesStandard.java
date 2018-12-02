package com.czz.hwy.bean.syspar;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

public class FinesStandard extends PageReqParamDTO  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  int id;
	private  int areaid;
	private  String areaname;
	private  int pointid;
	private  String pointname;
	private  int evalid;
	private  String evalname;
	private  int  rolesid;
	private  String rolesname;
	private  double amount;
	private  Date createAt;
	private  Date updateAt;
	private int appealStatus; //是否允许申诉
	
	
	public int getAppealStatus() {
		return appealStatus;
	}
	public void setAppealStatus(int appealStatus) {
		this.appealStatus = appealStatus;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAreaid() {
		return areaid;
	}
	public void setAreaid(int areaid) {
		this.areaid = areaid;
	}
	public String getAreaname() {
		return areaname;
	}
	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}
	public int getPointid() {
		return pointid;
	}
	public void setPointid(int pointid) {
		this.pointid = pointid;
	}
	public String getPointname() {
		return pointname;
	}
	public void setPointname(String pointname) {
		this.pointname = pointname;
	}
	public int getEvalid() {
		return evalid;
	}
	public void setEvalid(int evalid) {
		this.evalid = evalid;
	}
	public String getEvalname() {
		return evalname;
	}
	public void setEvalname(String evalname) {
		this.evalname = evalname;
	}
	public int getRolesid() {
		return rolesid;
	}
	public void setRolesid(int rolesid) {
		this.rolesid = rolesid;
	}
	public String getRolesname() {
		return rolesname;
	}
	public void setRolesname(String rolesname) {
		this.rolesname = rolesname;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
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
