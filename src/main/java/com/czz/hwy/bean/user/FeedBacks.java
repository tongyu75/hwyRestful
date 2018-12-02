package com.czz.hwy.bean.user;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
  * 环卫云项目启用
  * @author 李宇宁 16-七月-2015
  * @version V0.1
  */
public class FeedBacks extends PageReqParamDTO implements Serializable {

    private static final long serialVersionUID = 939814584951822803L;

    // ID
    private int id;
    // 评价信息
    private String content;
    // 用户ID
    private int employeeId;
    // app版本值
    private int versionCode;
    // 创建时间
    private Date createAt;
    // 修改时间
    private Date updateAt;
    // 手机标识
    private String deviceId;
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
	  * 设定评价信息
	  * @param content
      */
	public void setContent(String content) {
	   this.content = content;
	}
	/**
	  * 获取评价信息
	  * @return 评价信息
      */
	public String getContent() {
	   return content;
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
	  * 设定app版本值
	  * @param versionCode
      */
	public void setVersionCode(int versionCode) {
	   this.versionCode = versionCode;
	}
	/**
	  * 获取app版本值
	  * @return app版本值
      */
	public int getVersionCode() {
	   return versionCode;
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
	  * 设定手机标识
	  * @param deviceId
      */
	public void setDeviceId(String deviceId) {
	   this.deviceId = deviceId;
	}
	/**
	  * 获取手机标识
	  * @return 手机标识
      */
	public String getDeviceId() {
	   return deviceId;
	}
}