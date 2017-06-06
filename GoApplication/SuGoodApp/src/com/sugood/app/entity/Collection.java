package com.sugood.app.entity;

/**
 * Package :com.sugood.app.entity
 * Description :
 * Author :Rc3
 * Created at :2017/04/21 09:52.
 */

public class Collection {

    /**
     * goodsId : 147
     * title : 奈瑞儿疏肝利胆保养套组（体验）
     * price : 15800
     * shopId : 102
     * mallPrice : 14200
     * photo : /attachs/2017/02/09/thumb_589c273b7d9bc.jpg
     * intro : 仅售142元！价值158元的疏肝利胆保养护肤1次
     */

    private int goodsId;
    private String title;
    private int price;
    private int shopId;
    private int mallPrice;
    private String photo;
    private String intro;

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price/100;
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

    public int getMallPrice() {
        return mallPrice/100;
    }

    public void setMallPrice(int mallPrice) {
        this.mallPrice = mallPrice;
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
