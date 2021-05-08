package com.example.test;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.provider.ContactsContract;
import android.telephony.mbms.MbmsErrors;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.Serializable;
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
    File[] mFiles;
    File mDirectory;
    private String mSeparator = "@";
    private RecyclerView mRecyclerView;
    private EditText mListTitle;
    private  ArrayList<Item> mItems;
    private MyRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    CoordinatorLayout mCoordinatorLayout;
    private DataManager mDataManager = new DataManager();
    private ItemData mItemData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_list);
        mCoordinatorLayout = findViewById(R.id.coordinatorLayout);
        mListTitle = findViewById(R.id.listTitle);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Intent intent = getIntent();
        String inputFile = intent.getSerializableExtra("currentFile").toString();
        mItems = (ArrayList<Item>) intent.getSerializableExtra("items");
        mItemData = new ItemData("Untitled", mItems);
        File directory;
        directory = getFilesDir();
        mFiles = directory.listFiles();

        if("".equals(inputFile)){
            mFileName = "list" + mFiles.length;
            mDataManager.write(this, mFileName, mItemData);
        }
        else{
            mFileName = inputFile;
        }


        createList();
        buildRecyclerView();


    }
    public void createList() {
        if(mItems.size() == 0){
            mDataManager.read(this, mFileName);
        }
    }
    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new MyRecyclerViewAdapter(this, mItems, mFileName);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ItemTouchHelper.Callback callback = new MyItemTouchHelper(mAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        mAdapter.setTouchHelper(itemTouchHelper);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    protected void onPause() {
        // call the superclass method first
        super.onPause();
        mDataManager.write(this, mFileName, mItemData);
    }

    @Override
    protected void onStop() {
        // call the superclass method first
        super.onStop();
        mDataManager.write(this, mFileName, mItemData);
    }

    @Override
    protected void onResume() {
        // call the superclass method first
        super.onResume();
        mDataManager.read(this, mFileName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menubar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.back:
            //add the function to perform here
            return(true);
        case R.id.about:
            //add the function to perform here
            return(true);
        case R.id.exit:
            //add the function to perform here
            return(true);
    }
        return(super.onOptionsItemSelected(item));
    }

}




