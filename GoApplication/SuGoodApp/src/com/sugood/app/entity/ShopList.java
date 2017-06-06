package com.sugood.app.entity;

import android.text.TextUtils;

/**
 * Package :com.sugood.app.entity
 * Description :
 * Author :Rc3
 * Created at :2017/3/28 11:19.
 */

public class ShopList {
    String title;
    String seccsion;
    int goodsId;
    int price;
    int shopId;
    String photo;
    int soldNum;
    String intro;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSeccsion() {
        return seccsion;
    }

    public void setSeccsion(String seccsion) {
        this.seccsion = seccsion;
    }

    public String getGoodsId() {
        return String.valueOf(goodsId);
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getPrice() {

        return Double.parseDouble(price+"") / 100 + "元";

    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getShopId() {

        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSoldNum() {
        return soldNum + "人付款";
    }

    public void setSoldNum(int soldNum) {
        this.soldNum = soldNum;
    }

    public String getIntro() {
        if (TextUtils.isEmpty(intro)) {
            return "洗衣机";
        }
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}
