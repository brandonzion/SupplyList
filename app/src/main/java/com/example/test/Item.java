package com.example.test;

import android.os.Parcelable;
import android.widget.Button;

import java.io.Serializable;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "item_table")
public class Item implements Serializable{
    @PrimaryKey(autoGenerate = true)
    private int mId;

    @ColumnInfo(name = "itemName")
    private String mName;

    @ColumnInfo(name = "itemQty")
    private int mQty;

    @ColumnInfo(name = "itemDesc")
    private String mDesc;

    @ColumnInfo(name = "itemShowMenu")
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

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }
}
