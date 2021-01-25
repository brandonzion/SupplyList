package com.example.test;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyRecyclerViewHolder>
        implements ItemTouchHelperAdapter {
    private ArrayList<Item> mList;
    private ItemTouchHelper mTouchHelper;



    public class MyRecyclerViewHolder extends RecyclerView.ViewHolder  implements
            View.OnTouchListener,
            GestureDetector.OnGestureListener
    {
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        public CheckBox mCheckBox;
        GestureDetector mGestureDetector;

        View rowView;
        public MyRecyclerViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            mTextView1 = itemView.findViewById(R.id.textView);
            mTextView2 = itemView.findViewById(R.id.textView2);
            mCheckBox = itemView.findViewById(R.id.checkBox);
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
    public MyRecyclerViewAdapter(ArrayList<Item> list) {
        mList = list;
    }
    @Override
    public MyRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        MyRecyclerViewHolder evh = new MyRecyclerViewHolder(v);
        return evh;
    }
    @Override
    public void onBindViewHolder(MyRecyclerViewHolder holder, int position) {
        Item currentItem = mList.get(position);
        holder.mImageView.setImageResource(currentItem.getImageResource());
        holder.mTextView1.setText(currentItem.getText1());
        holder.mTextView2.setText(currentItem.getText2());
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
        mList.remove(position);
        notifyItemRemoved(position);
    }

    public void removeItem(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Item itemNum, int position) {
        mList.add(new Item(R.drawable.ic_android, "Undo Works", "yeet"));
        notifyItemInserted(position);
    }

    public ArrayList<Item> getData() {
        return mList;
    }

    public void setTouchHelper(ItemTouchHelper touchHelper){
        this.mTouchHelper = touchHelper;
    }
}