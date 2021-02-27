package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.LinkMovementMethod;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);



        displayEditView();
    }

    public void displayEditView(){
        Intent mIntent = getIntent();
        String mTitle = mIntent.getStringExtra("name");
        String mBrand = mIntent.getStringExtra("brand");

        EditText mTitleView = findViewById(R.id.itemTitle);
        EditText mPriceView = findViewById(R.id.itemPrice);
        EditText mBrandView = findViewById(R.id.itemBrand);
        TextView mLinkView = findViewById(R.id.itemLink);
        ImageView mImageView = findViewById(R.id.itemImage);

        mTitleView.setText(mTitle);
        mLinkView.setText("http://www.google.com");
        mPriceView.setText("test");
        mBrandView.setText(mBrand);

        mLinkView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}