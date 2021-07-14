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
import android.widget.Toast;

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
    private String mListTitle;
    private String mTitle;
    private String mSeparator = "@";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent mIntent = getIntent();

        mListTitle = (String) mIntent.getSerializableExtra("title");
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
        mTitle = ItemRoomDatabase.getDatabase(getApplicationContext())
                .itemDao()
                .getTitle();

        ItemRoomDatabase.getDatabase(getApplicationContext())
                .itemDao()
                .update(mItem.getId(), mItem.getQty(), mItem.getName(), mItem.getDesc());
        Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
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
            intent.putExtra("title", mListTitle);
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