package com.example.test;

import java.util.ArrayList;

public class ItemData {
    private String mTitle;
    private ArrayList<Item> mItems;
    public ItemData(String title, ArrayList<Item> items){
        mTitle = title;
        mItems = items;
    }

    public String getTitle(){
        return mTitle;
    }

    public ArrayList<Item> getItems(){
        return mItems;
    }

    public Item getItem(int pos{
        Item item = mItems.get(pos);
        return item;
    }

    public void setTitle(String title){
        mTitle = title;
    }

    public void setItems(ArrayList<Item> items){
        mItems = items;
    }

    public void editItem(int pos, int qty, String name, String desc){
        Item item = mItems.get(pos);
        item.setQty(qty);
        item.setName(name);
        item.setDesc(desc);
    }

    public void addItem(Item item){
        mItems.add(item);
    }
}
