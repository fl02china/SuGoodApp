package com.sugood.app.entity;

/**
 * Package :com.sugood.app.entity
 * Description :
 * Author :Rc3
 * Created at :2017/3/28 22:49.
 */

public class ShopInfo {


    /**
     * tel : 18933817681
     * addr : 法院对面上50米
     * photo : /attachs/2017/01/19/thumb_5880732104f16.jpg
     */

    private String tel;
    private String addr;
    private String photo;
    private String shopName;


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

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    @Override
    public String toString() {
        return "ShopInfo{" +
                "tel='" + tel + '\'' +
                ", addr='" + addr + '\'' +
                ", photo='" + photo + '\'' +
                ", shopName='" + shopName + '\'' +
                '}';
    }
}
