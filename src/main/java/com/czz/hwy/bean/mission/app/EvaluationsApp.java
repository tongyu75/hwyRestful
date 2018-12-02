package com.czz.hwy.bean.mission.app;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.czz.hwy.base.PageReqParamDTO;

/**
  * 环卫云项目启用
  * @author 李宇宁 16-七月-2015
  * @version V0.1
  */
public class EvaluationsApp extends PageReqParamDTO implements Serializable {

    private static final long serialVersionUID = 5038717166607537447L;

    // ID
    private Integer id;
    // 责任区ID
    private Integer areaId;
    // 责任点ID
    private Integer pointId;
    // 任务类别
    private Integer workType;
    // 考核类型（1：5克2：5分3：超时4：巡回）
    private Integer evalType;
    // 考核类型名字
    private String evalName;
    // 描述
    private String evalDesc;
    // 考核标准值
    private Double standardValue;
    // 考核标准单位
    private String standardUnit;
    // 超标界限值
    private Double limitValue;
    // 超标界限单位
    private String limitUnit;
    // 创建时间
    private Date createAt;
    // 修改时间
    private Date updateAt;
    //罚金
    private String fine;
    
    private List<Integer> integerList;
	
	public List<Integer> getIntegerList() {
		return integerList;
	}

	public void setIntegerList(List<Integer> integerList) {
		this.integerList = integerList;
	}
	
	public String getFine() {
		return fine;
	}
	public void setFine(String fine) {
		this.fine = fine;
	}
	/**
	  * 设定ID
	  * @param id
      */
	public void setId(Integer id) {
	   this.id = id;
	}
	/**
	  * 获取ID
	  * @return ID
      */
	public Integer getId() {
	   return id;
	}
	/**
	  * 设定责任区ID
	  * @param areaId
      */
	public void setAreaId(Integer areaId) {
	   this.areaId = areaId;
	}
	/**
	  * 获取责任区ID
	  * @return 责任区ID
      */
	public Integer getAreaId() {
	   return areaId;
	}
	/**
	  * 设定责任点ID
	  * @param pointId
      */
	public void setPointId(Integer pointId) {
	   this.pointId = pointId;
	}
	/**
	  * 获取责任点ID
	  * @return 责任点ID
      */
	public Integer getPointId() {
	   return pointId;
	}
	/**
	  * 设定任务类别
	  * @param workType
      */
	public void setWorkType(Integer workType) {
	   this.workType = workType;
	}
	/**
	  * 获取任务类别
	  * @return 任务类别
      */
	public Integer getWorkType() {
	   return workType;
	}
	/**
	  * 设定考核类型（1：5克2：5分3：超时4：巡回）
	  * @param evalType
      */
	public void setEvalType(Integer evalType) {
	   this.evalType = evalType;
	}
	/**
	  * 获取考核类型（1：5克2：5分3：超时4：巡回）
	  * @return 考核类型（1：5克2：5分3：超时4：巡回）
      */
	public Integer getEvalType() {
	   return evalType;
	}
	/**
	  * 设定考核类型名字
	  * @param evalName
      */
	public void setEvalName(String evalName) {
	   this.evalName = evalName;
	}
	/**
	  * 获取考核类型名字
	  * @return 考核类型名字
      */
	public String getEvalName() {
	   return evalName;
	}
	/**
	  * 设定描述
	  * @param evalDesc
      */
	public void setEvalDesc(String evalDesc) {
	   this.evalDesc = evalDesc;
	}
	/**
	  * 获取描述
	  * @return 描述
      */
	public String getEvalDesc() {
	   return evalDesc;
	}
	/**
	  * 设定考核标准值
	  * @param standardValue
      */
	public void setStandardValue(Double standardValue) {
	   this.standardValue = standardValue;
	}
	/**
	  * 获取考核标准值
	  * @return 考核标准值
      */
	public Double getStandardValue() {
	   return standardValue;
	}
	/**
	  * 设定考核标准单位
	  * @param standardUnit
      */
	public void setStandardUnit(String standardUnit) {
	   this.standardUnit = standardUnit;
	}
	/**
	  * 获取考核标准单位
	  * @return 考核标准单位
      */
	public String getStandardUnit() {
	   return standardUnit;
	}
	/**
	  * 设定超标界限值
	  * @param limitValue
      */
	public void setLimitValue(Double limitValue) {
	   this.limitValue = limitValue;
	}
	/**
	  * 获取超标界限值
	  * @return 超标界限值
      */
	public Double getLimitValue() {
	   return limitValue;
	}
	/**
	  * 设定超标界限单位
	  * @param limitUnit
      */
	public void setLimitUnit(String limitUnit) {
	   this.limitUnit = limitUnit;
	}
	/**
	  * 获取超标界限单位
	  * @return 超标界限单位
      */
	public String getLimitUnit() {
	   return limitUnit;
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