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
        setContentView(R.layout.activity_generate_seemore);



        displayEditView();
    }

    public void displayEditView(){
        Intent mIntent = getIntent();
        String mTitle = mIntent.getStringExtra("name");
        String mBrand = mIntent.getStringExtra("brand");

        TextView mTitleView = findViewById(R.id.itemTitle);
        TextView mPriceView = findViewById(R.id.itemPrice);
        EditText mBrandView = findViewById(R.id.itemBrand);
        TextView mLinkView = findViewById(R.id.itemLink);
        ImageView mImageView = findViewById(R.id.itemImage);

        mTitleView.setText("currently in edit mode");
        mPriceView.setText("test");
        //mBrandView.setText(mBrand);
        mLinkView.setText("http://www.google.com");

        mLinkView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}