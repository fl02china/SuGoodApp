package com.sugood.app.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wilk on 17/3/7 15:25
 * ganweib@gmail.com
 * describe:
 */

public class Shop implements Serializable {

    private static final long serialVersionUID = -5052305860059285652L;

    private YouHui YouHui;

    private List<TuanGou> BaoTuan;

    private ShopDetail baoshop;

    private List<DianPing> dianping;

    public com.sugood.app.entity.YouHui getYouHui() {
        return YouHui;
    }

    public void setYouHui(com.sugood.app.entity.YouHui youHui) {
        YouHui = youHui;
    }

    public List<TuanGou> getBaoTuan() {
        return BaoTuan;
    }

    public void setBaoTuan(List<TuanGou> baoTuan) {
        BaoTuan = baoTuan;
    }

    public ShopDetail getBaoshop() {
        return baoshop;
    }

    public void setBaoshop(ShopDetail baoshop) {
        this.baoshop = baoshop;
    }

    public List<DianPing> getDianping() {
        return dianping;
    }

    public void setDianping(List<DianPing> dianping) {
        this.dianping = dianping;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "YouHui=" + YouHui +
                ", BaoTuan=" + BaoTuan +
                ", baoshop=" + baoshop +
                ", dianping=" + dianping +
                '}';
    }
}
