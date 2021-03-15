package com.example.test;

import java.io.Serializable;

public class ItemDisplay implements Serializable {

    Item mItem;
    Boolean mShowMenu;

    public ItemDisplay(Item item) {
        mItem = item;
        mShowMenu = false;
    }

    public void setShowMenu(boolean ifShow){
        mShowMenu = ifShow;
    }

    public Boolean isShowMenu(){
        return mShowMenu;
    }

    public int getQty() {
        return mItem.getQty();
    }
    public String getName() {
        return mItem.getName();
    }
    public String getDesc() {
        return mItem.getDesc();
    }
}
