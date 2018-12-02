package com.czz.hwy.bean.manager;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
  * 环卫云项目启用
  * @author 李宇宁 16-七月-2015
  * @version V0.1
  */
public class StandardFines extends PageReqParamDTO implements Serializable {

    private static final long serialVersionUID = 4402603932104291595L;

    // ID
    private int id;
    // 角色ID
    private int rolesId;
    // 考核类型ID
    private int evalId;
    // 责任区域ID
    private int areaId;
    // 罚款金额
    private double amount;
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
	  * 设定角色ID
	  * @param rolesId
      */
	public void setRolesId(int rolesId) {
	   this.rolesId = rolesId;
	}
	/**
	  * 获取角色ID
	  * @return 角色ID
      */
	public int getRolesId() {
	   return rolesId;
	}
	/**
	  * 设定考核类型ID
	  * @param evalId
      */
	public void setEvalId(int evalId) {
	   this.evalId = evalId;
	}
	/**
	  * 获取考核类型ID
	  * @return 考核类型ID
      */
	public int getEvalId() {
	   return evalId;
	}
	/**
	  * 设定责任区域ID
	  * @param areaId
      */
	public void setAreaId(int areaId) {
	   this.areaId = areaId;
	}
	/**
	  * 获取责任区域ID
	  * @return 责任区域ID
      */
	public int getAreaId() {
	   return areaId;
	}
	/**
	  * 设定罚款金额
	  * @param amount
      */
	public void setAmount(double amount) {
	   this.amount = amount;
	}
	/**
	  * 获取罚款金额
	  * @return 罚款金额
      */
	public double getAmount() {
	   return amount;
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