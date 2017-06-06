package com.sugood.app.entity;

import java.io.Serializable;

/**
 * Created by wilk on 17/3/7 15:21
 * ganweib@gmail.com
 * describe:
 */

public class ShopDetail implements Serializable {

    private static final long serialVersionUID = 7871120003126559107L;

    private String logo;
    private String isNew;
    private String tel;
    private String isWifi;
    private String score;
    private String businessTime;
    private String lng;
    private String addr;
    private String photo;
    private String isWai;
    private String scoreNum;
    private String num;
    private String price;
    private String shopName;
    private String IsParking;
    private String lat;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getIsNew() {
        return isNew;
    }

    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getIsWifi() {
        return isWifi;
    }

    public void setIsWifi(String isWifi) {
        this.isWifi = isWifi;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getBusinessTime() {
        return businessTime;
    }

    public void setBusinessTime(String businessTime) {
        this.businessTime = businessTime;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getIsWai() {
        return isWai;
    }

    public void setIsWai(String isWai) {
        this.isWai = isWai;
    }

    public String getScoreNum() {
        return scoreNum;
    }

    public void setScoreNum(String scoreNum) {
        this.scoreNum = scoreNum;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getIsParking() {
        return IsParking;
    }

    public void setIsParking(String isParking) {
        IsParking = isParking;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return "ShopDetail{" +
                "logo='" + logo + '\'' +
                ", isNew='" + isNew + '\'' +
                ", tel='" + tel + '\'' +
                ", isWifi='" + isWifi + '\'' +
                ", score='" + score + '\'' +
                ", businessTime='" + businessTime + '\'' +
                ", lng='" + lng + '\'' +
                ", addr='" + addr + '\'' +
                ", photo='" + photo + '\'' +
                ", isWai='" + isWai + '\'' +
                ", scoreNum='" + scoreNum + '\'' +
                ", num='" + num + '\'' +
                ", price='" + price + '\'' +
                ", shopName='" + shopName + '\'' +
                ", IsParking='" + IsParking + '\'' +
                ", lat='" + lat + '\'' +
                '}';
    }
}
