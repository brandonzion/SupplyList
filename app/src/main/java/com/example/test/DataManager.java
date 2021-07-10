package com.example.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.io.File;

import android.content.Context;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

public class DataManager {
    private String mSeparator = "@";
    private ItemData mItemData;

    public ItemData read(Context context, String fileName){
        FileInputStream fis = null;
        String title = new String();
        ArrayList<Item> items = new ArrayList<>();  
        try {
            fis = context.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            String textTitle = br.readLine();
            title = textTitle;
            while ((text = br.readLine()) != null) {
                String[] splited = text.split("@");
                int qty = Integer.parseInt(splited[0]);
                String name = splited[1];
                String desc = splited[2];
                Item item = new Item(qty, name, desc, title);

                items.add(item);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        mItemData = new ItemData(title, items);
        return mItemData;
    }

    public void write(Context context, String fileName, ItemData itemData){     
        FileOutputStream fos = null;
        String title = itemData.getTitle();
        ArrayList<Item> items = itemData.getItems();
        try {
            fos = context.openFileOutput(fileName, MODE_PRIVATE);
            String textTitle = title + "\n";
            fos.write(textTitle.getBytes());
               for(int i = 0; i < items.size(); i++) {
                Item currentItem = items.get(i);
                String textData = currentItem.getQty() + mSeparator + currentItem.getName() + mSeparator + currentItem.getDesc() + "\n";
                fos.write(textData.getBytes());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
