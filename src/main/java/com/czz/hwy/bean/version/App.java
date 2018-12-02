package com.czz.hwy.bean.version;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
  * 环卫云项目启用
  * @author 李宇宁 16-七月-2015
  * @version V0.1
  */
public class App extends PageReqParamDTO implements Serializable {

    private static final long serialVersionUID = -9124309464163257568L;

    // ID
    private int id;
    // 版本值
    private int versionCode;
    // 版本名字
    private String versionName;
    // app名字
    private String appName;
    // app类型
    private String appType;
    // 大小
    private double size;
    // 发布者
    private int employeeId;
    // 发布者名字
    private String employeeName;
    // 发布时间
    private Date publishTime;
	//发布起始时间
	private Date f_publishTime;
	//发布截至时间
	private Date t_publishTime;
    // 版本描述
    private String apkDesc;
    // 1：上架2：下架
    private int status;
    // 创建时间
    private Date createAt;
    // 修改时间
    private Date updateAt;
    // 下载量统计
    private int downloadCensus;
    //APP功能 1 代表是客户端APP  2代表是采集APP
    private int appFunction;
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
	  * 设定版本值
	  * @param versionCode
      */
	public void setVersionCode(int versionCode) {
	   this.versionCode = versionCode;
	}
	/**
	  * 获取版本值
	  * @return 版本值
      */
	public int getVersionCode() {
	   return versionCode;
	}
	/**
	  * 设定版本名字
	  * @param versionName
      */
	public void setVersionName(String versionName) {
	   this.versionName = versionName;
	}
	/**
	  * 获取版本名字
	  * @return 版本名字
      */
	public String getVersionName() {
	   return versionName;
	}
	/**
	  * 设定app名字
	  * @param appName
      */
	public void setAppName(String appName) {
	   this.appName = appName;
	}
	/**
	  * 获取app名字
	  * @return app名字
      */
	public String getAppName() {
	   return appName;
	}
	/**
	  * 设定app类型
	  * @param appType
      */
	public void setAppType(String appType) {
	   this.appType = appType;
	}
	/**
	  * 获取app类型
	  * @return app类型
      */
	public String getAppType() {
	   return appType;
	}
	/**
	  * 设定大小
	  * @param size
      */
	public void setSize(double size) {
	   this.size = size;
	}
	/**
	  * 获取大小
	  * @return 大小
      */
	public double getSize() {
	   return size;
	}
	/**
	  * 设定发布者
	  * @param employeeId
      */
	public void setEmployeeId(int employeeId) {
	   this.employeeId = employeeId;
	}
	/**
	  * 获取发布者
	  * @return 发布者
      */
	public int getEmployeeId() {
	   return employeeId;
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
	  * 设定版本描述
	  * @param apkDesc
      */
	public void setApkDesc(String apkDesc) {
	   this.apkDesc = apkDesc;
	}
	/**
	  * 获取版本描述
	  * @return 版本描述
      */
	public String getApkDesc() {
	   return apkDesc;
	}
	/**
	  * 设定1：上架2：下架
	  * @param status
      */
	public void setStatus(int status) {
	   this.status = status;
	}
	/**
	  * 获取1：上架2：下架
	  * @return 1：上架2：下架
      */
	public int getStatus() {
	   return status;
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
	public int getDownloadCensus() {
		return downloadCensus;
	}
	public void setDownloadCensus(int downloadCensus) {
		this.downloadCensus = downloadCensus;
	}
	public Date getF_publishTime() {
		return f_publishTime;
	}
	public void setF_publishTime(Date f_publishTime) {
		this.f_publishTime = f_publishTime;
	}
	public Date getT_publishTime() {
		return t_publishTime;
	}
	public void setT_publishTime(Date t_publishTime) {
		this.t_publishTime = t_publishTime;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public int getAppFunction() {
		return appFunction;
	}
	public void setAppFunction(int appFunction) {
		this.appFunction = appFunction;
	}
	
}