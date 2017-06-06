package com.sugood.app.entity;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Package :com.sugood.app.entity
 * Description :
 * Author :Rc3
 * Created at :2017/04/18 15:20.
 */

public class ShopCarList extends RealmObject {
    private RealmList<ShopCarProduct> mShopCarProducts;

    public RealmList<ShopCarProduct> getShopCarProducts() {
        return mShopCarProducts;
    }

    public void setShopCarProducts(RealmList<ShopCarProduct> shopCarProducts) {
        mShopCarProducts = shopCarProducts;
    }
}
