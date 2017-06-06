package com.sugood.app.entity;

import java.io.Serializable;

/**
 * Created by wilk on 17/2/27 11:26
 * ganweib@gmail.com
 * describe:
 */

public class Food implements Serializable {

    private static final long serialVersionUID = -8185092091785281721L;

    private String min;
    private String logo;
    private String fanMoney;
    private int isNew;
    private int score;
    private String sinceMoney;
    private int youTypeid;
    private String photo;
    private String distribution;
    private int sun;
    private int soldNum;
    private String discount;
    private int isOpen;
    private int amount;
    private int minAmount;
    private String monthNum;
    private String shopName;
    private String shopId;
    private int isFan;
    private String typeId;
    private String logistics;
    private String fullMoney;
    private String newMoney;
    private int isPei;

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getFanMoney() {
        return fanMoney;
    }

    public void setFanMoney(String fanMoney) {
        this.fanMoney = fanMoney;
    }

    public int getIsNew() {
        return isNew;
    }

    public void setIsNew(int isNew) {
        this.isNew = isNew;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getSinceMoney() {
        return sinceMoney;
    }

    public void setSinceMoney(String sinceMoney) {
        this.sinceMoney = Integer.parseInt(sinceMoney) / 100 + "";
    }

    public int getYouTypeid() {
        return youTypeid;
    }

    public void setYouTypeid(int youTypeid) {
        this.youTypeid = youTypeid;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDistribution() {
        return distribution;
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }

    public int getSun() {
        return sun;
    }

    public void setSun(int sun) {
        this.sun = sun;
    }

    public int getSoldNum() {
        return soldNum;
    }

    public void setSoldNum(int soldNum) {
        this.soldNum = soldNum;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public int getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(int isOpen) {
        this.isOpen = isOpen;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount / 100;
    }

    public int getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(int minAmount) {
        this.minAmount = minAmount / 100;
    }

    public String getMonthNum() {
        return monthNum;
    }

    public void setMonthNum(String monthNum) {
        this.monthNum = monthNum;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public int getIsFan() {
        return isFan;
    }

    public void setIsFan(int isFan) {
        this.isFan = isFan;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getLogistics() {
        return logistics;
    }

    public void setLogistics(String logistics) {
        this.logistics = Integer.parseInt(logistics) / 100 + "";
    }

    public String getFullMoney() {
        return fullMoney;
    }

    public void setFullMoney(String fullMoney) {
        this.fullMoney = Integer.parseInt(fullMoney) / 100 + "";
    }

    public String getNewMoney() {
        return newMoney;
    }

    public void setNewMoney(String newMoney) {
        this.newMoney = Integer.parseInt(newMoney) / 100 + "";
    }

    public int getIsPei() {
        return isPei;
    }

    public void setIsPei(int isPei) {
        this.isPei = isPei;
    }

    @Override
    public String toString() {
        return "Food{" +
                "min='" + min + '\'' +
                ", logo='" + logo + '\'' +
                ", fanMoney='" + fanMoney + '\'' +
                ", isNew=" + isNew +
                ", score=" + score +
                ", sinceMoney='" + sinceMoney + '\'' +
                ", youTypeid=" + youTypeid +
                ", photo='" + photo + '\'' +
                ", distribution='" + distribution + '\'' +
                ", sun=" + sun +
                ", soldNum=" + soldNum +
                ", discount='" + discount + '\'' +
                ", isOpen=" + isOpen +
                ", amount=" + amount +
                ", minAmount=" + minAmount +
                ", monthNum='" + monthNum + '\'' +
                ", shopName='" + shopName + '\'' +
                ", shopId='" + shopId + '\'' +
                ", isFan=" + isFan +
                ", typeId='" + typeId + '\'' +
                ", logistics='" + logistics + '\'' +
                ", fullMoney='" + fullMoney + '\'' +
                ", newMoney='" + newMoney + '\'' +
                ", isPei=" + isPei +
                '}';
    }
}
