  package com.example.test;
import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import com.example.test.ItemMoveCallback.ItemTouchHelperContract;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements ItemTouchHelperAdapter {
    private ArrayList<Item> mList;
    private ItemTouchHelper mTouchHelper;
    final int SHOW_MENU = 1;
    final int HIDE_MENU = 2;
    Context mContext;



    public class MyRecyclerViewHolder extends RecyclerView.ViewHolder  implements

            View.OnTouchListener,
            GestureDetector.OnGestureListener
    {

        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        GestureDetector mGestureDetector;

        View rowView;
        public MyRecyclerViewHolder(View itemView) {

            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            mTextView1 = itemView.findViewById(R.id.textView);
            mTextView2 = itemView.findViewById(R.id.textView2);
            rowView = itemView;

            mGestureDetector = new GestureDetector(itemView.getContext(), this);

            itemView.setOnTouchListener(this);

        }



        @Override
        public boolean onDown(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {
            mTouchHelper.startDrag(this);
        }

        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return true;
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mGestureDetector.onTouchEvent(motionEvent);
            return true;
        }

    }
    public class MenuViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout mEditButton;
        public LinearLayout mDeleteButton;
        public MenuViewHolder(View view){
            super(view);
            mEditButton = view.findViewById(R.id.editButton);
            mDeleteButton = view.findViewById(R.id.deleteButton);
        }
    }
    public MyRecyclerViewAdapter(Context context, ArrayList<Item> list) {
        mContext = context;
        mList = list;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v;
        if(viewType==SHOW_MENU){
            v= LayoutInflater.from(parent.getContext()).inflate(R.layout.menu, parent, false);
            return new MenuViewHolder(v);
        }else{
            v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            return new MyRecyclerViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MyRecyclerViewHolder){
            Item currentItem = mList.get(position);
            ((MyRecyclerViewHolder)holder).mImageView.setImageResource(currentItem.getImageResource());
            ((MyRecyclerViewHolder)holder).mTextView1.setText(currentItem.getText1());
            ((MyRecyclerViewHolder)holder).mTextView2.setText(currentItem.getText2());
        }

        if(holder instanceof MenuViewHolder){
            ((MenuViewHolder)holder).mDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeItem(position);
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Item fromItem = mList.get(fromPosition);
        mList.remove(fromItem);
        mList.add(toPosition, fromItem);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemSwiped(int position) {

    }

    public void showMenu(int position) {
        for(int i=0; i<mList.size(); i++){
            mList.get(i).setShowMenu(false);
        }
        mList.get(position).setShowMenu(true);
        notifyDataSetChanged();
    }

    public boolean isMenuShown() {
        for(int i=0; i<mList.size(); i++){
            if(mList.get(i).isShowMenu()){
                return true;
            }
        }
        return false;
    }

    public void closeMenu() {
        for(int i=0; i<mList.size(); i++){
            mList.get(i).setShowMenu(false);
        }
        notifyDataSetChanged();
    }



    @Override
    public int getItemViewType(int position) {
        if(mList.get(position).isShowMenu()){
            return SHOW_MENU;
        }else{
            return HIDE_MENU;
        }
    }


    public void removeItem(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }

    public ArrayList<Item> getData() {
        return mList;
    }

    public void setTouchHelper(ItemTouchHelper touchHelper){
        this.mTouchHelper = touchHelper;
    }
}