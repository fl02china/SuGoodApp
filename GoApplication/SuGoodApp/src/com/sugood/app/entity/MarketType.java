package com.sugood.app.entity;

/**
 * Package :com.android.supermarket.supermaket.bean
 * Description :
 * Author :Rc3
 * Created at :2017/2/27 00:43.
 */

public class MarketType {

    public String url;
    public String title;
    public String content;
    public String price;
    public String soldNum;



    public MarketType(String url, String title, String content, String price, String soldNum) {
        this.url = url;
        this.title = title;
        this.content = content;
        this.price = price;
        this.soldNum = soldNum;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSoldNum() {
        return soldNum;
    }

    public void setSoldNum(String soldNum) {
        this.soldNum = soldNum;
    }
}
