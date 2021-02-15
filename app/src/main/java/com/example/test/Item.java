package com.example.test;
public class Item {
    private int mImageResource;
    private String mText1;
    private String mText2;
    private boolean mShowMenu = false;
    public Item(int imageResource, String text1, String text2, boolean showMenu) {
        mImageResource = imageResource;
        mShowMenu = showMenu;
        mText1 = text1;
        mText2 = text2;
    }
    public int getImageResource() {
        return mImageResource;
    }
    public String getText1() {
        return mText1;
    }
    public String getText2() {
        return mText2;
    }
    public boolean isShowMenu(){return mShowMenu;}
    public void setShowMenu(boolean showMenu){mShowMenu = showMenu;};
    public void setImage(int imageResource){mImageResource = imageResource;};
    public void setText1(String text){mText1 = text;}
    public void setText2(String text){mText2 = text;}
    }
