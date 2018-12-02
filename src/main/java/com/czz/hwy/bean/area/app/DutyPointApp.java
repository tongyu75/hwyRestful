package com.czz.hwy.bean.area.app;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
  * 责任点实体类，用于app接口
  * @author 张咏雪，2016-11-02
  * @version V0.1
  */
public class DutyPointApp extends PageReqParamDTO  implements Serializable {

    private static final long serialVersionUID = 8113100210174931579L;

    // ID
    private int id;
    // 责任区ID
    private int areaId;
	// 责任点名字
    private String pointName;
    // 责任区名字
    private String areaName;
    // 责任点起点
    private String fromAddress;
    // 责任点终点
    private String toAddress;
    // 责任点起点纬度
    private double fromLat;
    // 责任点起点经度
    private double fromLng;
    // 责任点终点纬度
    private double toLat;
    // 责任点终点经度
    private double toLng;
    // 创建时间
    private Date createAt;
    // 修改时间
    private Date updateAt;
    // 创建者
    private int createId;
    // 创建者
    private  String createName;
    // 修改者
    private int updateId;
    // 状态（1:启用2：废止）
    private int status;
    // 责任点的子区域的文件保存路径
    private String pointFilepath;
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
	  * 设定责任区ID
	  * @param areaId
      */
	public void setAreaId(int areaId) {
	   this.areaId = areaId;
	}
	/**
	  * 获取责任区ID
	  * @return 责任区ID
      */
	public int getAreaId() {
	   return areaId;
	}
	/**
	  * 设定责任点名字
	  * @param pointName
      */
	public void setPointName(String pointName) {
	   this.pointName = pointName;
	}
	/**
	  * 获取责任点名字
	  * @return 责任点名字
      */
	public String getPointName() {
	   return pointName;
	}
	/**
	  * 设定责任点起点
	  * @param fromAddress
      */
	public void setFromAddress(String fromAddress) {
	   this.fromAddress = fromAddress;
	}
	/**
	  * 获取责任点起点
	  * @return 责任点起点
      */
	public String getFromAddress() {
	   return fromAddress;
	}
	/**
	  * 设定责任点终点
	  * @param toAddress
      */
	public void setToAddress(String toAddress) {
	   this.toAddress = toAddress;
	}
	/**
	  * 获取责任点终点
	  * @return 责任点终点
      */
	public String getToAddress() {
	   return toAddress;
	}
	/**
	  * 设定责任点起点纬度
	  * @param fromLat
      */
	public void setFromLat(double fromLat) {
	   this.fromLat = fromLat;
	}
	/**
	  * 获取责任点起点纬度
	  * @return 责任点起点纬度
      */
	public double getFromLat() {
	   return fromLat;
	}
	/**
	  * 设定责任点起点经度
	  * @param fromLng
      */
	public void setFromLng(double fromLng) {
	   this.fromLng = fromLng;
	}
	/**
	  * 获取责任点起点经度
	  * @return 责任点起点经度
      */
	public double getFromLng() {
	   return fromLng;
	}
	/**
	  * 设定责任点终点纬度
	  * @param toLat
      */
	public void setToLat(double toLat) {
	   this.toLat = toLat;
	}
	/**
	  * 获取责任点终点纬度
	  * @return 责任点终点纬度
      */
	public double getToLat() {
	   return toLat;
	}
	/**
	  * 设定责任点终点经度
	  * @param toLng
      */
	public void setToLng(double toLng) {
	   this.toLng = toLng;
	}
	/**
	  * 获取责任点终点经度
	  * @return 责任点终点经度
      */
	public double getToLng() {
	   return toLng;
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
	/**
	  * 设定修改时间
	  * @param updateAt
      */
	public void setUpdateAt(Date updateAt) {
	   this.updateAt = updateAt;
	}
	/**
	  * 获取修改时间
	  * @return 修改时间
      */
	public Date getUpdateAt() {
	   return updateAt;
	}
	/**
	  * 设定创建者
	  * @param createId
      */
	public void setCreateId(int createId) {
	   this.createId = createId;
	}
	/**
	  * 获取创建者
	  * @return 创建者
      */
	public int getCreateId() {
	   return createId;
	}
	/**
	  * 设定修改者
	  * @param updateId
      */
	public void setUpdateId(int updateId) {
	   this.updateId = updateId;
	}
	/**
	  * 获取修改者
	  * @return 修改者
      */
	public int getUpdateId() {
	   return updateId;
	}
	/**
	  * 设定状态（1:启用2：废止）
	  * @param status
      */
	public void setStatus(int status) {
	   this.status = status;
	}
	/**
	  * 获取状态（1:启用2：废止）
	  * @return 状态（1:启用2：废止）
      */
	public int getStatus() {
	   return status;
	}
    public String getAreaName() {
        return areaName;
    }
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
    public String getCreateName() {
        return createName;
    }
    public void setCreateName(String createName) {
        this.createName = createName;
    }
    public String getPointFilepath() {
        return pointFilepath;
    }
    public void setPointFilepath(String pointFilepath) {
        this.pointFilepath = pointFilepath;
    }
}