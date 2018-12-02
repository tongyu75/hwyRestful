package com.czz.hwy.bean.mission;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
 * 班次的bean对象
 * 功能描述
 * @author 张咏雪
 * @createDate 2016-09-28
 */
public class Banci extends PageReqParamDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int id;//主键id
	
	private int dutyNumber;//班次的序号
	
	private Date dutyOntime;//计划上班时间
	
	private Date dutyOfftime;//计划下班时间
	
	private int dutyRoles;//班次所属的人员角色
	
	private int status;//班次的状态 1 表示有效 2 表示无效班次
	
	private int banzuId;//班组ID
	
	private int createUserId;//创建人Id
	
	private Date createAt;//创建时间
	
	private int updateUserId;//更新人Id
	
	private Date updateAt;//更新时间
	
	private String banzuName;//班组名称
	

	public String getBanzuName() {
		return banzuName;
	}

	public void setBanzuName(String banzuName) {
		this.banzuName = banzuName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDutyNumber() {
		return dutyNumber;
	}

	public void setDutyNumber(int dutyNumber) {
		this.dutyNumber = dutyNumber;
	}

	public Date getDutyOntime() {
		return dutyOntime;
	}

	public void setDutyOntime(Date dutyOntime) {
		this.dutyOntime = dutyOntime;
	}

	public Date getDutyOfftime() {
		return dutyOfftime;
	}

	public void setDutyOfftime(Date dutyOfftime) {
		this.dutyOfftime = dutyOfftime;
	}

	public int getDutyRoles() {
		return dutyRoles;
	}

	public void setDutyRoles(int dutyRoles) {
		this.dutyRoles = dutyRoles;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getBanzuId() {
		return banzuId;
	}

	public void setBanzuId(int banzuId) {
		this.banzuId = banzuId;
	}

	public int getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public int getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(int updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

}
