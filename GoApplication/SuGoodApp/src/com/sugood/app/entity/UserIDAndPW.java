package com.sugood.app.entity;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

/**
 * Created by wilk on 17/3/18 16:00
 * ganweib@gmail.com
 * describe:
 */
@Table("UserIDAndPW")
public class UserIDAndPW {

    @PrimaryKey(AssignType.BY_MYSELF)
    @Column("userId")
    private String UserID;

    @Column("password")
    private String password;

    @Column("isLogin")
    private boolean isLogin;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    @Override
    public String toString() {
        return "UserIDAndPW{" +
                "UserID='" + UserID + '\'' +
                ", password='" + password + '\'' +
                ", isLogin=" + isLogin +
                '}';
    }


}
