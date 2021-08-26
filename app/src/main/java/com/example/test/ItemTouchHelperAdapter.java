package com.example.test;


import androidx.recyclerview.widget.RecyclerView;

public interface ItemTouchHelperAdapter {

    void onItemMove(int fromPosition, int toPosition);

    void showMenu(int adapterPosition);

    void closeMenu();

    void notifyDataSetChanged();
}
