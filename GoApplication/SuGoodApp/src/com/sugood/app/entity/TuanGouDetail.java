package com.sugood.app.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wilk on 17/3/14 15:52
 * ganweib@gmail.com
 * describe:
 */

public class TuanGouDetail implements Serializable {

    private static final long serialVersionUID = -3556674300363781344L;

    private List<DianPing> Dianping;
    private TuanShop Shop;
    private Tuan Tuan;

    public List<DianPing> getDianping() {
        return Dianping;
    }

    public void setDianping(List<DianPing> dianping) {
        Dianping = dianping;
    }

    public TuanShop getShop() {
        return Shop;
    }

    public void setShop(TuanShop shop) {
        Shop = shop;
    }

    public com.sugood.app.entity.Tuan getTuan() {
        return Tuan;
    }

    public void setTuan(com.sugood.app.entity.Tuan tuan) {
        Tuan = tuan;
    }

    @Override
    public String toString() {
        return "TuanGouDetail{" +
                "Dianping=" + Dianping +
                ", Shop=" + Shop +
                ", Tuan=" + Tuan +
                '}';
    }
}
