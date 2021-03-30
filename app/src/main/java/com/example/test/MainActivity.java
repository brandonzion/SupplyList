package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    private String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File directory;
        directory = getFilesDir();
        File[] files = directory.listFiles();
        File[] exFileList = searchListData();

        if(mListData.size() > 0){
            load(mListData.get(0));
        }


        //search for any existing files containing list data
        //make a preview for each file
        //preview should show title and a few items
     
        configureAddButton();
        //configureShowButton();
        //configureOpenList();
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

    private void configureShowButton() {
        Button showButton = findViewById(R.id.showButton);
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, GenerateListActivity.class));
            }
        });
    }

    public void configureOpenList() {
        TextView list1 = findViewById(R.id.list1);
        list1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, GenerateListActivity.class));
            }
        });
    }

    private File[] searchListData(){
        filePath = "list0.dat";
        File directory;
        if (filePath.isEmpty()) {
            directory = getFilesDir();
        }
        else {
            directory = getDir(filePath, MODE_PRIVATE);
        }
        File[] files = directory.listFiles();
        return files;
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
}
