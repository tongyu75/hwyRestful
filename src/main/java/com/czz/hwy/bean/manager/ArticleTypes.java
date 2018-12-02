package com.czz.hwy.bean.manager;

import java.io.Serializable;
import java.util.Date;

import com.czz.hwy.base.PageReqParamDTO;

public class ArticleTypes extends PageReqParamDTO implements Serializable{

	private static final long serialVersionUID = -5289241745044977057L;

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

    // 物品总数
    private int articleTotal;

    // 当前库存
    private int articleStock;

    // 使用中
    private int articleUse;

    // 已报废
    private int articleScrap;

    // 劳资编号(参数)
    private int inArticleType;
    
    public int getInArticleType() {
		return inArticleType;
	}

	public void setInArticleType(int inArticleType) {
		this.inArticleType = inArticleType;
	}

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

    public int getArticleTotal() {
        return articleTotal;
    }

    public void setArticleTotal(int articleTotal) {
        this.articleTotal = articleTotal;
    }

    public int getArticleStock() {
        return articleStock;
    }

    public void setArticleStock(int articleStock) {
        this.articleStock = articleStock;
    }

    public int getArticleUse() {
        return articleUse;
    }

    public void setArticleUse(int articleUse) {
        this.articleUse = articleUse;
    }

    public int getArticleScrap() {
        return articleScrap;
    }

    public void setArticleScrap(int articleScrap) {
        this.articleScrap = articleScrap;
    }
}