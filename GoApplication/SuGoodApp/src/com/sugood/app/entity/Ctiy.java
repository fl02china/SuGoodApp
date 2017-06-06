package com.sugood.app.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wilk on 17/2/24 10:29
 * ganweib@gmail.com
 * describe:
 */

public class Ctiy implements Serializable {

    private static final long serialVersionUID = -1639526672888547236L;
    private List<Area> area;
    private Cty city;

    public List<Area> getArea() {
        return area;
    }

    public void setArea(List<Area> area) {
        this.area = area;
    }

    public Cty getCity() {
        return city;
    }

    public void setCity(Cty city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Ctiy{" +
                "area=" + area +
                ", city=" + city +
                '}';
    }
}
