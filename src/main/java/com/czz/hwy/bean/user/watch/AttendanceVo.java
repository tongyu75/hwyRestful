package com.czz.hwy.bean.user.watch;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
  * 环卫云项目启用
  * @author 李宇宁 16-七月-2015
  * @version V0.1
  */
public class AttendanceVo extends PageReqParamDTO  implements Serializable {

    private static final long serialVersionUID = 710801094562647491L;

    // ID
    private int id;
    // 用户ID
    private int employeeId;
    // 考勤日期
    private Date attendanceDate;
    // 考勤记录时间
    private Date recordTime;
    // 考勤记录地址
    private String address;
    // 考勤记录纬度
    private String lat;
    // 考勤记录经度
    private String lng;
    // 创建时间
    private Date createAt;
    private String workStatus;//上下班状态， 1：上班  2：下班
    
    // 手动考勤选择的时间段
    private String timePlan;
    // 手动考勤选择的时间类型(1.上班 2.下班)
    private int timeFlag;
    
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
	  * 设定用户ID
	  * @param employeeId
      */
	public void setEmployeeId(int employeeId) {
	   this.employeeId = employeeId;
	}
	/**
	  * 获取用户ID
	  * @return 用户ID
      */
	public int getEmployeeId() {
	   return employeeId;
	}
	/**
	  * 设定考勤日期
	  * @param attendanceDate
      */
	public void setAttendanceDate(Date attendanceDate) {
	   this.attendanceDate = attendanceDate;
	}
	/**
	  * 获取考勤日期
	  * @return 考勤日期
      */
	public Date getAttendanceDate() {
	   return attendanceDate;
	}
	/**
	  * 设定考勤记录时间
	  * @param recordTime
      */
	public void setRecordTime(Date recordTime) {
	   this.recordTime = recordTime;
	}
	/**
	  * 获取考勤记录时间
	  * @return 考勤记录时间
      */
	public Date getRecordTime() {
	   return recordTime;
	}
	/**
	  * 设定考勤记录地址
	  * @param address
      */
	public void setAddress(String address) {
	   this.address = address;
	}
	/**
	  * 获取考勤记录地址
	  * @return 考勤记录地址
      */
	public String getAddress() {
	   return address;
	}
	/**
	  * 设定考勤记录纬度
	  * @param lat
      */
	public void setLat(String lat) {
	   this.lat = lat;
	}
	/**
	  * 获取考勤记录纬度
	  * @return 考勤记录纬度
      */
	public String getLat() {
	   return lat;
	}
	/**
	  * 设定考勤记录经度
	  * @param lng
      */
	public void setLng(String lng) {
	   this.lng = lng;
	}
	/**
	  * 获取考勤记录经度
	  * @return 考勤记录经度
      */
	public String getLng() {
	   return lng;
	}
	/**
	  * 设定创建时间
	  * @param createAt
      */
	public void setCreateAt(Date createAt) {
	   this.createAt = createAt;
	}
	/**
	  * 获取创建时间
	  * @return 创建时间
      */
	public Date getCreateAt() {
	   return createAt;
	}
	public String getWorkStatus() {
		return workStatus;
	}
	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}
    public String getTimePlan() {
        return timePlan;
    }
    public void setTimePlan(String timePlan) {
        this.timePlan = timePlan;
    }
    public int getTimeFlag() {
        return timeFlag;
    }
    public void setTimeFlag(int timeFlag) {
        this.timeFlag = timeFlag;
    }
}