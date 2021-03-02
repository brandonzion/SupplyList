package com.example.test;

public class Item {
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



    }
