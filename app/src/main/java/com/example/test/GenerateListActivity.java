package com.example.test;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.test.R;
import com.google.firebase.ml.vision.text.FirebaseVisionText;

import java.util.ArrayList;
public class GenerateListActivity extends AppCompatActivity {
    private ArrayList<Item> mList;
    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button buttonInsert;
    private Button buttonRemove;
    private EditText editTextInsert;
    private EditText editTextRemove;

    private EditText editTextInsert;
    private EditText editTextRemove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_list);

        createList();
        buildRecyclerView();

        buttonInsert = findViewById(R.id.button_insert);
        buttonRemove= findViewById(R.id.button_remove);
        editTextRemove = findViewById(R.id.edittext_remove);
        editTextInsert = findViewById(R.id.edittext_insert);

        buttonInsert.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int position = Integer.parseInt(editTextInsert.getText().toString());
                insertItem(position);
            }
        });

        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = Integer.parseInt(editTextRemove.getText().toString());
                removeItem(position);
            }
        });
    }

    public void insertItem(int position) {
        mList.add(position, new Item(R.drawable.ic_android, "this is position " + position, "Brandon is a  big poopyhead"));
        mAdapter.notifyItemInserted(position);
    }

    public void removeItem(int position) {
        if(position < mList.size()){
            mList.remove(position);
            mAdapter.notifyItemRemoved(position);
        }
    }

    public void createList() {
        Intent i = getIntent();
        ArrayList<String> textList = i.getStringArrayListExtra("textList");
=======
        createList();
        buildRecyclerView();
    }
    public void insertItem(int position) {
        mList.add(position, new Item(R.drawable.ic_android, "New Item At Position" + position, "This is Line 2"));
        mAdapter.notifyItemInserted(position);
    }
    public void removeItem(int position) {
        mList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }
    public void changeItem(int position, String text) {
        mList.get(position).changeText1(text);
        mAdapter.notifyItemChanged(position);
    }
    public void createList() {
        mList = new ArrayList<>();
        mList.add(new Item(R.drawable.ic_android, "Line 1", "Line 2"));
        mList.add(new Item(R.drawable.ic_android, "Line 3", "Line 4"));
        mList.add(new Item(R.drawable.ic_android, "Line 5", "Line 6"));
    }
    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new Adapter(mList);

        mAdapter = new MyRecyclerViewAdapter(mList);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ItemTouchHelper.Callback callback = new MyItemTouchHelper(mAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        mAdapter.setTouchHelper(itemTouchHelper);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        mRecyclerView.setAdapter(mAdapter);
    }
}