package com.example.test;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class GenerateListActivity extends AppCompatActivity {
    private static final String MY_FILE_NAME = "listData.txt";

    private ArrayList<ItemDisplay> mList;

    private RecyclerView mRecyclerView;
    private  ArrayList<Item> mItems;
    private MyRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_list);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        createList();
        buildRecyclerView();
    }
    public void createList() {
        Intent mIntent = getIntent();
        mItems = (ArrayList<Item>) mIntent.getSerializableExtra("items");
        mList = new ArrayList<>();
        for(int i = 0; i<mItems.size(); i++) {
            ItemDisplay itemDisplay = new ItemDisplay(mItems.get(i));
            mList.add(itemDisplay);
        }

    }
    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new MyRecyclerViewAdapter(this, mList);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ItemTouchHelper.Callback callback = new MyItemTouchHelper(mAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        mAdapter.setTouchHelper(itemTouchHelper);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        mRecyclerView.setAdapter(mAdapter);
    }

    public void configureSaveButton(View v) {
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(MY_FILE_NAME, MODE_PRIVATE);
            for(int i = 0; i < mItems.size(); i++) {
                Item currentItem = mItems.get(i);
                String text = currentItem.getQty() + " " + currentItem.getName() + " " + currentItem.getDesc() + "\n";
                fos.write(text.getBytes());
            }
            Toast.makeText(this, "Saved to " + getFilesDir() + "/" + MY_FILE_NAME,
                    Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void load(View v) {
        FileInputStream fis = null;
        try {
            fis = openFileInput(MY_FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


//TODO when delete, add to garbage can (be able to recover it)
//TODO save list on home screen when photo is taken
//TODO make home button
//TODO how to save things and load it back

