package com.example.test;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
@Dao
public interface ItemDao {
    // allowing the insert of the same word multiple times by passing a
    // conflict resolution strategy
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Item item);

    @Query("DELETE FROM item_table")
    void deleteAll();

    @Query("DELETE FROM item_table WHERE mId = :id")
    void delete(int id);

    @Query("SELECT * FROM item_table ORDER BY itemName ASC")
    List<Item> getAlphabetizedItemNames();

    @Query("SELECT * FROM item_table WHERE listId = :id")
    List<Item> getAllByListId(Long id);

    @Query("UPDATE item_table SET itemQty = :qty, itemName = :name, itemDesc = :desc WHERE mId = :id")
    void update(int id, int qty, String name, String desc);

    @Insert
    void insertAll(ArrayList<Item> items);

    @Query("DELETE FROM item_table WHERE listId = :listId")
    void deleteAllByListId(int listId);
}
