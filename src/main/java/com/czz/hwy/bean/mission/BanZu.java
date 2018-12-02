package com.czz.hwy.bean.mission;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
  * 班组实体类
  * @author 张咏雪 2016-09-27
  * @version V0.1
  */
public class BanZu extends PageReqParamDTO implements Serializable {

    private static final long serialVersionUID = 1597023096156371609L;
   
    private int banzuId; // ID
    
    private String banzuName;//班组名称
    
    private int createUserId;//创建人ID
    
    private Date createAt;//创建时间
    
    private int updateUserId;//修改人ID
    
    private Date updateAt;//修改时间

	public int getBanzuId() {
		return banzuId;
	}

	public void setBanzuId(int banzuId) {
		this.banzuId = banzuId;
	}

	public String getBanzuName() {
		return banzuName;
	}

	public void setBanzuName(String banzuName) {
		this.banzuName = banzuName;
	}

	public int getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public int getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(int updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}
    
}