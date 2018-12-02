package com.czz.hwy.bean.area;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

public class AreaManagerFile extends PageReqParamDTO<AreaManagerFile> implements Serializable {

	private static final long serialVersionUID = -2803277287795471163L;
	private int id;
	private String areaName;
	private String areaAddress;
	private String areaDesc;
	private int createId;
	private int updateId;
	private Date createAt;
	private Date updateAt;
	private int status;
	private String statusStr;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getAreaAddress() {
		return areaAddress;
	}
	public void setAreaAddress(String areaAddress) {
		this.areaAddress = areaAddress;
	}
	public String getAreaDesc() {
		return areaDesc;
	}
	public void setAreaDesc(String areaDesc) {
		this.areaDesc = areaDesc;
	}
	public int getCreateId() {
		return createId;
	}
	public void setCreateId(int createId) {
		this.createId = createId;
	}
	public int getUpdateId() {
		return updateId;
	}
	public void setUpdateId(int updateId) {
		this.updateId = updateId;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getStatusStr() {
		return statusStr;
	}
	public void setStatusStr() {
		if(this.status==1)this.statusStr="启用";
		else this.statusStr="废止";	
	}
}
