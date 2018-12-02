package com.czz.hwy.bean.user;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
  * 环卫云项目启用
  * @author 李宇宁 16-七月-2015
  * @version V0.1
  */
public class Departments extends PageReqParamDTO<Departments> implements Serializable {

    private static final long serialVersionUID = 7964718848448421561L;

    // ID
    private int id;
    // 部门ID
    private int deptId;
    // 部门名字
    private String deptName;
    // 部门描述
    private String deptDesc;
    // 创建时间
    private Date createAt;
    // 修改时间
    private Date updateAt;
    
    private int status;
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
	  * 设定部门ID
	  * @param deptId
      */
	public void setDeptId(int deptId) {
	   this.deptId = deptId;
	}
	/**
	  * 获取部门ID
	  * @return 部门ID
      */
	public int getDeptId() {
	   return deptId;
	}
	/**
	  * 设定部门名字
	  * @param deptName
      */
	public void setDeptName(String deptName) {
	   this.deptName = deptName;
	}
	/**
	  * 获取部门名字
	  * @return 部门名字
      */
	public String getDeptName() {
	   return deptName;
	}
	/**
	  * 设定部门描述
	  * @param deptDesc
      */
	public void setDeptDesc(String deptDesc) {
	   this.deptDesc = deptDesc;
	}
	/**
	  * 获取部门描述
	  * @return 部门描述
      */
	public String getDeptDesc() {
	   return deptDesc;
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
	private Date beginDate;
	private Date endDate;
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}