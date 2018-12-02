package com.czz.hwy.bean.manager.app;

import java.util.Date;

public class ToiletInformationApp {
    private Integer id;

    private String toiletPlaceName;

    private Double lat;

    private Double lng;

    private Date createAt;

    private Date updateAt;

    private Integer createId;

    private Integer updateId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToiletPlaceName() {
        return toiletPlaceName;
    }

    public void setToiletPlaceName(String toiletPlaceName) {
        this.toiletPlaceName = toiletPlaceName;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
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

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public Integer getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }
}