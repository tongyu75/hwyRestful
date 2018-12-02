package com.czz.hwy.bean.manager;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
  * 环卫云项目启用
  * @author 李宇宁 16-七月-2015
  * @version V0.1
  */
public class ApperrLogs extends PageReqParamDTO  implements Serializable {

    private static final long serialVersionUID = -1933116628617216898L;

    // ID
    private int id;
    // 手机标识
    private String deviceId;
    // 用户ID
    private int employeeId;
    // 日志内容
    private String content;
    // 状态
    private String status;
    // 备注
    private String remark;
    // 创建时间
    private Date createAt;
    // 修改时间
    private Date updateAt;
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
	  * 设定日志内容
	  * @param content
      */
	public void setContent(String content) {
	   this.content = content;
	}
	/**
	  * 获取日志内容
	  * @return 日志内容
      */
	public String getContent() {
	   return content;
	}
	/**
	  * 设定状态
	  * @param status
      */
	public void setStatus(String status) {
	   this.status = status;
	}
	/**
	  * 获取状态
	  * @return 状态
      */
	public String getStatus() {
	   return status;
	}
	/**
	  * 设定备注
	  * @param remark
      */
	public void setRemark(String remark) {
	   this.remark = remark;
	}
	/**
	  * 获取备注
	  * @return 备注
      */
	public String getRemark() {
	   return remark;
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
}