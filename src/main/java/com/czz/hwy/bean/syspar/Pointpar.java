package com.czz.hwy.bean.syspar;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

public class Pointpar extends PageReqParamDTO  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private  int id;
	private  int areaid;
	private  String areaname;
	private  int pointid;
	private  String pointname;
	private  int colrate;
	private  int reportrate;
	private  int staytime;
	private  Date creatAt;
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
	public int getColrate() {
		return colrate;
	}
	public void setColrate(int colrate) {
		this.colrate = colrate;
	}
	public int getReportrate() {
		return reportrate;
	}
	public void setReportrate(int reportrate) {
		this.reportrate = reportrate;
	}
	public int getStaytime() {
		return staytime;
	}
	public void setStaytime(int staytime) {
		this.staytime = staytime;
	}
	public Date getCreatAt() {
		return creatAt;
	}
	public void setCreatAt(Date creatAt) {
		this.creatAt = creatAt;
	}
	
	


}
