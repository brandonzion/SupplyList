package com.example.test;


public interface ItemTouchHelperAdapter {

    void onItemMove(int fromPosition, int toPosition);

    void showMenu(int adapterPosition);

    void closeMenu();
}
