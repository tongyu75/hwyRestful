package com.czz.hwy.bean.user;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
  * 环卫云项目启用
  * @author 李宇宁 16-七月-2015
  * @version V0.1
  */
public class Avatars extends PageReqParamDTO implements Serializable {

    private static final long serialVersionUID = 496828809626370632L;

    // ID
    private int id;
    // 用户ID
    private int employeeId;
    // 头像名字
    private String iconFileName;
    // 头像类型
    private String iconContentType;
    // 头像大小
    private int iconFileSize;
    // 文件流信息
    private String iconContent;
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
	  * 设定头像名字
	  * @param iconFileName
      */
	public void setIconFileName(String iconFileName) {
	   this.iconFileName = iconFileName;
	}
	/**
	  * 获取头像名字
	  * @return 头像名字
      */
	public String getIconFileName() {
	   return iconFileName;
	}
	/**
	  * 设定头像类型
	  * @param iconContentType
      */
	public void setIconContentType(String iconContentType) {
	   this.iconContentType = iconContentType;
	}
	/**
	  * 获取头像类型
	  * @return 头像类型
      */
	public String getIconContentType() {
	   return iconContentType;
	}
	/**
	  * 设定头像大小
	  * @param iconFileSize
      */
	public void setIconFileSize(int iconFileSize) {
	   this.iconFileSize = iconFileSize;
	}
	/**
	  * 获取头像大小
	  * @return 头像大小
      */
	public int getIconFileSize() {
	   return iconFileSize;
	}
	/**
	  * 设定文件流信息
	  * @param iconContent
      */
	public void setIconContent(String iconContent) {
	   this.iconContent = iconContent;
	}
	/**
	  * 获取文件流信息
	  * @return 文件流信息
      */
	public String getIconContent() {
	   return iconContent;
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