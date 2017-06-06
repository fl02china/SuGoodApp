package com.sugood.app.entity;

import java.io.Serializable;

/**
 * Created by wilk on 17/4/21 14:19
 * ganweib@gmail.com
 * describe:
 */

public class Msg implements Serializable {

    private static final long serialVersionUID = -6661062121476445070L;

    private String createTime;
    private String linkUrl;
    private String cityId;
    private String workerId;
    private String createIp;
    private String intro;
    private String villageId;
    private String title;
    private String adminId;
    private String deliveryId;
    private String details;
    private String views;
    private String shopId;
    private String userId;
    private String msgId;
    private String closed;
    private String cateId;

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    private String communityId;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public String getCreateIp() {
        return createIp;
    }

    public void setCreateIp(String createIp) {
        this.createIp = createIp;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getVillageId() {
        return villageId;
    }

    public void setVillageId(String villageId) {
        this.villageId = villageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getClosed() {
        return closed;
    }

    public void setClosed(String closed) {
        this.closed = closed;
    }

    public String getCateId() {
        return cateId;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId;
    }

    @Override
    public String toString() {
        return "Msg{" +
                "createTime='" + createTime + '\'' +
                ", linkUrl='" + linkUrl + '\'' +
                ", cityId='" + cityId + '\'' +
                ", workerId='" + workerId + '\'' +
                ", createIp='" + createIp + '\'' +
                ", intro='" + intro + '\'' +
                ", villageId='" + villageId + '\'' +
                ", title='" + title + '\'' +
                ", adminId='" + adminId + '\'' +
                ", deliveryId='" + deliveryId + '\'' +
                ", details='" + details + '\'' +
                ", views='" + views + '\'' +
                ", shopId='" + shopId + '\'' +
                ", userId='" + userId + '\'' +
                ", msgId='" + msgId + '\'' +
                ", closed='" + closed + '\'' +
                ", cateId='" + cateId + '\'' +
                '}';
    }
}
