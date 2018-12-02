package com.czz.hwy.bean.mission;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
  * 环卫云项目启用
  * @author 李宇宁 16-七月-2015
  * @version V0.1
  */
public class ReportTypes  extends PageReqParamDTO implements Serializable {

    private static final long serialVersionUID = 6548989909880472519L;

    // ID
    private int id;
    // 举报id
    private int supervisorId;
    // 举报名字
    private String supervisorName;
    // 描述
    private String supervisorDesc;
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
	  * 设定举报id
	  * @param supervisorId
      */
	public void setSupervisorId(int supervisorId) {
	   this.supervisorId = supervisorId;
	}
	/**
	  * 获取举报id
	  * @return 举报id
      */
	public int getSupervisorId() {
	   return supervisorId;
	}
	/**
	  * 设定举报名字
	  * @param supervisorName
      */
	public void setSupervisorName(String supervisorName) {
	   this.supervisorName = supervisorName;
	}
	/**
	  * 获取举报名字
	  * @return 举报名字
      */
	public String getSupervisorName() {
	   return supervisorName;
	}
	/**
	  * 设定描述
	  * @param supervisorDesc
      */
	public void setSupervisorDesc(String supervisorDesc) {
	   this.supervisorDesc = supervisorDesc;
	}
	/**
	  * 获取描述
	  * @return 描述
      */
	public String getSupervisorDesc() {
	   return supervisorDesc;
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