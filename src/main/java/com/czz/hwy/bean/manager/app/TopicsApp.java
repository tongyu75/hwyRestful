package com.czz.hwy.bean.manager.app;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
  * 环卫云项目启用
  * @author 李宇宁 16-七月-2015
  * @version V0.1
  */
public class TopicsApp extends PageReqParamDTO  implements Serializable {

    private static final long serialVersionUID = 6965960167032579454L;

    // ID
    private int id;
    // 标题
    private String title;
    // 内容
    private String content;
    // 发布时间
    private Date publishTime;
    //消息发布起始时间
  	private Date f_publishTime;
  	//消息发布截至时间
  	private Date t_publishTime;
    // 创建时间
    private Date createAt;
    // 修改时间
    private Date updateAt;
    // 发布者ID
    private int employeeId;
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
	  * 设定标题
	  * @param title
      */
	public void setTitle(String title) {
	   this.title = title;
	}
	/**
	  * 获取标题
	  * @return 标题
      */
	public String getTitle() {
	   return title;
	}
	/**
	  * 设定内容
	  * @param content
      */
	public void setContent(String content) {
	   this.content = content;
	}
	/**
	  * 获取内容
	  * @return 内容
      */
	public String getContent() {
	   return content;
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
	  * 设定发布者ID
	  * @param employeeId
      */
	public void setEmployeeId(int employeeId) {
	   this.employeeId = employeeId;
	}
	/**
	  * 获取发布者ID
	  * @return 发布者ID
      */
	public int getEmployeeId() {
	   return employeeId;
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
}