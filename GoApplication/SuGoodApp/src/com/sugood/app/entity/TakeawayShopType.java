package com.sugood.app.entity;

import java.util.List;

/**
 * Package :com.sugood.app.entity
 * Description :
 * Author :Rc3
 * Created at :2017/3/31 09:40.
 */

public class TakeawayShopType {

    String typeName;
    List<TakeawayShopTypeInfo> list;
    boolean isSelected;
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<TakeawayShopTypeInfo> getList() {
        return list;
    }

    public void setList(List<TakeawayShopTypeInfo> list) {
        this.list = list;
    }
    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


}
