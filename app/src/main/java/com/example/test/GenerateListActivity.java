package com.example.test;
import android.content.Intent;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
/*
public class GenerateListActivity extends AppCompatActivity {
    private ArrayList<Item> mList;

    private ArrayList<Integer> qtyList;
    private ArrayList<String> nameList;
    private ArrayList<String> descList;

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
    public void createList() {
        int nItem = 5;

        qtyList = new ArrayList<>(
                Arrays.asList(1, 2, 10, 1, 2)
        );

        nameList = new ArrayList<>(
                Arrays.asList("pencil(s)",
                        "eraser(s)",
                        "notebook(s)",
                        "calculator(s)",
                        "paper")
        );

        descList = new ArrayList<>(
                Arrays.asList("Box(es) of Paper Mate® Woodcase Pencils",
                        "pink, standard erasers",
                        "Mead® Five Star® Spiral Notebook Wide Ruled",
                        "Texas Instruments TI-30XIIS 10-Digit Scientific Calculator",
                        "Package(s) of Mead® Five Star® Reinforced Filler Paper, Wide Ruled, 3 Hole Punched")
        );

        mList = new ArrayList<>();

        // mList = GetItemList()
        //mList.add(new Item(R.drawable.ic_android, "Line 1", "Line 2", false));
        //mList.add(new Item(R.drawable.ic_android, "Line 3", "Line 4", false));
        //mList.add(new Item(R.drawable.ic_android, "Line 5", "Line 6", false));

        // mList[i].GetQty()

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



}*/

//TODO add function to edit button: be able to edit title, desc, qty
//TODO when delete, add to garbage can (be able to recover it)
//TODO fine tune the swipe visual effects
//TODO have Brandon's engine give us link and image
//TODO fix item width: if desc is too long, cut it off
//TODO figure out the limit of characters for desc

