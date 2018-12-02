package com.czz.hwy.bean.user.watch;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
  * 环卫云项目启用
  * @author 李宇宁 16-七月-2015
  * @version V0.1
  */
public class Attendances extends PageReqParamDTO  implements Serializable {

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
    private double lat;
    // 考勤记录经度
    private double lng;
    // 创建时间
    private Date createAt;
    //是否检测过上下班状态
    private int ischeckattend;
    //是否检测过漏扫、漏检，怠工状态
    private int isvalidate;
    //当前位置是否在责任点范围内：0否1是
    private int atPoint;
    // 当前是否是手动考勤：0否1是
    private int isExcetion;
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
	public void setLat(double lat) {
	   this.lat = lat;
	}
	/**
	  * 获取考勤记录纬度
	  * @return 考勤记录纬度
      */
	public double getLat() {
	   return lat;
	}
	/**
	  * 设定考勤记录经度
	  * @param lng
      */
	public void setLng(double lng) {
	   this.lng = lng;
	}
	/**
	  * 获取考勤记录经度
	  * @return 考勤记录经度
      */
	public double getLng() {
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
	public int getIscheckattend() {
		return ischeckattend;
	}
	public void setIscheckattend(int ischeckattend) {
		this.ischeckattend = ischeckattend;
	}
	public int getIsvalidate() {
		return isvalidate;
	}
	public void setIsvalidate(int isvalidate) {
		this.isvalidate = isvalidate;
	}
    public int getAtPoint() {
        return atPoint;
    }
    public void setAtPoint(int atPoint) {
        this.atPoint = atPoint;
    }
    public int getIsExcetion() {
        return isExcetion;
    }
    public void setIsExcetion(int isExcetion) {
        this.isExcetion = isExcetion;
    }
    
}