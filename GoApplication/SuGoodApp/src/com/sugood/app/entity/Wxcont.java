package com.sugood.app.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/10 0010.
 */

public class Wxcont implements Serializable {
    private static final long serialVersionUID = -6919461967497580385L;
    private String appid;
    private String noncestr;

    private String partnerid;
    private String prepayid;
    private String sign;
    private String timestamp;

    @Override
    public String toString() {
        return "Wxcont{" +
                "appid='" + appid + '\'' +
                ", noncestr='" + noncestr + '\'' +

                ", partnerid='" + partnerid + '\'' +
                ", prepayid='" + prepayid + '\'' +
                ", sign='" + sign + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }



    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
