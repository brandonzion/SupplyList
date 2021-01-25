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
        enableSwipeToDeleteAndUndo();
    }
    public void insertItem(int position) {
        mList.add(position, new Item(R.drawable.ic_android, "New Item At Position" + position, "This is Line 2"));
        mAdapter.notifyItemInserted(position);
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
        mAdapter = new MyRecyclerViewAdapter(mList);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ItemTouchHelper.Callback callback = new MyItemTouchHelper(mAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        mAdapter.setTouchHelper(itemTouchHelper);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        mRecyclerView.setAdapter(mAdapter);
    }

    private void enableSwipeToDeleteAndUndo() {
        MyItemTouchHelper swipeToDeleteCallback = new MyItemTouchHelper(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                final int position = viewHolder.getAdapterPosition();
                final Item item = mAdapter.getData().get(position);

                mAdapter.removeItem(position);


                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mAdapter.restoreItem(item, position);
                        mRecyclerView.scrollToPosition(position);
                    }
                });

                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(mRecyclerView);
    }

}

//TODO: add quantity and item description and check: Done
//TODO add a delete button when swiped: Not Done
//TODO add undo/recover button: Not Done
