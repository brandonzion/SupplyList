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
import android.widget.ImageView;
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
import java.util.Date;
import java.util.List;
import java.util.Calendar;

  public class GenerateListActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private EditText mListTitle;
    private  ArrayList<Item> mItems;
    private MyRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    CoordinatorLayout mCoordinatorLayout;
    private SupplyList mSupplyList;
    private int mListId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_list);
        mCoordinatorLayout = findViewById(R.id.coordinatorLayout);
        mListTitle = findViewById(R.id.listTitle);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Intent intent = getIntent();
        int listId = (int) intent.getSerializableExtra("listId");
        mItems = (ArrayList<Item>) intent.getSerializableExtra("items");
        //if title = nothing it means a new list needs to be created
        if(listId == -1){
            //create a supply list and save to database
            String defaultTitle = "Untitled";
            mListTitle.setText(defaultTitle);
            Date currentTime = Calendar.getInstance().getTime();
            Long longCurrentTime = DateConverter.fromDate(currentTime);
            mSupplyList = new SupplyList(defaultTitle, longCurrentTime);
            long longListId = SupplyListRoomDatabase.getDatabase(getApplicationContext())
                    .supplyListDao()
                    .insert(mSupplyList);
            mListId = (int) longListId;

            //create items and save to database also link items to list
            for(Item item: mItems){
                item.setListId(mListId);
            }
            ItemRoomDatabase.getDatabase(getApplicationContext())
                .itemDao()
                .insertAll(mItems);
        }
        //if title is passed in, list exists and needs to be retrieved
        else{
            String title = SupplyListRoomDatabase.getDatabase(getApplicationContext())
                    .supplyListDao()
                    .getTitle(listId);
            mListTitle.setText(title);
            mListId = listId;
            getList(listId);
        }



        buildRecyclerView();


    }
    public void getList(int id) {
        Long listId = Long.valueOf(id);
        List<Item> items = ItemRoomDatabase
                .getDatabase(getApplicationContext())
                .itemDao()
                .getAllByListId(listId);
        mItems = (ArrayList<Item>) items;
    }
    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new MyRecyclerViewAdapter(this, mItems, mListId);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ItemTouchHelper.Callback callback = new MyItemTouchHelper(mAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        mAdapter.setTouchHelper(itemTouchHelper);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    protected void onResume() {
        // call the superclass method first
        super.onResume();
        List<Item> items = ItemRoomDatabase.getDatabase(getApplicationContext())
                .itemDao()
                .getAllByListId((long)mListId);
        mItems = (ArrayList<Item>) items;
    }


      @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menumain, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.back:
            SupplyListRoomDatabase.getDatabase(getApplicationContext())
                    .supplyListDao()
                    .update(mListId, mListTitle.getText().toString());
            for(int i = 0; i<mItems.size(); i++) {
                Item currentItem = mItems.get(i);
                ItemRoomDatabase.getDatabase(getApplicationContext())
                        .itemDao()
                        .update(currentItem.getId(), currentItem.getQty(), currentItem.getName(), currentItem.getDesc());

            }
            Intent intent = new Intent(GenerateListActivity.this, MainActivity.class);
            GenerateListActivity.this.startActivity(intent);
            return(true);
        case R.id.add:
            int position = mItems.size();
            insertItem(position);
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

    private void insertItem(int pos){
        Item item = new Item( 1, "Blank", "this item doesn't have a description yet", mListId);

        mItems.add(pos, item);
        long longItemId = ItemRoomDatabase.getDatabase(getApplicationContext())
                .itemDao()
                .insert(item);
        int itemId = (int) longItemId;
        item.setListId(mListId);
        item.setId(itemId);
        Intent listToEditIntent = new Intent(this, EditActivity.class);
        listToEditIntent.putExtra("list", mItems);
        listToEditIntent.putExtra("position", pos);
        listToEditIntent.putExtra("listId", mListId);
        this.startActivity(listToEditIntent);
    }
}




