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
    private ArrayList<Item> mList;
    File mDirectory;
    private String mSeparator = "@";
    private RecyclerView mRecyclerView;
    private EditText mListTitle;
    private  ArrayList<Item> mItems;
    private MyRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    CoordinatorLayout mCoordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_list);
        mCoordinatorLayout = findViewById(R.id.coordinatorLayout);
        mListTitle = findViewById(R.id.listTitle);

        Intent intent = getIntent();
        String inputFile = intent.getSerializableExtra("currentFile").toString();
        mItems = (ArrayList<Item>) intent.getSerializableExtra("items");

        if("".equals(inputFile)){
            mFileName = createFile();
        }
        else{
            mFileName = inputFile;
        }


        createList();
        buildRecyclerView();


    }
    public void createList() {
        mList = new ArrayList<>();
        if(mItems.size() == 0){
            loadItems(mFileName);
        }
        else{
            for(int i = 0; i<mItems.size(); i++) {
                mList.add(mItems.get(i));
            }
        }
    }
    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new MyRecyclerViewAdapter(this, mList, mFileName);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ItemTouchHelper.Callback callback = new MyItemTouchHelper(mAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        mAdapter.setTouchHelper(itemTouchHelper);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        mRecyclerView.setAdapter(mAdapter);
    }

    public String createFile(){
        mDirectory = getFilesDir();
        mFiles = mDirectory.listFiles();
        String fileName = "list" + mFiles.length;
        FileOutputStream fos = null;
        try {

            fos = openFileOutput(fileName, MODE_PRIVATE);
            String textTitle = mListTitle.getText().toString() + "\n";
            fos.write(textTitle.getBytes());
            for(int j = 0; j < mItems.size(); j++) {
                Item currentItem = mItems.get(j);
                String textData = currentItem.getQty() + mSeparator + currentItem.getName() + mSeparator + currentItem.getDesc() + "\n";
                fos.write(textData.getBytes());
            }
            Toast.makeText(this, "Created " + fileName,
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
        return fileName;
    }

    public void save(View v) {
        FileOutputStream fos = null;

        try {

            fos = openFileOutput(mFileName, MODE_PRIVATE);
            String textTitle = mListTitle.getText().toString() + "\n";
            fos.write(textTitle.getBytes());
            for(int i = 0; i < mItems.size(); i++) {
                Item currentItem = mItems.get(i);
                String textData = currentItem.getQty() + mSeparator + currentItem.getName() + mSeparator + currentItem.getDesc() + "\n";
                fos.write(textData.getBytes());
            }
            Toast.makeText(this, "Save successful" ,
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

        Intent intent = new Intent(GenerateListActivity.this, MainActivity.class);
        GenerateListActivity.this.startActivity(intent);
    }
    public void loadItems(String fileName) {
        FileInputStream fis = null;
        try {
            fis = openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            String title = br.readLine();
            mListTitle.setText(title);
            while ((text = br.readLine()) != null) {
                String[] splited = text.split("@");
                int qty = Integer.parseInt(splited[0]);
                String name = splited[1];
                String desc = splited[2];
                Item item = new Item(qty, name, desc);
                mList.add(item);
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


