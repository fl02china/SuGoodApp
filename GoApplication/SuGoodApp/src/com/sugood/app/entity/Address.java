package com.sugood.app.entity;

import java.io.Serializable;

/**
 * Created by wilk on 17/3/27 16:44
 * ganweib@gmail.com
 * describe:
 */

public class Address implements Serializable {


    private static final long serialVersionUID = 4491388723891279080L;

    private String id;
    private String provinceId;
    private String defaults;
    private String cityId;
    private String areaStr;
    private String tel;
    private String userId;
    private String xm;
    private String closed;
    private String areaId;
    private String info;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getDefaults() {
        return defaults;
    }

    public void setDefaults(String defaults) {
        this.defaults = defaults;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getAreaStr() {
        return areaStr;
    }

    public void setAreaStr(String areaStr) {
        this.areaStr = areaStr;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getClosed() {
        return closed;
    }

    public void setClosed(String closed) {
        this.closed = closed;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "Address{" +
                "id='" + id + '\'' +
                ", provinceId='" + provinceId + '\'' +
                ", defaults='" + defaults + '\'' +
                ", cityId='" + cityId + '\'' +
                ", areaStr='" + areaStr + '\'' +
                ", tel='" + tel + '\'' +
                ", userId='" + userId + '\'' +
                ", xm='" + xm + '\'' +
                ", closed='" + closed + '\'' +
                ", areaId='" + areaId + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
