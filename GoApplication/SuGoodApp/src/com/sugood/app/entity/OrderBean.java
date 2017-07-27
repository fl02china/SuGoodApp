package com.sugood.app.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/7/26 0026.
 */

public class OrderBean implements Serializable {
    private static final long serialVersionUID = -3385092091785281721L;


    /**
     * orderId : 201705151707607263
     * shopId : 142
     * userId : 263
     * logistics : 0
     * needPay : 1800
     * num : 5
     * status : 0
     * createTime : 1494839236
     * shopName : 双可糖水（原可可甜品）
     * logo : /attachs/2017/02/14/thumb_58a2cfcc846a1.jpg
     * tel : 0759-8806223
     * score : 3
     * waimai : [{"orderId":"201705151707607263","productId":3124,"num":1,"productName":"炼奶龟苓膏","photo":"/attachs/2017/05/14/thumb_5917ac1e94754.jpg","price":400,"shopId":142},{"orderId":"201705151707607263","productId":3123,"num":1,"productName":"芒果龟苓膏","photo":"/attachs/2017/05/14/thumb_5917abe27aee9.jpg","price":500,"shopId":142},{"orderId":"201705151707607263","productId":3122,"num":3,"productName":"蜜糖龟苓膏","photo":"/attachs/2017/07/17/thumb_596c29662123e.jpg","price":300,"shopId":142}]
     */

    private String orderId;
    private int shopId;
    private int userId;
    private int logistics;
    private int needPay;
    private int num;
    private int status;
    private int createTime;
    private String shopName;
    private String logo;
    private String tel;
    private int score;
    private List<WaimaiBean> waimai;

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

    public int getLogistics() {
        return logistics;
    }

    public void setLogistics(int logistics) {
        this.logistics = logistics;
    }

    public int getNeedPay() {
        return needPay;
    }

    public void setNeedPay(int needPay) {
        this.needPay = needPay;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<WaimaiBean> getWaimai() {
        return waimai;
    }

    public void setWaimai(List<WaimaiBean> waimai) {
        this.waimai = waimai;
    }

    @Override
    public String toString() {
        return "OrderBean{" +
                "orderId='" + orderId + '\'' +
                ", shopId=" + shopId +
                ", userId=" + userId +
                ", logistics=" + logistics +
                ", needPay=" + needPay +
                ", num=" + num +
                ", status=" + status +
                ", createTime=" + createTime +
                ", shopName='" + shopName + '\'' +
                ", logo='" + logo + '\'' +
                ", tel='" + tel + '\'' +
                ", score=" + score +
                ", waimai=" + waimai +
                '}';
    }

    public static class WaimaiBean {
        /**
         * orderId : 201705151707607263
         * productId : 3124
         * num : 1
         * productName : 炼奶龟苓膏
         * photo : /attachs/2017/05/14/thumb_5917ac1e94754.jpg
         * price : 400
         * shopId : 142
         */

        private String orderId;
        private int productId;
        private int num;
        private String productName;
        private String photo;
        private int price;
        private int shopId;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getShopId() {
            return shopId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }
    }
}
