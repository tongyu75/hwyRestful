package com.czz.hwy.bean.user;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
  * 环卫云项目启用
  * @author 李宇宁 16-七月-2015
  * @version V0.1
  */
public class Roles extends PageReqParamDTO implements Serializable {

    private static final long serialVersionUID = 3942417245783258449L;

    // ID
    private int id;
    // 角色应用权限
    private int value;
    // 角色名字
    private String name;
    // 备注
    private String remark ;
    // 创建时间
    private Date createAt;
    // 修改时间
    private Date updateAt;
    
    private int status;
    
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
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
	  * 设定角色应用权限
	  * @param value
      */
	public void setValue(int value) {
	   this.value = value;
	}
	/**
	  * 获取角色应用权限
	  * @return 角色应用权限
      */
	public int getValue() {
	   return value;
	}
	/**
	  * 设定角色名字
	  * @param name
      */
	public void setName(String name) {
	   this.name = name;
	}
	/**
	  * 获取角色名字
	  * @return 角色名字
      */
	public String getName() {
	   return name;
	}
	/**
	  * 设定备注
	  * @param remark 
      */
	public void setRemark (String remark ) {
	   this.remark  = remark ;
	}
	/**
	  * 获取备注
	  * @return 备注
      */
	public String getRemark () {
	   return remark ;
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