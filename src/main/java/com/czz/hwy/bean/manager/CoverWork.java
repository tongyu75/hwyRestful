package com.czz.hwy.bean.manager;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
 * 代班Bean
 * @Author 张咏雪
 * @createDate 2016-11-16
 */
public class CoverWork extends PageReqParamDTO implements Serializable{

    private static final long serialVersionUID = 6642443764467236464L;

    // ID
	private int id;
	
	//2016-10-19
	private int leaveInfoId;//对应的请假信息ID

	// 请假人ID
    private int leaveId;

	// 请假人名称 
    private String leaveName;

    // 请假人角色
    private int leaveRole;
    
    // 代班开始时间
    private Date coverFromTime;

    // 代班结束时间
    private Date coverToTime;

    // 应出勤责任区ID
    private int dutyAreaId;

    // 应出勤责任区ID
    private int dutyPointId;

    // 代班人ID
    private int coverId;

    // 代班人名称
    private String coverName;

    // 代班人角色
    private int coverRole;
    
    // 代班状态
    private int coverStatus;//1 已代班 2 未代班
    
    // 检索结果请假截止时间  
    private String toTime;

    // 检索结果请假提交时间    
    private String fromTime;
    
    // 创建时间
    private Date createAt;

    // 修改时间
    private Date updateAt;        
    
    // 责任区名字
    private String areaName;

    // 责任点名字
    private String pointName;
    
    // 请假天数
    private int leaveNumber;
    


	public int getLeaveInfoId() {
		return leaveInfoId;
	}

	public void setLeaveInfoId(int leaveInfoId) {
		this.leaveInfoId = leaveInfoId;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(int leaveId) {
        this.leaveId = leaveId;
    }

    public String getLeaveName() {
        return leaveName;
    }

    public void setLeaveName(String leaveName) {
        this.leaveName = leaveName;
    }

    public Date getCoverFromTime() {
        return coverFromTime;
    }

    public void setCoverFromTime(Date coverFromTime) {
        this.coverFromTime = coverFromTime;
    }

    public Date getCoverToTime() {
        return coverToTime;
    }

    public void setCoverToTime(Date coverToTime) {
        this.coverToTime = coverToTime;
    }

    public int getDutyAreaId() {
        return dutyAreaId;
    }

    public void setDutyAreaId(int dutyAreaId) {
        this.dutyAreaId = dutyAreaId;
    }

    public int getDutyPointId() {
        return dutyPointId;
    }

    public void setDutyPointId(int dutyPointId) {
        this.dutyPointId = dutyPointId;
    }

    public int getCoverId() {
        return coverId;
    }

    public void setCoverId(int coverId) {
        this.coverId = coverId;
    }

    public String getCoverName() {
        return coverName;
    }

    public void setCoverName(String coverName) {
        this.coverName = coverName;
    }

    public int getCoverStatus() {
        return coverStatus;
    }

    public void setCoverStatus(int coverStatus) {
        this.coverStatus = coverStatus;
    }
    
    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
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

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public int getLeaveRole() {
        return leaveRole;
    }

    public void setLeaveRole(int leaveRole) {
        this.leaveRole = leaveRole;
    }

    public int getCoverRole() {
        return coverRole;
    }

    public void setCoverRole(int coverRole) {
        this.coverRole = coverRole;
    }

    public int getLeaveNumber() {
        return leaveNumber;
    }

    public void setLeaveNumber(int leaveNumber) {
        this.leaveNumber = leaveNumber;
    }          
    
}