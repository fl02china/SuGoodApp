package com.sugood.app.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/7/31 0031.
 */

public class OrderShangCheng {

    /**
     * orderId : 20170717173024852
     * shopId : 182
     * userId : 852
     * needPay : 539900
     * status : 13
     * tel : 07598666000
     * userTel : 13888888888
     * xm : 余老板
     * shopName : 胜利农场
     * addr : 广东省 广州市 萝岗区 测试的地址
     * createTime : 1500283824
     * expressPrice : 0
     * goods : [{"goodsId":2861,"title":"唐家湾 湛江覃斗鸡蛋芒 5斤/箱","guige":"箱","mallPrice":10800,"price":12800,"orderId":"20170717173024852","shopId":182,"status":0,"photo":"/attachs/2017/06/09/thumb_593a4cfab97d5.jpg","num":2}]
     */

    private String orderId;
    private int shopId;
    private int userId;
    private int needPay;
    private int status;
    private String tel;
    private String userTel;
    private String xm;
    private String shopName;
    private String addr;
    private int createTime;
    private int expressPrice;
    private List<GoodsBean> goods;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getNeedPay() {
        return needPay;
    }

    public void setNeedPay(int needPay) {
        this.needPay = needPay;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }

    public int getExpressPrice() {
        return expressPrice;
    }

    public void setExpressPrice(int expressPrice) {
        this.expressPrice = expressPrice;
    }

    public List<GoodsBean> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsBean> goods) {
        this.goods = goods;
    }

    public static class GoodsBean {
        /**
         * goodsId : 2861
         * title : 唐家湾 湛江覃斗鸡蛋芒 5斤/箱
         * guige : 箱
         * mallPrice : 10800
         * price : 12800
         * orderId : 20170717173024852
         * shopId : 182
         * status : 0
         * photo : /attachs/2017/06/09/thumb_593a4cfab97d5.jpg
         * num : 2
         */

        private int goodsId;
        private String title;
        private String guige;
        private int mallPrice;
        private int price;
        private String orderId;
        private int shopId;
        private int status;
        private String photo;
        private int num;

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getGuige() {
            return guige;
        }

        public void setGuige(String guige) {
            this.guige = guige;
        }

        public int getMallPrice() {
            return mallPrice;
        }

        public void setMallPrice(int mallPrice) {
            this.mallPrice = mallPrice;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public int getShopId() {
            return shopId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}
