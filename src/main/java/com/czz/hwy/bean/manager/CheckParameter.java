package com.czz.hwy.bean.manager;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
  * 环卫云项目启用
  * @author 李宇宁 16-七月-2015
  * @version V0.1
  */
public class CheckParameter extends PageReqParamDTO  implements Serializable {

    private static final long serialVersionUID = -2517212139692920069L;

    // ID
    private int id;
    // 经过次数
    private int passCount;
    // 单点访问频率
    private int accessRate;
    // 创建时间
    private Date createAt;
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
	  * 设定经过次数
	  * @param passCount
      */
	public void setPassCount(int passCount) {
	   this.passCount = passCount;
	}
	/**
	  * 获取经过次数
	  * @return 经过次数
      */
	public int getPassCount() {
	   return passCount;
	}
	/**
	  * 设定单点访问频率
	  * @param accessRate
      */
	public void setAccessRate(int accessRate) {
	   this.accessRate = accessRate;
	}
	/**
	  * 获取单点访问频率
	  * @return 单点访问频率
      */
	public int getAccessRate() {
	   return accessRate;
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
}