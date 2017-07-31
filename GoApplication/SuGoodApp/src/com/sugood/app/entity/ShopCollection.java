package com.sugood.app.entity;

/**
 * Package :com.android.supermarket.user.bean
 * Description :
 * Author :Rc3
 * Created at :2017/3/10 15:06.
 */

public class ShopCollection {


    /**
     * shopId : 10
     * shopName : 市场
     * tel : 0759-8899118
     * addr : 广东省雷州市新城大道102号
     * photo : /attachs/2017/04/11/thumb_58ec482fdbf2d.jpg
     */

    private int shopId;
    private String shopName;
    private String tel;
    private String addr;
    private String photo;
    private String type;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ShopCollection{" +
                "id=" + id +
                "shopId=" + shopId +
                ", shopName='" + shopName + '\'' +
                ", tel='" + tel + '\'' +
                ", addr='" + addr + '\'' +
                ", photo='" + photo + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
