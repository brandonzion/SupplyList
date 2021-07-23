package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class GenerateSeemoreActivity extends AppCompatActivity {
    ArrayList<Item> mItems;
    int mPosition;
    int mListId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_seemore);

        Intent mIntent = getIntent();
        mItems = (ArrayList<Item>) mIntent.getSerializableExtra("list");
        mPosition = (int) mIntent.getSerializableExtra("position");
        mListId = (int) mIntent.getSerializableExtra("listId");

        displaySeemore();
    }

    public void displaySeemore(){
        Item currentItem = mItems.get(mPosition);
        String listTitle = SupplyListRoomDatabase.getDatabase(getApplicationContext())
                .supplyListDao()
                .getTitle(mListId);

        TextView mNameView = findViewById(R.id.itemName);
        TextView mPriceView = findViewById(R.id.itemPrice);
        TextView mDescView = findViewById(R.id.itemBrand);
        TextView mLinkView = findViewById(R.id.itemLink);
        ImageView mImageView = findViewById(R.id.itemImage);
        mNameView.setText(currentItem.getName());
        mPriceView.setText("test");
        mDescView.setText(currentItem.getDesc());
        mLinkView.setText("http://www.google.com");
        mLinkView.setMovementMethod(LinkMovementMethod.getInstance());
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
            ArrayList<Item> placeholderItems = new ArrayList<>();
            Intent intent = new Intent(this, GenerateListActivity.class);
            intent.putExtra("listId", mListId);
            intent.putExtra("items", placeholderItems);
            this.startActivity(intent);
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