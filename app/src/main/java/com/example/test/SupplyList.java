package com.example.test;

import android.os.Parcelable;
import android.widget.Button;

import java.io.Serializable;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "list_table")
public class SupplyList {
    @PrimaryKey(autoGenerate = true)
    private int mId;

    @ColumnInfo(name = "listTitle")
    private String mListTitle;

    @ColumnInfo(name = "createdAt")
    private int mCreatedAt;

    public SupplyList(String listTitle, int createdAt){
        mListTitle = listTitle;
        mCreatedAt = createdAt;
    }

    public int getId(){
        return mId;
    }
    public String getListTitle(){
        return mListTitle;
    }
    public int getCreatedAt(){
        return mCreatedAt;
    }
    public void setId(int id){
        mId = id;
    }
    public void setListTitle(String listTitle){
        mListTitle = listTitle;
    }
    public void setCreatedAt(int createdAt){
        mCreatedAt = createdAt;
    }
}
