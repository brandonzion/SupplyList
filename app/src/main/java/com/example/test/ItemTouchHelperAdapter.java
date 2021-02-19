package com.example.test;

import java.util.ArrayList;

public interface ItemTouchHelperAdapter {

    void onItemMove(int fromPosition, int toPosition);

    void onItemSwiped(int position);

    void showMenu(int adapterPosition);

    void closeMenu();
}
