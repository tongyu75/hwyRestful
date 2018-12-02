package com.czz.hwy.bean.mission;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

/**
  * 环卫云项目启用
  * @author 李宇宁 16-七月-2015
  * @version V0.1
  */
public class CheckImage  extends PageReqParamDTO implements Serializable {

    private static final long serialVersionUID = -4975251178243077962L;

    // ID
    private int id;
    // 检查ID
    private int checkId;
    // 考核类型ID
    private int evalId;
    // 检查图片名字
    private String imageFileName;
    // 检查图片类型
    private String imageContentType;
    // 检查图片大小
    private String imageFileSize;
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
	  * 设定检查ID
	  * @param checkId
      */
	public void setCheckId(int checkId) {
	   this.checkId = checkId;
	}
	/**
	  * 获取检查ID
	  * @return 检查ID
      */
	public int getCheckId() {
	   return checkId;
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
	  * 设定检查图片名字
	  * @param imageFileName
      */
	public void setImageFileName(String imageFileName) {
	   this.imageFileName = imageFileName;
	}
	/**
	  * 获取检查图片名字
	  * @return 检查图片名字
      */
	public String getImageFileName() {
	   return imageFileName;
	}
	/**
	  * 设定检查图片类型
	  * @param imageContentType
      */
	public void setImageContentType(String imageContentType) {
	   this.imageContentType = imageContentType;
	}
	/**
	  * 获取检查图片类型
	  * @return 检查图片类型
      */
	public String getImageContentType() {
	   return imageContentType;
	}
	/**
	  * 设定检查图片大小
	  * @param imageFileSize
      */
	public void setImageFileSize(String imageFileSize) {
	   this.imageFileSize = imageFileSize;
	}
	/**
	  * 获取检查图片大小
	  * @return 检查图片大小
      */
	public String getImageFileSize() {
	   return imageFileSize;
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