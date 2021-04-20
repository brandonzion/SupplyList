package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintProperties;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> mListData;
    private File[] mFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.ConstraintLayout);
        ConstraintSet set = new ConstraintSet();
        set.clone(layout);
        File directory;
        directory = getFilesDir();
        mFiles = directory.listFiles();
        int  listButtonWidth = 500;
        int listButtonHeight = 550;
        for(int i = 0; i < 6 && i < mFiles.length; i++) {
            String currentFile = mFiles[i].getName();
            Button button = new Button(this);

            // Open file and read title, and list
            FileInputStream fis = null;
            try {
                fis = openFileInput(mFiles[i].getName());
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String text;

                text = br.readLine();

                button.setText(text);

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
                    intent.putExtra("currentFile", currentFile);
                    MainActivity.this.startActivity(intent);
                }
            });

        }


        configureAddButton();
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
                Toast.makeText(this, "Option 1 selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.share:
                Toast.makeText(this, "Option 2 selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }
    private void configureAddButton() {
        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, GeneratePhotoActivity.class));
            }
        });
    }



    public void load(String fileName) {
        FileInputStream fis = null;
        try {
            fis = openFileInput(fileName);
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

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh(){
        for(int i = 0; i < 6 && i < mFiles.length; i++) {
            String currentFile = mFiles[i].getName();
            Button button = findViewById(i + 100);
            // Open file and read title, and list
            FileInputStream fis = null;
            try {
                fis = openFileInput(mFiles[i].getName());
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String text;

                text = br.readLine();

                button.setText(text);

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
}



//TODO add functions to context menu
//TODO delete file and ask for confirmation
//TODO figure out how to share files
//TODO remove tool bar at top of screen
//TODO upload file support format pdf jpg txt
//TODO how to add a test
//TODO share through email, upload to google drive, message
//TODO fix title bug when exit edit activity
//TODO when coming out of edit, close menu view
//TODO how to manage life cycle of activities/views

