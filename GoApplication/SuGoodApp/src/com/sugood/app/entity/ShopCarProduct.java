package com.sugood.app.entity;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Package :com.sugood.app.entity
 * Description :
 * Author :Rc3
 * Created at :2017/3/31 17:26.
 */

public class ShopCarProduct extends RealmObject implements Serializable {

    private static final long serialVersionUID = -5813401037060212230L;
    String foodName;
    int foodAmount;
    String foodPrice;
    String productId;

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getFoodAmount() {
        return foodAmount;
    }

    public void setFoodAmount(int foodAmount) {
        this.foodAmount = foodAmount;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "ShopCarProduct{" +
                "foodName='" + foodName + '\'' +
                ", foodAmount=" + foodAmount +
                ", foodPrice='" + foodPrice + '\'' +
                ", productId='" + productId + '\'' +
                '}';
    }
}
