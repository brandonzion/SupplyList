package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> mListData;
    private int mListAmount;
    private ItemData mItemData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.ConstraintLayout);
        ConstraintSet set = new ConstraintSet();
        set.clone(layout);
        File directory;
        directory = getFilesDir();
        int  listButtonWidth = 500;
        int listButtonHeight = 550;
        mListAmount = SupplyListRoomDatabase.getDatabase(getApplicationContext())
                .supplyListDao()
                .getTitle();
        for(int i = 0; i < 6 && i < mListAmount; i++) {
            Button button = new Button(this);

            // Open database and read title, and list
           String textTitle = SupplyListRoomDatabase.getDatabase(getApplicationContext())
                   .supplyListDao()
                   .getTitle();
           button.setText(textTitle);

            button.setId(i + 100);           // <-- Important
            layout.addView(button);
            int row = i/2;
            int col = i%2;
            set.connect(button.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 24 + (24+ listButtonHeight) * row);
            set.connect(button.getId(),ConstraintSet.LEFT,ConstraintSet.PARENT_ID,ConstraintSet.LEFT,32 + (24 + listButtonWidth) * col);
            set.constrainHeight(button.getId(), listButtonHeight);
            set.constrainWidth(button.getId(), listButtonWidth);
            set.applyTo(layout);

            //On long click, open popup
            registerForContextMenu(button);

            //On Short Click, open list
            button.setOnClickListener(new View.OnClickListener(){
                ArrayList<Item> placeholderList = new ArrayList<>();

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, GenerateListActivity.class);
                    intent.putExtra("items", placeholderList);
                    intent.putExtra("title", textTitle);
                    MainActivity.this.startActivity(intent);
                }
            });

        }


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.popupmenu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                //TODO delete button and delete row from database
                refresh();
                Toast.makeText(this, "Delete Successful", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.share:
                Toast.makeText(this, "Option 2 selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh(){
        for(int i = 0; i < 6 && i < mListAmount; i++) {
            Button button = findViewById(i + 100);
            // Open database and read title, and list
            //TODO add list title database here
            String text;
            button.setText(text);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menumain, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.add:
            startActivity(new Intent(MainActivity.this, GeneratePhotoActivity.class));
            return(true);
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



//TODO share through email, upload to google drive, message
//TODO change app to database
//TODO add an ok button for GenerateListActivity and EditActivity
//TODO connect data to database 

