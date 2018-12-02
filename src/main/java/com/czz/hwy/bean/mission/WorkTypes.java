package com.czz.hwy.bean.mission;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
  * 环卫云项目启用
  * @author 李宇宁 16-七月-2015
  * @version V0.1
  */
public class WorkTypes  extends PageReqParamDTO implements Serializable {

    private static final long serialVersionUID = 3401283527671041654L;

    // ID
    private int id;
    // 1：机械2：人力
    private int workType;
    // 任务名字
    private String workName;
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
	  * 设定1：机械2：人力
	  * @param workType
      */
	public void setWorkType(int workType) {
	   this.workType = workType;
	}
	/**
	  * 获取1：机械2：人力
	  * @return 1：机械2：人力
      */
	public int getWorkType() {
	   return workType;
	}
	/**
	  * 设定任务名字
	  * @param workName
      */
	public void setWorkName(String workName) {
	   this.workName = workName;
	}
	/**
	  * 获取任务名字
	  * @return 任务名字
      */
	public String getWorkName() {
	   return workName;
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