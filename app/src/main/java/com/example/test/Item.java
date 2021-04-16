package com.example.test;

import android.os.Parcelable;
import android.widget.Button;

import java.io.Serializable;

public class Item implements Serializable{
    private int mQty;
    private String mName;
    private String mDesc;
    Boolean mShowMenu;
    public Item(int qty, String name, String desc) {
        mQty = qty;
        mName = name;
        mDesc = desc;
        mShowMenu = false;
    }

    public void setShowMenu(boolean ifShow){
        mShowMenu = ifShow;
    }

    public Boolean isShowMenu(){
        return mShowMenu;
    }

    public int getQty() {
        return mQty;
    }
    public String getName() {
        return mName;
    }
    public String getDesc() {
        return mDesc;
    }
    public void setQty(int numb){mQty = numb;};
    public void setName(String text){mName = text;}
    public void setDesc(String text){mDesc = text;}


}
