package com.example.test;

import android.database.Cursor;

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

    @Query("DELETE FROM list_table")
    void deleteAll();

    @Query("DELETE FROM list_table WHERE mId = :id")
    void delete(int id);

    @Query("SELECT * FROM list_table ORDER BY listTitle ASC")
    List<SupplyList> getAlphabetizedListNames();

    @Query("SELECT * FROM list_table")
    List<SupplyList> getAll();

    @Query("UPDATE list_table SET listTitle = :title WHERE mId = :id")
    void update(int id, String title);

    @Query("SELECT listTitle FROM list_table WHERE mId = :id")
    String getTitle(int id);

    @Query("SELECT mId FROM list_table")
    List<Integer> getIdAll();
}