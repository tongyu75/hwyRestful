package com.czz.hwy.bean.mission.app;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
  * 五克检测实体类，用于app接口
  * @author 张咏雪，2016-12-20
  * @version V0.1
  */
public class CheckGramApp extends PageReqParamDTO  implements Serializable {

    private static final long serialVersionUID = -932729141516828849L;

    // ID
    private int id;
    // 考核类型ID
    private int evalType;
    // 检查者ID
    private int employeeId;
    // 检查地址
    private String checkAddress;
    // 检查时间
    private Date checkTime;
    // 检查纬度
    private double checkLat;
    // 检查经度
    private double checkLng;
    // 检查值
    private double checkValue;
    // 发布时间
    private Date publishTime;
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
    
    //检测员人名称
    private String employeeName;
    
    
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
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
	  * 设定考核类型ID
	  * @param evalType
      */
	public void setEvalType(int evalType) {
	   this.evalType = evalType;
	}
	/**
	  * 获取考核类型ID
	  * @return 考核类型ID
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
	  * 设定检查值
	  * @param checkValue
      */
	public void setCheckValue(double checkValue) {
	   this.checkValue = checkValue;
	}
	/**
	  * 获取检查值
	  * @return 检查值
      */
	public double getCheckValue() {
	   return checkValue;
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