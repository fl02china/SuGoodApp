package com.sugood.app.entity;

import java.io.Serializable;

/**
 * Created by wilk on 17/3/7 15:14
 * ganweib@gmail.com
 * describe: 商铺优惠信息
 */

public class YouHui implements Serializable {

    private static final long serialVersionUID = -8064944240699084493L;

    private String amount;
    private String YouHuiXingX;
    private String minAmount;
    private String typeId;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getYouHuiXingX() {
        return YouHuiXingX;
    }

    public void setYouHuiXingX(String youHuiXingX) {
        YouHuiXingX = youHuiXingX;
    }

    public String getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(String minAmount) {
        this.minAmount = minAmount;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return "YouHui{" +
                "amount='" + amount + '\'' +
                ", YouHuiXingX='" + YouHuiXingX + '\'' +
                ", minAmount='" + minAmount + '\'' +
                ", typeId='" + typeId + '\'' +
                '}';
    }
}
