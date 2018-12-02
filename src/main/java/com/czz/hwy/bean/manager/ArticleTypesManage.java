package com.czz.hwy.bean.manager;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

public class ArticleTypesManage extends PageReqParamDTO implements Serializable{

    private static final long serialVersionUID = -1807565730150550735L;

    // ID
    private int id;

    // 劳资编号
    private int articleType;

    // 劳资名字
    private String articleName;

    // 创建时间
    private Date createAt;

    // 修改时间
    private Date updateAt;

    // 物品数量
    private int articleNumber;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArticleType() {
        return articleType;
    }

    public void setArticleType(int articleType) {
        this.articleType = articleType;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName == null ? null : articleName.trim();
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public int getArticleNumber() {
        return articleNumber;
    }

    public void setArticleNumber(int articleNumber) {
        this.articleNumber = articleNumber;
    }
}