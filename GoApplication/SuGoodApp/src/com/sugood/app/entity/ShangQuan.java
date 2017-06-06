package com.sugood.app.entity;

import java.io.Serializable;

/**
 * Created by wilk on 17/4/17 09:32
 * ganweib@gmail.com
 * describe:
 */

public class ShangQuan implements Serializable {
    private static final long serialVersionUID = 4113357363541985055L;

    private String businessName;
    private String orderby;
    private String isHot;
    private String closed;
    private String businessId;
    private String areaId;

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getOrderby() {
        return orderby;
    }

    public void setOrderby(String orderby) {
        this.orderby = orderby;
    }

    public String getIsHot() {
        return isHot;
    }

    public void setIsHot(String isHot) {
        this.isHot = isHot;
    }

    public String getClosed() {
        return closed;
    }

    public void setClosed(String closed) {
        this.closed = closed;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    @Override
    public String toString() {
        return "ShangQuan{" +
                "businessName='" + businessName + '\'' +
                ", orderby='" + orderby + '\'' +
                ", isHot='" + isHot + '\'' +
                ", closed='" + closed + '\'' +
                ", businessId='" + businessId + '\'' +
                ", areaId='" + areaId + '\'' +
                '}';
    }
}
