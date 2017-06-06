package com.sugood.app.entity;

import java.io.Serializable;

/**
 * Created by wilk on 17/4/25 18:02
 * ganweib@gmail.com
 * describe:
 */

public class Cell implements Serializable {
    private static final long serialVersionUID = 4564901305528171580L;

    private String orderId;
    private String type;
    private String typeOrderId;
    private String deliveryId;
    private String shopId;
    private String cityId;
    private String areaId;
    private String businessId;
    private String lat;
    private String lng;
    private String userId;
    private String shopName;
    private String shopMobile;
    private String shopAddr;
    private String name;
    private String mobile;
    private String addr;
    private String addrId;
    private String  addressId;
    private String logisticsPrice;
    private String isAppoint;
    private String  appointUserId;
    private String createTime;
    private String updateTime;
    private String endTime;
    private String status;
    private String closed;
    private String totalPrice;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeOrderId() {
        return typeOrderId;
    }

    public void setTypeOrderId(String typeOrderId) {
        this.typeOrderId = typeOrderId;
    }

    public String getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopMobile() {
        return shopMobile;
    }

    public void setShopMobile(String shopMobile) {
        this.shopMobile = shopMobile;
    }

    public String getShopAddr() {
        return shopAddr;
    }

    public void setShopAddr(String shopAddr) {
        this.shopAddr = shopAddr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getAddrId() {
        return addrId;
    }

    public void setAddrId(String addrId) {
        this.addrId = addrId;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getLogisticsPrice() {
        return logisticsPrice;
    }

    public void setLogisticsPrice(String logisticsPrice) {
        this.logisticsPrice = logisticsPrice;
    }

    public String getIsAppoint() {
        return isAppoint;
    }

    public void setIsAppoint(String isAppoint) {
        this.isAppoint = isAppoint;
    }

    public String getAppointUserId() {
        return appointUserId;
    }

    public void setAppointUserId(String appointUserId) {
        this.appointUserId = appointUserId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getClosed() {
        return closed;
    }

    public void setClosed(String closed) {
        this.closed = closed;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }


    @Override
    public String toString() {
        return "Cell{" +
                "orderId='" + orderId + '\'' +
                ", type='" + type + '\'' +
                ", typeOrderId='" + typeOrderId + '\'' +
                ", deliveryId='" + deliveryId + '\'' +
                ", shopId='" + shopId + '\'' +
                ", cityId='" + cityId + '\'' +
                ", areaId='" + areaId + '\'' +
                ", businessId='" + businessId + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                ", userId='" + userId + '\'' +
                ", shopName='" + shopName + '\'' +
                ", shopMobile='" + shopMobile + '\'' +
                ", shopAddr='" + shopAddr + '\'' +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", addr='" + addr + '\'' +
                ", addrId='" + addrId + '\'' +
                ", addressId='" + addressId + '\'' +
                ", logisticsPrice='" + logisticsPrice + '\'' +
                ", isAppoint='" + isAppoint + '\'' +
                ", appointUserId='" + appointUserId + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", status='" + status + '\'' +
                ", closed='" + closed + '\'' +
                ", totalPrice='" + totalPrice + '\'' +
                '}';
    }
}
