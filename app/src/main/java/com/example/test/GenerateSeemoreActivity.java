package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

public class GenerateSeemoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_seemore);



        displaySeemore();
    }

    public void displaySeemore(){
        Intent mIntent = getIntent();
        String mTitle = mIntent.getStringExtra("name");
        String mBrand = mIntent.getStringExtra("brand");

        TextView mTitleView = findViewById(R.id.itemName);
        TextView mPriceView = findViewById(R.id.itemPrice);
        TextView mBrandView = findViewById(R.id.itemBrand);
        TextView mLinkView = findViewById(R.id.itemLink);
        ImageView mImageView = findViewById(R.id.itemImage);
        mTitleView.setText(mTitle);
        mPriceView.setText("test");
        mBrandView.setText(mBrand);
        mLinkView.setText("http://www.google.com");
        mLinkView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}