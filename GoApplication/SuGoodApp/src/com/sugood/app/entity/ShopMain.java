package com.sugood.app.entity;

import java.io.Serializable;

/**
 * Package :com.sugood.app.entity
 * Description :
 * Author :Rc3
 * Created at :2017/3/28 09:56.
 */

public class ShopMain implements Serializable {

    private static final long serialVersionUID = -4306233642818446519L;

    int parentId;
    int rate;
    int orderBy;
    String photo;
    int cateId;
    String cateName;

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(int orderBy) {
        this.orderBy = orderBy;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getCateId() {
        return cateId;
    }

    public void setCateId(int cateId) {
        this.cateId = cateId;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    @Override
    public String toString() {
        return "ShopMain{" +
                "parentId=" + parentId +
                ", rate=" + rate +
                ", orderBy=" + orderBy +
                ", photo='" + photo + '\'' +
                ", cateId=" + cateId +
                ", cateName='" + cateName + '\'' +
                '}';
    }
}
