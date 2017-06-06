package com.sugood.app.entity;

/**
 * Created by dec on 2016/12/19.
 */

public class SameCityYellowPage {

    private String address;
    private String shopId;
    private String name;
    private String  photo;
    private String telephone;
    private String mi;

    public String getAddress() {

        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMi() {
        return mi;
    }

    public void setMi(String mi) {
        this.mi = mi;
    }


    @Override
    public String toString() {
        return "SameCityYellowPage{" +
                "address='" + address + '\'' +
                ", shopId='" + shopId + '\'' +
                ", name='" + name + '\'' +
                ", photo='" + photo + '\'' +
                ", telephone='" + telephone + '\'' +
                ", mi='" + mi + '\'' +
                '}';
    }
}
