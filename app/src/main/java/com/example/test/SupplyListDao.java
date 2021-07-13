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
public interface SupplyListDao {
    // allowing the insert of the same word multiple times by passing a
    // conflict resolution strategy
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(SupplyList list);

    @Query("DELETE FROM item_table")
    void deleteAll();

    @Query("DELETE FROM item_table WHERE mId = :id")
    void delete(int id);

    @Query("SELECT * FROM item_table ORDER BY listTitle ASC")
    List<Item> getAlphabetizedListNames();

    @Query("SELECT * FROM item_table")
    List<Item> getAll();

    @Query("UPDATE item_table SET listTitle = :title WHERE mId = :id")
    void update(int id, String title);

    @Query("SELECT listTitle FROM item_table WHERE mId = :id")
    String getTitle(int id);

    @Query("SELECT mId FROM item_table")
    ArrayList<Integer> getIdAll();
}