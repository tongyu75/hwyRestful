package com.czz.hwy.bean.area.app;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
  * 责任区实体类，用于app接口
  * @author 张咏雪
  */
public class DutyAreaApp extends PageReqParamDTO  implements Serializable {

    private static final long serialVersionUID = 8840582696320557529L;

    // ID
    private int id;
    // 责任区名字
    private String areaName;
    // 责任区地址
    private String areaAddress;
    // 责任区描述
    private String areaDesc;
    // 创建时间
    private Date createAt;
    // 修改时间
    private Date updateAt;
    // 创建者ID
    private int createId;
    // 修改者ID
    private int updateId;
    // 状态（1:启用2：废止）
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
	  * 设定责任区名字
	  * @param areaName
      */
	public void setAreaName(String areaName) {
	   this.areaName = areaName;
	}
	/**
	  * 获取责任区名字
	  * @return 责任区名字
      */
	public String getAreaName() {
	   return areaName;
	}
	/**
	  * 设定责任区地址
	  * @param areaAddress
      */
	public void setAreaAddress(String areaAddress) {
	   this.areaAddress = areaAddress;
	}
	/**
	  * 获取责任区地址
	  * @return 责任区地址
      */
	public String getAreaAddress() {
	   return areaAddress;
	}
	/**
	  * 设定责任区描述
	  * @param areaDesc
      */
	public void setAreaDesc(String areaDesc) {
	   this.areaDesc = areaDesc;
	}
	/**
	  * 获取责任区描述
	  * @return 责任区描述
      */
	public String getAreaDesc() {
	   return areaDesc;
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
	  * 设定创建者ID
	  * @param createId
      */
	public void setCreateId(int createId) {
	   this.createId = createId;
	}
	/**
	  * 获取创建者ID
	  * @return 创建者ID
      */
	public int getCreateId() {
	   return createId;
	}
	/**
	  * 设定修改者ID
	  * @param updateId
      */
	public void setUpdateId(int updateId) {
	   this.updateId = updateId;
	}
	/**
	  * 获取修改者ID
	  * @return 修改者ID
      */
	public int getUpdateId() {
	   return updateId;
	}
	/**
	  * 设定状态（1:启用2：废止）
	  * @param status
      */
	public void setStatus(int status) {
	   this.status = status;
	}
	/**
	  * 获取状态（1:启用2：废止）
	  * @return 状态（1:启用2：废止）
      */
	public int getStatus() {
	   return status;
	}
}