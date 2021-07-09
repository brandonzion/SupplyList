package com.example.test;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface ItemDao {
    // allowing the insert of the same word multiple times by passing a
    // conflict resolution strategy
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Item item);

    @Query("DELETE FROM item_table")
    void deleteAll();

    @Delete
    void delete(Item item);

    @Query("SELECT * FROM item_table ORDER BY itemName ASC")
    List<Item> getAlphabetizedWords();

    @Query("SELECT * FROM item_table")
    List<Item> getAll();

    @Update
    void update(Item item);
}
