
package com.example.test;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "item_table")
public class ItemEntity {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "itemEntity")

    private Item mItem;

    public ItemEntity(@NonNull Item item) {this.mItem = item;}

    public Item getWord(){return this.mItem;}
}
