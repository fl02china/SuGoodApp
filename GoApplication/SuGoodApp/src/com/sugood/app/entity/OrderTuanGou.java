package com.sugood.app.entity;

/**
 * Created by Administrator on 2017/8/3.
 */

public class OrderTuanGou {

    /**
     * codeId : 41
     * code : 78359772
     * userId : 871
     * isUsed : 871
     * orderId : 201708031157379871
     * tuanId : 392
     * realMoney : 1
     * failDate : 2019-03-28 00:00:00
     * logo : null
     * createTime : 1501732670
     * title : 特价出售仙人掌15元
     * photo : /attachs/2017/03/22/thumb_58d1f48a45ab0.jpg
     * status : 1
     * shopName : null
     */

    private int codeId;
    private String code;
    private int userId;
    private int isUsed;
    private String orderId;
    private int tuanId;
    private int realMoney;
    private String failDate;
    private Object logo;
    private int createTime;
    private String title;
    private String photo;
    private int status;
    private String shopName;
    private int shopId;

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public int getCodeId() {
        return codeId;
    }

    public void setCodeId(int codeId) {
        this.codeId = codeId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(int isUsed) {
        this.isUsed = isUsed;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getTuanId() {
        return tuanId;
    }

    public void setTuanId(int tuanId) {
        this.tuanId = tuanId;
    }

    public int getRealMoney() {
        return realMoney;
    }

    public void setRealMoney(int realMoney) {
        this.realMoney = realMoney;
    }

    public String getFailDate() {
        return failDate;
    }

    public void setFailDate(String failDate) {
        this.failDate = failDate;
    }

    public Object getLogo() {
        return logo;
    }

    public void setLogo(Object logo) {
        this.logo = logo;
    }

    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
