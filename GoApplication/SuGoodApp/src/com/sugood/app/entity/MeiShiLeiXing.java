package com.sugood.app.entity;

import java.io.Serializable;

/**
 * Created by wilk on 17/4/17 09:30
 * ganweib@gmail.com
 * describe:
 */

public class MeiShiLeiXing implements Serializable {
    private static final long serialVersionUID = -2787769294880652365L;

    private String sum;
    private String cateId;
    private String cateName;

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getCateId() {
        return cateId;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    @Override
    public String toString() {
        return "MeiShiLeiXing{" +
                "sum='" + sum + '\'' +
                ", cateId='" + cateId + '\'' +
                ", cateName='" + cateName + '\'' +
                '}';
    }
}
