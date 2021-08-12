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

    @ColumnInfo(name = "listId")
    private long mListId;

    @ColumnInfo(name = "itemName")
    private String mName;

    @ColumnInfo(name = "itemQty")
    private int mQty;

    @ColumnInfo(name = "itemDesc")
    private String mDesc;

    @ColumnInfo(name = "ItemisChecked")
    private boolean mIsChecked;

    Boolean mShowMenu;
    public Item(int qty, String name, String desc, boolean isChecked, long listId) {
        mListId = listId;
        mQty = qty;
        mName = name;
        mDesc = desc;
        mShowMenu = false;
        mIsChecked = isChecked;
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
    public boolean getIsChecked(){ return mIsChecked; }
    public long getListId(){return mListId;}
    public void setQty(int numb){mQty = numb;};
    public void setName(String text){mName = text;}
    public void setDesc(String text){mDesc = text;}
    public void setIsChecked(boolean isChecked){ mIsChecked = isChecked; }
    public void setListId(long id){mListId = id;}


    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }
}
