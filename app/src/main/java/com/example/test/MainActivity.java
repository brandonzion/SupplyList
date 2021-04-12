package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintProperties;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.os.Bundle;
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
        for(int i = 0; i < 6 && i < mFiles.length; i++){
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

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ArrayList<ItemDisplay> placeHolderList = new ArrayList<>();
                    int i = view.getId() - 100;
                    Intent intent = new Intent(MainActivity.this, GenerateListActivity.class);
                    intent.putExtra("items", placeHolderList);
                    intent.putExtra("currentFile", mFiles[i].getName());
                    MainActivity.this.startActivity(intent);
                }
            });
        }



        //search for any existing files containing list data
        //make a preview for each file
        //preview should show title and a few items
     
        configureAddButton();
        //configureShowButton();
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

    /*private void configureShowButton() {
        Button showButton = findViewById(R.id.showButton);
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, GenerateListActivity.class));
            }
        });
    }*/



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
}

//TODO merge itemdisplay and item
//TODO figure out how to add in title ex.) send seperately or make a new item list that holds items and title
//TODO fix edit save (passing in file name to save)
//TODO clean up code (fix variable names)
//TODO long hold to bring up delete, share, and others if needed

