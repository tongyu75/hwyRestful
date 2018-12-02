package com.czz.hwy.bean.mission.app;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.czz.hwy.base.PageReqParamDTO;

/**
  * 考核分类任务提交图片详细实体类，用于app接口
  * @author 张咏雪
  * @createDate 2016-11-07
  */
public class CheckImageApp  extends PageReqParamDTO implements Serializable {

    private static final long serialVersionUID = -4975251178243077962L;

    private int id;// ID
    // 检查ID(外键ID)
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
    
    private List<File> image; // 上传的文件
    
   
   
    private String savePath;
    private String index;
    
 	
 	/*// 定义考核类型为举报任务
  	private static final int EVAL_TYPE_JUBAO = 3;
  	
  	// 定义考核类型为监察任务
   	private static final int EVAL_TYPE_JIANCHA = 4;*/
    
	/**
	  * 设定ID
	  * @param id
      */
	public void setId(int id) {
	   this.id = id;
	}
	public List<File> getImage() {
		return image;
	}
	public void setImage(List<File> image) {
		this.image = image;
	}
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
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