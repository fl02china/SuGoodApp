package com.sugood.app.entity;

import java.io.Serializable;

/**
 * Created by wilk on 17/2/24 10:53
 * ganweib@gmail.com
 * describe:
 */

public class Cty implements Serializable {
    private static final long serialVersionUID = -879638314131169132L;

    private String name;
    private String cityId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    @Override
    public String toString() {
        return "Cty{" +
                "name='" + name + '\'' +
                ", cityId='" + cityId + '\'' +
                '}';
    }
}
