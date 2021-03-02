package com.example.test;

public class ItemDisplay {
    private int mQty;
    private String mName;
    private String mDesc;
    private boolean mShowMenu = false;

    public ItemDisplay(boolean showMenu) {

    }

    public boolean isShowMenu(){return mShowMenu;}
    public void setShowMenu(boolean showMenu){mShowMenu = showMenu;};
    public void setQty(int numb){mQty = numb;};
    public void setName(String text){mName = text;}
    public void setDesc(String text){mDesc = text;}
}
