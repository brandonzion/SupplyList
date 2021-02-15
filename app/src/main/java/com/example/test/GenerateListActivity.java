package com.example.test;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
public class GenerateListActivity extends AppCompatActivity {
    private ArrayList<Item> mList;
    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_list);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        createList();
        buildRecyclerView();
    }
    public void insertItem(int position) {
        mList.add(position, new Item(R.drawable.ic_android, "New Item At Position" + position, "This is Line 2", false));
        mAdapter.notifyItemInserted(position);
    }
    public void createList() {
        mList = new ArrayList<>();
        mList.add(new Item(R.drawable.ic_android, "Line 1", "Line 2", false));
        mList.add(new Item(R.drawable.ic_android, "Line 3", "Line 4", false));
        mList.add(new Item(R.drawable.ic_android, "Line 5", "Line 6", false));
    }
    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new MyRecyclerViewAdapter(this, mList);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ItemTouchHelper.Callback callback = new MyItemTouchHelper(mAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        mAdapter.setTouchHelper(itemTouchHelper);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onBackPressed() {
        if (mAdapter.isMenuShown()) {
            mAdapter.closeMenu();
        } else {
            super.onBackPressed();
        }
    }
}

//TODO add function to buttons
//TODO have a way to recover original item
//TODO when delete, add to garbage can (be able to recover it)
//TODO fine tune the swipe visual effects
//TODO get rid of bug when swipe twice
