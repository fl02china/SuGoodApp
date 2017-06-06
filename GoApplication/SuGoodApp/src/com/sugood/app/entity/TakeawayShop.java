package com.sugood.app.entity;

import java.io.Serializable;

/**
 * Package :com.android.supermarket.takeaway.bean
 * Description :
 * Author :Rc3
 * Created at :2017/2/28 10:47.
 */

public class TakeawayShop implements Serializable {


    private static final long serialVersionUID = 5104985075222248879L;
    /**
     * min : 203
     * logo : /attachs/2017/01/19/thumb_58807a7a8be68.jpg
     * fanMoney : 0
     * isNew : 0
     * score : 0
     * sinceMoney : 2000
     * youTypeid : null
     * photo : /attachs/2017/01/19/thumb_58807a8aaff6c.jpg
     * distribution : 30
     * sun : 0
     * soldNum : 0
     * discount : 0
     * isOpen : 0
     * amount : 0
     * minAmount : 0
     * monthNum : 0
     * shopName : 雷记馨怡饼店（美好华庭店）
     * shopId : 65
     * isFan : 0
     * typeId :
     * logistics : 300
     * fullMoney : 0
     * newMoney : 0
     * isPei : 0
     */

    private int min;
    private String logo;
    private double fanMoney;
    private double isNew;
    private int score;
    private double sinceMoney;
    private String youTypeid;
    private String photo;
    private int distribution;
    private int sun;
    private int soldNum;
    private int discount;
    private int isOpen;
    private double amount;
    private double minAmount;
    private int monthNum;
    private String shopName;
    private int shopId;
    private int isFan;
    private String typeId;
    private int logistics;
    private double fullMoney;
    private double newMoney;
    private int isPei;

    public String getMin() {
        return String.valueOf(min);
    }

    public void setMin(int min) {
        this.min = min;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public double getFanMoney() {
        return Double.parseDouble(fanMoney + "") / 100;
    }

    public void setFanMoney(double fanMoney) {
        this.fanMoney = fanMoney;
    }

    public double getIsNew() {
        return Double.parseDouble(isNew + "") / 100;
    }

    public void setIsNew(double isNew) {
        this.isNew = isNew;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public double getSinceMoney() {
        return Double.parseDouble(sinceMoney + "") / 100;
    }

    public void setSinceMoney(double sinceMoney) {
        this.sinceMoney = sinceMoney;
    }

    public String getYouTypeid() {
        return youTypeid;
    }

    public void setYouTypeid(String youTypeid) {
        this.youTypeid = youTypeid;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getDistribution() {
        return distribution;
    }

    public void setDistribution(int distribution) {
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

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(int isOpen) {
        this.isOpen = isOpen;
    }

    public double getAmount() {
        return Double.parseDouble(amount + "") / 100;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getMinAmount() {
        return Double.parseDouble(minAmount + "") / 100;
    }

    public void setMinAmount(double minAmount) {
        this.minAmount = minAmount;
    }

    public int getMonthNum() {
        return monthNum;
    }

    public void setMonthNum(int monthNum) {
        this.monthNum = monthNum;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
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

    public int getLogistics() {
        return logistics;
    }

    public void setLogistics(int logistics) {
        this.logistics = logistics / 100;
    }

    public double getFullMoney() {
        return Double.parseDouble(fullMoney+"")/100;
    }

    public void setFullMoney(double fullMoney) {
        this.fullMoney = fullMoney;
    }

    public double getNewMoney() {
        return Double.parseDouble(newMoney+"")/100;
    }

    public void setNewMoney(double newMoney) {
        this.newMoney = newMoney;
    }

    public int getIsPei() {
        return isPei;
    }

    public void setIsPei(int isPei) {
        this.isPei = isPei;
    }
}
