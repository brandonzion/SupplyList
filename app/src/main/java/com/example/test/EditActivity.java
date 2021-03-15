package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {

    private ArrayList<ItemDisplay> mItemDisplayList;
    private ArrayList<Item> mItemList;
    private int mPosition;
    private ItemDisplay mItemDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent mIntent = getIntent();

        //String mTitle = mIntent.getStringExtra("name");
        //String mBrand = mIntent.getStringExtra("brand");
        mItemDisplayList = (ArrayList<ItemDisplay>) mIntent.getSerializableExtra("list");
        mPosition = (int) mIntent.getSerializableExtra("position");
        mItemDisplay = mItemDisplayList.get(mPosition);
        mItemList = new ArrayList<>();



        displayEditView();
        configureSaveButton();
    }

    public void displayEditView(){

        EditText mTitleView = findViewById(R.id.itemTitle);
        EditText mPriceView = findViewById(R.id.itemPrice);
        EditText mBrandView = findViewById(R.id.itemBrand);
        TextView mLinkView = findViewById(R.id.itemLink);
        ImageView mImageView = findViewById(R.id.itemImage);

        mTitleView.setText(mItemDisplay.getQty() + " " + mItemDisplay.getName());
        mLinkView.setText("http://www.google.com");
        mPriceView.setText("test");
        mBrandView.setText(mItemDisplay.getDesc());

        mLinkView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void configureSaveButton(){
        EditText mTitleView = findViewById(R.id.itemTitle);
        EditText mPriceView = findViewById(R.id.itemPrice);
        EditText mBrandView = findViewById(R.id.itemBrand);
        Button mSaveButton = findViewById(R.id.saveButton);


        for(int i = 0; i<mItemDisplayList.size(); i++) {
            Item item = mItemDisplayList.get(i).mItem;
            mItemList.add(item);
        }

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editToItemIntent = new Intent(EditActivity.this, GenerateListActivity.class);
                editToItemIntent.putExtra("items", mItemList);
                 EditActivity.this.startActivity(editToItemIntent);
            }
        });
    }
}