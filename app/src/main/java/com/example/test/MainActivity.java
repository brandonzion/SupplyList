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
import java.util.List;

import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Integer> mListIds;
    private ConstraintSet mSet = new ConstraintSet();
    private int mListButtonWidth = 500;
    private int mListButtonHeight = 550;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListIds = (ArrayList<Integer>) SupplyListRoomDatabase.getDatabase(getApplicationContext())
                .supplyListDao()
                .getIdAll();
        previewDisplay();
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
                List<Item> listItems = ItemRoomDatabase.getDatabase(getApplicationContext())
                        .itemDao()
                        .getAllByListId((long) (item.getItemId()-100));
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, item.getTitle());
                for(Item currentItem: listItems){
                    String stringQty = Integer.toString(currentItem.getQty());
                    sendIntent.putExtra(Intent.EXTRA_TEXT, stringQty);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, currentItem.getName());
                    sendIntent.putExtra(Intent.EXTRA_TEXT, currentItem.getDesc());
                }
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, "Share list to... ");
                startActivity(shareIntent);
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
        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.ConstraintLayout);
        mSet.clone(layout);
        previewDisplay();
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

    private void previewDisplay(){
        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.ConstraintLayout);
        mSet.clone(layout);
        for(int i = 0; i < 6 && i < mListIds.size(); i++) {
            int buttonId = i+100;
            //if button doesn't exist, create a new one
            if(layout.findViewById(buttonId) == null) {
                int listId = mListIds.get(i);
                Button button = new Button(this);
                // Open database and read title, and list
                String text;
                text = SupplyListRoomDatabase.getDatabase(getApplicationContext())
                        .supplyListDao()
                        .getTitle(listId);
                button.setText(text);

                button.setId(listId + 100);           // <-- Important
                layout.addView(button);
                int row = i / 2;
                int col = i % 2;
                mSet.connect(button.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 24 + (24 + mListButtonHeight) * row);
                mSet.connect(button.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 32 + (24 + mListButtonWidth) * col);
                mSet.constrainHeight(button.getId(), mListButtonHeight);
                mSet.constrainWidth(button.getId(), mListButtonWidth);
                mSet.applyTo(layout);

                //On long click, open popup
                registerForContextMenu(button);

                //On Short Click, open list
                int finalI = i;
                button.setOnClickListener(new View.OnClickListener() {
                    ArrayList<Item> placeholderList = new ArrayList<>();

                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, GenerateListActivity.class);
                        intent.putExtra("items", placeholderList);
                        intent.putExtra("listId", listId);
                        MainActivity.this.startActivity(intent);
                    }
                });
            }
            else{
                //If button exists, replace the text
                int listId = mListIds.get(i);
                Button button = (Button) layout.getViewById(buttonId);
                String text;
                text = SupplyListRoomDatabase.getDatabase(getApplicationContext())
                        .supplyListDao()
                        .getTitle(listId);
                button.setText(text);
            }
        }
    }
}



//TODO share through email, upload to google drive, message
//TODO figure out upload file
//TODO be able to delete preview and list
//UI todo
//TODO app logo and name
//TODO color
//TODO Tutorial for first time users
//TODO use consistent

