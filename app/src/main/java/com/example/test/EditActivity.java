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

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {

    private ArrayList<Item> mItemList;
    private int mPosition;
    private Item mItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent mIntent = getIntent();

        //String mTitle = mIntent.getStringExtra("name");
        //String mBrand = mIntent.getStringExtra("brand");
        mItemList = (ArrayList<Item>) mIntent.getSerializableExtra("list");
        mPosition = (int) mIntent.getSerializableExtra("position");
        mItem = mItemList.get(mPosition);
        mItemList = new ArrayList<>();



        displayEditView();
        configureSaveButton();
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

    public void configureSaveButton(){
        EditText mNameView = findViewById(R.id.itemName);
        EditText mQtyView = findViewById(R.id.itemQty);
        EditText mPriceView = findViewById(R.id.itemPrice);
        EditText mBrandView = findViewById(R.id.itemBrand);
        Button mSaveButton = findViewById(R.id.saveButton);

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = mNameView.getText().toString();
                mItem.setName(newName);
                int newQty = Integer.parseInt(mQtyView.getText().toString());
                mItem.setQty(newQty);
                String newDesc = mBrandView.getText().toString();
                mItem.setDesc(newDesc);


                for(int i = 0; i<mItemList.size(); i++) {
                    Item item = mItemList.get(i);
                    mItemList.add(item);
                }
                Intent editToItemIntent = new Intent(EditActivity.this, GenerateListActivity.class);
                editToItemIntent.putExtra("items", mItemList);
                 EditActivity.this.startActivity(editToItemIntent);
            }
        });
    }
}