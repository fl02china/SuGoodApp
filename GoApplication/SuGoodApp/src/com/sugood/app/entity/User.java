package com.sugood.app.entity;

/**
 * Created by wilk on 17/3/24 21:06
 * ganweib@gmail.com
 * describe:
 */

public class User {

    private String account;
    private String nickname;
    private String face;
    private String userId;
    private String money;

    @Override
    public String toString() {
        return "User{" +
                "account='" + account + '\'' +
                ", nickname='" + nickname + '\'' +
                ", face='" + face + '\'' +
                ", userId='" + userId + '\'' +
                ", money='" + money + '\'' +
                '}';
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
