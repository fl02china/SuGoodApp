package com.sugood.app.entity;

/**
 * Package :com.android.supermarket.supermaket.bean
 * Description :
 * Author :Rc3
 * Created at :2017/2/27 16:11.
 */

public class MarketAddress {

    public String address;
    public String name;
    public String phoneNum;

    public MarketAddress(String address, String name, String phoneNum) {
        this.address = address;
        this.name = name;
        this.phoneNum = phoneNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
