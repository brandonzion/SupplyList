package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
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
    private DataManager mDataManager = new DataManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent mIntent = getIntent();

        mFileName = (String) mIntent.getSerializableExtra("currentFile");
        mItemList = (ArrayList<Item>) mIntent.getSerializableExtra("list");
        mPosition = (int) mIntent.getSerializableExtra("position");
        mItem = mItemList.get(mPosition);

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

        String newName = mNameView.getText().toString();
        mItem.setName(newName);
        int newQty = Integer.parseInt(mQtyView.getText().toString());
        mItem.setQty(newQty);
        String newDesc = mBrandView.getText().toString();
        mItem.setDesc(newDesc);
        mTitle = mDataManager.read(this, mFileName).getTitle();

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menubar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.back:
            save();
            Intent intent = new Intent(EditActivity.this, GenerateListActivity.class);
            ArrayList<Item> placeholderItems = new ArrayList<>();
            intent.putExtra("items", placeholderItems);
            intent.putExtra("currentFile", mFileName);
            EditActivity.this.startActivity(intent);
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