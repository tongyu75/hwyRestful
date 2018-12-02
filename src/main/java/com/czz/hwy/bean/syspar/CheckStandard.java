package com.czz.hwy.bean.syspar;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

public class CheckStandard extends PageReqParamDTO  implements Serializable{

	/**
	 * 
	 */
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private  int id;
	private  int areaid;
	private  String areaname;
	private  int pointid;
	private  String pointname;
	private  int worktype;
	private  int evaltype;
	private  String evalname;
	private  String evaldesc;
	private  double standardvalue;
	private  String standardunit;
	private  String limitvalue;
	private  String limitunit;
	private  Date  createAt;
	private  Date  updateAt;
	

	public String getAreaname() {
		return areaname;
	}
	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}
	public String getPointname() {
		return pointname;
	}
	public void setPointname(String pointname) {
		this.pointname = pointname;
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
	public int getPointid() {
		return pointid;
	}
	public void setPointid(int pointid) {
		this.pointid = pointid;
	}
	public int getWorktype() {
		return worktype;
	}
	public void setWorktype(int worktype) {
		this.worktype = worktype;
	}
	public int getEvaltype() {
		return evaltype;
	}
	public void setEvaltype(int evaltype) {
		this.evaltype = evaltype;
	}
	public String getEvalname() {
		return evalname;
	}
	public void setEvalname(String evalname) {
		this.evalname = evalname;
	}
	public String getEvaldesc() {
		return evaldesc;
	}
	public void setEvaldesc(String evaldesc) {
		this.evaldesc = evaldesc;
	}
	public double getStandardvalue() {
		return standardvalue;
	}
	public void setStandardvalue(double standardvalue) {
		this.standardvalue = standardvalue;
	}
	public String getStandardunit() {
		return standardunit;
	}
	public void setStandardunit(String standardunit) {
		this.standardunit = standardunit;
	}
	public String getLimitvalue() {
		return limitvalue;
	}
	public void setLimitvalue(String limitvalue) {
		this.limitvalue = limitvalue;
	}
	public String getLimitunit() {
		return limitunit;
	}
	public void setLimitunit(String limitunit) {
		this.limitunit = limitunit;
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
