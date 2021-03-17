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

        EditText mNameView = findViewById(R.id.itemName);
        EditText mQtyView = findViewById(R.id.itemQty);
        EditText mPriceView = findViewById(R.id.itemPrice);
        EditText mBrandView = findViewById(R.id.itemBrand);
        TextView mLinkView = findViewById(R.id.itemLink);
        ImageView mImageView = findViewById(R.id.itemImage);
        String stringQty = Integer.toString(mItemDisplay.getQty());

        mNameView.setText(mItemDisplay.getName());
        mQtyView.setText(stringQty);
        mLinkView.setText("http://www.google.com");
        mPriceView.setText("test");
        mBrandView.setText(mItemDisplay.getDesc());

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
                mItemDisplay.mItem.setName(newName);
                int newQty = Integer.parseInt(mQtyView.getText().toString());
                mItemDisplay.mItem.setQty(newQty);
                String newDesc = mBrandView.getText().toString();
                mItemDisplay.mItem.setDesc(newDesc);


                for(int i = 0; i<mItemDisplayList.size(); i++) {
                    Item item = mItemDisplayList.get(i).mItem;
                    mItemList.add(item);
                }
                Intent editToItemIntent = new Intent(EditActivity.this, GenerateListActivity.class);
                editToItemIntent.putExtra("items", mItemList);
                 EditActivity.this.startActivity(editToItemIntent);
            }
        });
    }
}