package com.sugood.app.entity;

import java.io.Serializable;

/**
 * Created by wilk on 17/2/24 10:35
 * ganweib@gmail.com
 * describe:
 */

public class Area implements Serializable {

    private static final long serialVersionUID = -8151003110422752877L;
    private String name;
    private String areaId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    @Override
    public String toString() {
        return "Area{" +
                "name='" + name + '\'' +
                ", areaId='" + areaId + '\'' +
                '}';
    }
}
