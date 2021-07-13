package com.example.test;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {SupplyList.class}, version = 1, exportSchema = false)
public abstract class SupplyListRoomDatabase extends RoomDatabase {

    public abstract SupplyListDao supplyListDao();

    private static volatile SupplyListRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    //Below is Singleton
    static SupplyListRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (SupplyListRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SupplyListRoomDatabase.class, "supplyList_database")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}