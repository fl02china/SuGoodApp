package com.sugood.app.entity;

/**
 * Package :com.android.supermarket.user.bean
 * Description :
 * Author :Rc3
 * Created at :2017/3/8 15:04.
 */

public class UserOrder {


    String createTime;
    String status;
    String QRCodeAdd;
    String code;
    String type;
    String photo;
    String productId;
    String num;
    String price;
    String shopName;
    String shopId;
    String settlementPrice;
    String productName;
    String orderId;


    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getQRCodeAdd() {
        return QRCodeAdd;
    }

    public void setQRCodeAdd(String QRCodeAdd) {
        this.QRCodeAdd = QRCodeAdd;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getPrice() {
        return Double.parseDouble(price) / 100 + "";
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getSettlementPrice() {
        return settlementPrice;
    }

    public void setSettlementPrice(String settlementPrice) {
        this.settlementPrice = settlementPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "UserOrder{" +
                "createTime='" + createTime + '\'' +
                ", status='" + status + '\'' +
                ", QRCodeAdd='" + QRCodeAdd + '\'' +
                ", code='" + code + '\'' +
                ", type='" + type + '\'' +
                ", photo='" + photo + '\'' +
                ", productId='" + productId + '\'' +
                ", num='" + num + '\'' +
                ", price='" + price + '\'' +
                ", shopName='" + shopName + '\'' +
                ", shopId='" + shopId + '\'' +
                ", settlementPrice='" + settlementPrice + '\'' +
                ", productName='" + productName + '\'' +
                ", orderId='" + orderId + '\'' +
                '}';
    }
}
