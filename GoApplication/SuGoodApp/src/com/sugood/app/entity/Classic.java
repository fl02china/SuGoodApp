package com.sugood.app.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wilk on 17/4/17 09:29
 * ganweib@gmail.com
 * describe:
 */

public class Classic implements Serializable {
    private static final long serialVersionUID = 5659718684299972209L;

    private List<MeiShiLeiXing> MeiShiLeiXing;
    private List<ShangQuan> ShangQuan;

    public List<com.sugood.app.entity.MeiShiLeiXing> getMeiShiLeiXing() {
        return MeiShiLeiXing;
    }

    public void setMeiShiLeiXing(List<com.sugood.app.entity.MeiShiLeiXing> meiShiLeiXing) {
        MeiShiLeiXing = meiShiLeiXing;
    }

    public List<com.sugood.app.entity.ShangQuan> getShangQuan() {
        return ShangQuan;
    }

    public void setShangQuan(List<com.sugood.app.entity.ShangQuan> shangQuan) {
        ShangQuan = shangQuan;
    }

    @Override
    public String toString() {
        return "Classic{" +
                "MeiShiLeiXing=" + MeiShiLeiXing +
                ", ShangQuan=" + ShangQuan +
                '}';
    }
}
