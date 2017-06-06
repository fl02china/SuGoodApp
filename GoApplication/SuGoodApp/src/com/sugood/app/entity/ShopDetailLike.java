package com.sugood.app.entity;

/**
 * Package :com.sugood.app.entity
 * Description :
 * Author :Rc3
 * Created at :2017/3/29 00:41.
 */

public class ShopDetailLike {

    String title;
    int price;
    String seccsion;
    int shopId;
    String photo;
    String intro;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {

        String thePrice = String.valueOf(price);

        if (price==0) {
            return "1999元";
        } else {
            return thePrice.substring(0, thePrice.length() - 2) + "." + thePrice.substring(thePrice.length() - 2) + "元";

        }
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getSeccsion() {
        return seccsion;
    }

    public void setSeccsion(String seccsion) {
        this.seccsion = seccsion;
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

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}
