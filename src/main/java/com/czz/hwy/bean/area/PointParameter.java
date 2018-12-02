package com.czz.hwy.bean.area;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
  * 环卫云项目启用
  * @author 李宇宁 16-七月-2015
  * @version V0.1
  */
public class PointParameter extends PageReqParamDTO  implements Serializable {

    private static final long serialVersionUID = -8637478848857994010L;

    // ID
    private int id;
    // 责任点ID
    private int pointId;
    // 采集频率
    private int colRate;
    // 上报频率
    private int reportRate;
    // 停留时间
    private int stayTime;
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
	  * 设定责任点ID
	  * @param pointId
      */
	public void setPointId(int pointId) {
	   this.pointId = pointId;
	}
	/**
	  * 获取责任点ID
	  * @return 责任点ID
      */
	public int getPointId() {
	   return pointId;
	}
	/**
	  * 设定采集频率
	  * @param colRate
      */
	public void setColRate(int colRate) {
	   this.colRate = colRate;
	}
	/**
	  * 获取采集频率
	  * @return 采集频率
      */
	public int getColRate() {
	   return colRate;
	}
	/**
	  * 设定上报频率
	  * @param reportRate
      */
	public void setReportRate(int reportRate) {
	   this.reportRate = reportRate;
	}
	/**
	  * 获取上报频率
	  * @return 上报频率
      */
	public int getReportRate() {
	   return reportRate;
	}
	/**
	  * 设定停留时间
	  * @param stayTime
      */
	public void setStayTime(int stayTime) {
	   this.stayTime = stayTime;
	}
	/**
	  * 获取停留时间
	  * @return 停留时间
      */
	public int getStayTime() {
	   return stayTime;
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