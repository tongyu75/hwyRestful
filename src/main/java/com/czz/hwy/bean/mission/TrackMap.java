package com.czz.hwy.bean.mission;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
  * 环卫云项目启用
  * @author 李宇宁 16-七月-2015
  * @version V0.1
  */
public class TrackMap extends PageReqParamDTO implements Serializable {

    private static final long serialVersionUID = -6526818182438991473L;

    // ID
    private int id;
    // 用户ID
    private int userId;
    // 轨迹跟踪点地址
    private String trackAddress;
    // 轨迹跟踪点纬度
    private double trackLat;
    // 轨迹跟踪点经度
    private double trackLng;
    // 轨迹跟踪开始时间
    private Date startTime;
    // 轨迹跟踪点结束时间
    private Date stopTime;
    // 停留时间
    private double count;
    // 责任点ID
    private int pointId;
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
	  * @param userId
      */
	public void setUserId(int userId) {
	   this.userId = userId;
	}
	/**
	  * 获取用户ID
	  * @return 用户ID
      */
	public int getUserId() {
	   return userId;
	}
	/**
	  * 设定轨迹跟踪点地址
	  * @param trackAddress
      */
	public void setTrackAddress(String trackAddress) {
	   this.trackAddress = trackAddress;
	}
	/**
	  * 获取轨迹跟踪点地址
	  * @return 轨迹跟踪点地址
      */
	public String getTrackAddress() {
	   return trackAddress;
	}
	/**
	  * 设定轨迹跟踪点纬度
	  * @param trackLat
      */
	public void setTrackLat(double trackLat) {
	   this.trackLat = trackLat;
	}
	/**
	  * 获取轨迹跟踪点纬度
	  * @return 轨迹跟踪点纬度
      */
	public double getTrackLat() {
	   return trackLat;
	}
	/**
	  * 设定轨迹跟踪点经度
	  * @param trackLng
      */
	public void setTrackLng(double trackLng) {
	   this.trackLng = trackLng;
	}
	/**
	  * 获取轨迹跟踪点经度
	  * @return 轨迹跟踪点经度
      */
	public double getTrackLng() {
	   return trackLng;
	}
	/**
	  * 设定轨迹跟踪开始时间
	  * @param startTime
      */
	public void setStartTime(Date startTime) {
	   this.startTime = startTime;
	}
	/**
	  * 获取轨迹跟踪开始时间
	  * @return 轨迹跟踪开始时间
      */
	public Date getStartTime() {
	   return startTime;
	}
	/**
	  * 设定轨迹跟踪点结束时间
	  * @param stopTime
      */
	public void setStopTime(Date stopTime) {
	   this.stopTime = stopTime;
	}
	/**
	  * 获取轨迹跟踪点结束时间
	  * @return 轨迹跟踪点结束时间
      */
	public Date getStopTime() {
	   return stopTime;
	}
	/**
	  * 设定停留时间
	  * @param count
      */
	public void setCount(double count) {
	   this.count = count;
	}
	/**
	  * 获取停留时间
	  * @return 停留时间
      */
	public double getCount() {
	   return count;
	}
	/**
	  * 设定责任点ID
	  * @param pointId
      */
	public void setPointId(int pointId) {
	   this.pointId = pointId;
	}
	/**
	  * 获取责任点ID
	  * @return 责任点ID
      */
	public int getPointId() {
	   return pointId;
	}
}