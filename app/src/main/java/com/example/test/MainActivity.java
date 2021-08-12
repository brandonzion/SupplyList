    package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.View;
import android.view.ViewManager;
import android.widget.AdapterView;
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
    private final int SHARE_BTN = 1212330;
    private final int DELETE_BTN = 9128123;
    private int tutNumb = 0;
    private ConstraintLayout mConLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mConLayout = findViewById(R.id.ConstraintLayout);
        previewDisplay();
        if(tutNumb == 0){
            displayTutorial();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(v.getId(), DELETE_BTN, Menu.NONE, "Delete");
        menu.add(v.getId(), SHARE_BTN, Menu.NONE, "Share");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int listBtnId = item.getGroupId(); //use group id to identify which preview is long clicked
        int listId = listBtnId - 100;
        switch (item.getItemId()) {
            case DELETE_BTN:
                Button listBtn = mConLayout.findViewById(listBtnId);
                ItemRoomDatabase.getDatabase(getApplicationContext())
                        .itemDao()
                        .deleteAllByListId(listId);
                SupplyListRoomDatabase.getDatabase(getApplicationContext())
                        .supplyListDao()
                        .delete(listId);
                mConLayout.removeView(listBtn);
                refresh();
                Toast.makeText(this, "Delete Successful", Toast.LENGTH_SHORT).show();
                return true;
            case SHARE_BTN:
                String textTBS; //TBS stands for "to be sent"
                String currentListTitle = SupplyListRoomDatabase.getDatabase(getApplicationContext())
                        .supplyListDao()
                        .getTitle(listId);
                List<Item> listItems = ItemRoomDatabase.getDatabase(getApplicationContext())
                        .itemDao()
                        .getAllByListId((long) (listId));
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                textTBS = currentListTitle + "\n";
                for(Item currentItem: listItems){
                    String stringQty = Integer.toString(currentItem.getQty());
                    textTBS += stringQty + " " + currentItem.getName() + " " + currentItem.getDesc() + "\n";
                }
                sendIntent.putExtra(Intent.EXTRA_TEXT, textTBS);
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
        previewDisplay();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menubaronlyadd, menu);
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
        int themeYellowValue = Color.parseColor("#FFAC00");
        int themeGrayValue = Color.parseColor("#2D2D2D");
        mListIds = (ArrayList<Integer>) SupplyListRoomDatabase.getDatabase(getApplicationContext())
                .supplyListDao()
                .getIdAll();
        mSet.clone(mConLayout);
        if(mListIds.size() != 0){
            for(int i = 0; i < 6 && i < mListIds.size(); i++) {
                int buttonId = i+100;
                //if button doesn't exist, create a new one
                if(mConLayout.findViewById(buttonId) == null) {
                    int listId = mListIds.get(i);
                    Button button = new Button(this);
                    // Open database and read title, and list
                    String text;
                    text = SupplyListRoomDatabase.getDatabase(getApplicationContext())
                            .supplyListDao()
                            .getTitle(listId);
                    button.setText(text);
                    button.setBackgroundColor(themeYellowValue);
                    button.setTextColor(themeGrayValue);

                    button.setId(listId + 100);           // <-- Important
                    mConLayout.addView(button);
                    int row = i / 2;
                    int col = i % 2;
                    mSet.connect(button.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 24 + (24 + mListButtonHeight) * row);
                    mSet.connect(button.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 32 + (24 + mListButtonWidth) * col);
                    mSet.constrainHeight(button.getId(), mListButtonHeight);
                    mSet.constrainWidth(button.getId(), mListButtonWidth);
                    mSet.applyTo(mConLayout);

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
                    Button button = (Button) mConLayout.getViewById(buttonId);
                    String text;
                    text = SupplyListRoomDatabase.getDatabase(getApplicationContext())
                            .supplyListDao()
                            .getTitle(listId);
                    button.setText(text);
                }
            }
        }
        else if(mListIds.size() == 0){
            mConLayout.removeAllViewsInLayout();
            int a = 9;
        }
    }

    private void displayTutorial(){

    }
}



//UI todo
//TODO app name
//TODO Tutorial for first time user
//TODO find the popular font for buttons and for text
//TODO make the preview text bigger
//TODO add menu bar to generate photo activity
//TODO change the app logo and name when figured out
//TODO change menu bar text and button color
//TODO fix upload file back button
//TODO figure out whether camera buttons are customizable
//TODO have photo to text add amazon links and prices as well
//TODO when checked, change whole item to light gray
//TODO (optional) move to bottom of recycler view when checked
//TODO save check box status to database with other item attributes
//TODO figure out menu border settings
//TODO make menu same size as item
//TODO make menu have same margin as item

