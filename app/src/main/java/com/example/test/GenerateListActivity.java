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

import android.telephony.mbms.MbmsErrors;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
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
    private  String mFileName;
    private int fileIndex;
    File[] files;
    private ArrayList<ItemDisplay> mList;

    private RecyclerView mRecyclerView;
    private EditText listTitle;
    private  ArrayList<Item> mItems;
    private MyRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_list);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        listTitle = findViewById(R.id.listTitle);
        createList();
        buildRecyclerView();


        //TODO create a blank file with index name
        //TODO fill it with current data
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

    public void save(View v) {
        //TODO when save, overwrite existing file and close it
        //TODO once saved, go back to home automatically
        File directory;
        directory = getFilesDir();
        files = directory.listFiles();
        fileIndex = files.length;
        mFileName = "list" + fileIndex + ".dat";
        FileOutputStream fos = null;
        try {

            fos = openFileOutput(mFileName, MODE_PRIVATE);
            String textTitle = listTitle.getText().toString() + "\n";
            fos.write(textTitle.getBytes());
            for(int i = 0; i < mItems.size(); i++) {
                Item currentItem = mItems.get(i);
                String textData = currentItem.getQty() + " " + currentItem.getName() + " " + currentItem.getDesc() + "\n";
                fos.write(textData.getBytes());
            }
            Toast.makeText(this, "Saved to " + getFilesDir() + "/" + mFileName,
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
        fileIndex ++;
    }
    public void load(View v) {
        FileInputStream fis = null;
        try {
            fis = openFileInput(mFileName);
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

    public void returnHome(View v) {
        Intent intent = new Intent(GenerateListActivity.this, MainActivity.class);
        GenerateListActivity.this.startActivity(intent);
    }
}


//TODO when delete, add to garbage can (be able to recover it)
//TODO save list on home screen when photo is taken
//TODO display title in preview of home screen
//TODO make home button
//TODO provide option to name save file, otherwise use default


