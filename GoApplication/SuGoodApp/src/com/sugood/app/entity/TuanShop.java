package com.sugood.app.entity;

import java.io.Serializable;

/**
 * Created by wilk on 17/3/14 15:24
 * ganweib@gmail.com
 * describe:
 */

public class TuanShop implements Serializable {

    private static final long serialVersionUID = 39904379382648747L;

    private String num;
    private String lag;
    private String tel;
    private String Addr;
    private String  photo;
    private String lat;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getAddr() {
        return Addr;
    }

    public void setAddr(String addr) {
        Addr = addr;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getLag() {
        return lag;
    }

    public void setLag(String lag) {
        this.lag = lag;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "TuanShop{" +
                "num='" + num + '\'' +
                ", lag='" + lag + '\'' +
                ", tel='" + tel + '\'' +
                ", Addr='" + Addr + '\'' +
                ", photo='" + photo + '\'' +
                ", lat='" + lat + '\'' +
                '}';
    }
}
