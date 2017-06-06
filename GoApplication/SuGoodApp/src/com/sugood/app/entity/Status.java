package com.sugood.app.entity;

import java.io.Serializable;

/**
 * Created by wilk on 17/4/25 18:33
 * ganweib@gmail.com
 * describe:
 */

public class Status implements Serializable {
    private static final long serialVersionUID = -8313741753216050200L;

    private String time;
    private int status;
    private String name;
    private String mobile;
    private String typeOrderId;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTypeOrderId() {
        return typeOrderId;
    }

    public void setTypeOrderId(String typeOrderId) {
        this.typeOrderId = typeOrderId;
    }

    @Override
    public String toString() {
        return "Status{" +
                "time='" + time + '\'' +
                ", status=" + status +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", typeOrderId='" + typeOrderId + '\'' +
                '}';
    }
}
