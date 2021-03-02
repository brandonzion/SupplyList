package com.example.test;

import android.os.Parcelable;
import android.widget.Button;

import java.io.Serializable;

public class Item implements Serializable{
    private int mQty;
    private String mName;
    private String mDesc;
    public Item(int qty, String name, String desc) {
        mQty = qty;
        mName = name;
        mDesc = desc;
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
