package com.sugood.app.entity;

import java.io.Serializable;

/**
 * Created by wilk on 17/3/7 15:17
 * ganweib@gmail.com
 * describe: 团购信息
 */

public class TuanGou implements Serializable {

    private static final long serialVersionUID = -4181831558755299875L;

    private String tuanPrice;
    private String photo;
    private String soldNum;
    private String taoNum;
    private String bgDate;
    private String endDate;
    private String useIntegral;
    private String isReturnCash;
    private String title;
    private String introl;
    private String tuanId;

    public String getTuanPrice() {
        return Double.parseDouble(tuanPrice) / 100 + "";
    }

    public void setTuanPrice(String tuanPrice) {
        this.tuanPrice = tuanPrice;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSoldNum() {
        return soldNum;
    }

    public void setSoldNum(String soldNum) {
        this.soldNum = soldNum;
    }

    public String getTaoNum() {
        return taoNum;
    }

    public void setTaoNum(String taoNum) {
        this.taoNum = taoNum;
    }

    public String getBgDate() {
        return bgDate;
    }

    public void setBgDate(String bgDate) {
        this.bgDate = bgDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getUseIntegral() {
        return useIntegral;
    }

    public void setUseIntegral(String useIntegral) {
        this.useIntegral = useIntegral;
    }

    public String getIsReturnCash() {
        return isReturnCash;
    }

    public void setIsReturnCash(String isReturnCash) {
        this.isReturnCash = isReturnCash;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntrol() {
        return introl;
    }

    public void setIntrol(String introl) {
        this.introl = introl;
    }

    public String getTuanId() {
        return tuanId;
    }

    public void setTuanId(String tuanId) {
        this.tuanId = tuanId;
    }

    @Override
    public String toString() {
        return "TuanGou{" +
                "tuanPrice='" + tuanPrice + '\'' +
                ", photo='" + photo + '\'' +
                ", soldNum='" + soldNum + '\'' +
                ", taoNum='" + taoNum + '\'' +
                ", bgDate='" + bgDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", useIntegral='" + useIntegral + '\'' +
                ", isReturnCash='" + isReturnCash + '\'' +
                ", title='" + title + '\'' +
                ", introl='" + introl + '\'' +
                ", tuanId='" + tuanId + '\'' +
                '}';
    }
}
