package com.czz.hwy.bean.manager.app;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
  * 通知订阅实体类，用于app接口
  * @author 张咏雪，2016-12-20
  * @version V0.1
  */
public class SubTopicsApp extends PageReqParamDTO  implements Serializable {

    private static final long serialVersionUID = -7150527942134490628L;

    // ID
    private int id;
    // 通知类型
    private int msgType;
    // 手机标识
    private String deviceId;
    // 接受者
    private int employeeId;
    // 接受者姓名
    private String employeeName;
    // 主题
    private String topics;
    // 状态1：订阅2：不订阅
    private int status;
    // 创建时间
    private Date createAt;
    // 修改时间
    private Date updateAt;
    //时间段 起始
    private Date t_createAt;
    //时间段 截至
    private Date f_createAt;
    
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
	  * 设定通知类型
	  * @param msgType
      */
	public void setMsgType(int msgType) {
	   this.msgType = msgType;
	}
	/**
	  * 获取通知类型
	  * @return 通知类型
      */
	public int getMsgType() {
	   return msgType;
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
	/**
	  * 设定接受者
	  * @param employeeId
      */
	public void setEmployeeId(int employeeId) {
	   this.employeeId = employeeId;
	}
	/**
	  * 获取接受者
	  * @return 接受者
      */
	public int getEmployeeId() {
	   return employeeId;
	}
	/**
	  * 设定主题
	  * @param topics
      */
	public void setTopics(String topics) {
	   this.topics = topics;
	}
	/**
	  * 获取主题
	  * @return 主题
      */
	public String getTopics() {
	   return topics;
	}
	/**
	  * 设定状态1：订阅2：不订阅
	  * @param status
      */
	public void setStatus(int status) {
	   this.status = status;
	}
	/**
	  * 获取状态1：订阅2：不订阅
	  * @return 状态1：订阅2：不订阅
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
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public Date getT_createAt() {
		return t_createAt;
	}
	public void setT_createAt(Date t_createAt) {
		this.t_createAt = t_createAt;
	}
	public Date getF_createAt() {
		return f_createAt;
	}
	public void setF_createAt(Date f_createAt) {
		this.f_createAt = f_createAt;
	}
}