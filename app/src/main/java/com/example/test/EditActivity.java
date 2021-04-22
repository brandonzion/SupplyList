package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {

    private ArrayList<Item> mItemList = new ArrayList<>();;
    private int mPosition;
    private Item mItem;
    private String mFileName;
    private String mTitle;
    private String mSeparator = "@";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent mIntent = getIntent();

        //String mTitle = mIntent.getStringExtra("name");
        //String mBrand = mIntent.getStringExtra("brand");
        mFileName = (String) mIntent.getSerializableExtra("currentFile");
        mItemList = (ArrayList<Item>) mIntent.getSerializableExtra("list");
        mPosition = (int) mIntent.getSerializableExtra("position");
        mItem = mItemList.get(mPosition);

        FileInputStream fis = null;
        try {
            fis = openFileInput(mFileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String title = br.readLine();
            mTitle = title;
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


        displayEditView();
    }

    public void displayEditView(){

        EditText mNameView = findViewById(R.id.itemName);
        EditText mQtyView = findViewById(R.id.itemQty);
        EditText mPriceView = findViewById(R.id.itemPrice);
        EditText mBrandView = findViewById(R.id.itemBrand);
        TextView mLinkView = findViewById(R.id.itemLink);
        ImageView mImageView = findViewById(R.id.itemImage);
        String stringQty = Integer.toString(mItem.getQty());

        mNameView.setText(mItem.getName());
        mQtyView.setText(stringQty);
        mLinkView.setText("http://www.google.com");
        mPriceView.setText("test");
        mBrandView.setText(mItem.getDesc());

        mLinkView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void save(){
        EditText mNameView = findViewById(R.id.itemName);
        EditText mQtyView = findViewById(R.id.itemQty);
        EditText mPriceView = findViewById(R.id.itemPrice);
        EditText mBrandView = findViewById(R.id.itemBrand);

        ArrayList<Item> items = new ArrayList<>();

        String newName = mNameView.getText().toString();
        mItem.setName(newName);
        int newQty = Integer.parseInt(mQtyView.getText().toString());
        mItem.setQty(newQty);
        String newDesc = mBrandView.getText().toString();
        mItem.setDesc(newDesc);


        FileOutputStream fos = null;

        try {

            fos = openFileOutput(mFileName, MODE_PRIVATE);
            String textTitle = mTitle + "\n";
            fos.write(textTitle.getBytes());
            for(int i = 0; i < mItemList.size(); i++) {
                Item currentItem = mItemList.get(i);
                String textData = currentItem.getQty() + mSeparator + currentItem.getName() + mSeparator + currentItem.getDesc() + "\n";
                fos.write(textData.getBytes());
                currentItem.setShowMenu(false);
            }
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
    }

    @Override
    protected void onPause() {
        // call the superclass method first
        super.onPause();
        save();
    }
    @Override
    protected void onStop() {
        // call the superclass method first
        super.onStop();
        save();
    }

}