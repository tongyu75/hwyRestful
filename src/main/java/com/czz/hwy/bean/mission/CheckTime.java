package com.czz.hwy.bean.mission;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
  * 环卫云项目启用
  * @author 李宇宁 16-七月-2015
  * @version V0.1
  */
public class CheckTime extends PageReqParamDTO implements Serializable {

    private static final long serialVersionUID = -2182588850631651803L;

    // ID
    private int id;
    // 考核类型ID
    private int evalType;
    // 检查者ID
    private int employeeId;
    // 举报ID
    private int supervisorId;
    // 检查地址
    private String checkAddress;
    // 检查时间
    private Date checkTime;
    // 发布时间
    private Date publishTime;
    // 检查纬度
    private double checkLat;
    // 检查经度
    private double checkLng;
    // 考核状态1：合格2：不合格
    private int checkStatus;
    
    // 责任区ID
    private int areaId;
    // 责任点ID
    private int pointId;
    // 开始时间
    private String startTimeStr;
    // 结束时间
    private String endTimeStr;
    // 责任人
    private String dutyPeopleName;
	/**
	  * 设定ID
	  * @param id
      */
	public void setId(int id) {
	   this.id = id;
	}
	/**
	  * 获取ID
	  * @return ID
      */
	public int getId() {
	   return id;
	}
	/**
	  * 设定考核类型Type
	  * @param evalType
      */
	public void setEvalType(int evalType) {
	   this.evalType = evalType;
	}
	/**
	  * 获取考核类型Type
	  * @return 考核类型Type
      */
	public int getEvalType() {
	   return evalType;
	}
	/**
	  * 设定检查者ID
	  * @param employeeId
      */
	public void setEmployeeId(int employeeId) {
	   this.employeeId = employeeId;
	}
	/**
	  * 获取检查者ID
	  * @return 检查者ID
      */
	public int getEmployeeId() {
	   return employeeId;
	}
	/**
	  * 设定举报ID
	  * @param supervisorId
      */
	public void setSupervisorId(int supervisorId) {
	   this.supervisorId = supervisorId;
	}
	/**
	  * 获取举报ID
	  * @return 举报ID
      */
	public int getSupervisorId() {
	   return supervisorId;
	}
	/**
	  * 设定检查地址
	  * @param checkAddress
      */
	public void setCheckAddress(String checkAddress) {
	   this.checkAddress = checkAddress;
	}
	/**
	  * 获取检查地址
	  * @return 检查地址
      */
	public String getCheckAddress() {
	   return checkAddress;
	}
	/**
	  * 设定检查时间
	  * @param checkTime
      */
	public void setCheckTime(Date checkTime) {
	   this.checkTime = checkTime;
	}
	/**
	  * 获取检查时间
	  * @return 检查时间
      */
	public Date getCheckTime() {
	   return checkTime;
	}
	/**
	  * 设定发布时间
	  * @param publishTime
      */
	public void setPublishTime(Date publishTime) {
	   this.publishTime = publishTime;
	}
	/**
	  * 获取发布时间
	  * @return 发布时间
      */
	public Date getPublishTime() {
	   return publishTime;
	}
	/**
	  * 设定检查纬度
	  * @param checkLat
      */
	public void setCheckLat(double checkLat) {
	   this.checkLat = checkLat;
	}
	/**
	  * 获取检查纬度
	  * @return 检查纬度
      */
	public double getCheckLat() {
	   return checkLat;
	}
	/**
	  * 设定检查经度
	  * @param checkLng
      */
	public void setCheckLng(double checkLng) {
	   this.checkLng = checkLng;
	}
	/**
	  * 获取检查经度
	  * @return 检查经度
      */
	public double getCheckLng() {
	   return checkLng;
	}
	/**
	  * 设定考核状态1：合格2：不合格
	  * @param checkStatus
      */
	public void setCheckStatus(int checkStatus) {
	   this.checkStatus = checkStatus;
	}
	/**
	  * 获取考核状态1：合格2：不合格
	  * @return 考核状态1：合格2：不合格
      */
	public int getCheckStatus() {
	   return checkStatus;
	}
    public int getAreaId() {
        return areaId;
    }
    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }
    public int getPointId() {
        return pointId;
    }
    public void setPointId(int pointId) {
        this.pointId = pointId;
    }
    public String getStartTimeStr() {
        return startTimeStr;
    }
    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }
    public String getEndTimeStr() {
        return endTimeStr;
    }
    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }
    public String getDutyPeopleName() {
        return dutyPeopleName;
    }
    public void setDutyPeopleName(String dutyPeopleName) {
        this.dutyPeopleName = dutyPeopleName;
    }
}