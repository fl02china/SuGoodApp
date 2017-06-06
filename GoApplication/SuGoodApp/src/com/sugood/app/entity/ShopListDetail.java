package com.sugood.app.entity;

/**
 * Package :com.sugood.app.entity
 * Description :
 * Author :Rc3
 * Created at :2017/3/28 15:50.
 */

public class ShopListDetail {

    String title;
    int price;
    String seccsion;
    String photo;
    int shopId;
    String intro;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
       this.photo = photo;
    }

    public String getShopId() {
        return String.valueOf(shopId);
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}
