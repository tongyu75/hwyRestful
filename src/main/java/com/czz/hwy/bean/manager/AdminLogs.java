package com.czz.hwy.bean.manager;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
  * 环卫云项目启用
  * @author 李宇宁 16-七月-2015
  * @version V0.1
  */
public class AdminLogs extends PageReqParamDTO implements Serializable {

    private static final long serialVersionUID = -815841541341918444L;

    // ID
    private int id;
    // 日志内容
    private String content;
    // 用户ID
    private int employeeId;
    // 用户姓名
    private String employeeName;
    // 操作状态
    private String status;
    // 创建时间
    private Date createAt;
    // 修改时间
    private Date updateAt;
    //起始时间
    private Date f_createAt;
    //截至时间
    private Date t_createAt;
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
	  * 设定操作状态
	  * @param status
      */
	public void setStatus(String status) {
	   this.status = status;
	}
	/**
	  * 获取操作状态
	  * @return 操作状态
      */
	public String getStatus() {
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
	public Date getF_createAt() {
		return f_createAt;
	}
	public void setF_createAt(Date f_createAt) {
		this.f_createAt = f_createAt;
	}
	public Date getT_createAt() {
		return t_createAt;
	}
	public void setT_createAt(Date t_createAt) {
		this.t_createAt = t_createAt;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	
}