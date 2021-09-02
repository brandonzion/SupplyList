package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class UploadFileActivity extends AppCompatActivity {
    public static final String TESS_DATA = "/tessdata";
    private static final String TAG = MainActivity.class.getSimpleName();
    private TessBaseAPI tessBaseAPI;
    private final int IMAGE_PICK_CODE = 1827;
    private ImageView mImageView;
    private final int PERMISSION_CODE = 8127;
    public static final String[] keywords = {"Colored Pencils", "Pencils", "Colored Markers", "Notebook", "Notebooks", "Highlighters", "Pens", "Index Cards", "Sharpener", "Sharpeners", "Folder", "Folders", "Glue Sticks", "Scissors", "Binder", "Binders", "Dividers", "Pencil Pouch", "Earbuds", "Hand Sanitizer",
            "Graph Paper", "Expo Markers", "Sticky Notes", "Crayons", "Sharpies", "Eraser", "Erasers", "Ruler", "Stapler", "Calculator", "Tissues"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_file);
        //check runtime permission
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                //permission not granted, request it
                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                //show popup for runtime permission
                requestPermissions(permissions, PERMISSION_CODE);
            }
            else{
                //permission is already granted
                pickImgFromGallery();
            }
        }
        else{
            //system os is less than marshmellow
            pickImgFromGallery();
        }

    }

    private void pickImgFromGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*mImageView = findViewById(R.id.imageView);
          if(resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){
            mImageView.setImageURI(data.getData());
        }*/
        if(resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){
            /*Uri imageUri = data.getData();
            Bitmap image = null;
            try {
                image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            Bitmap image = BitmapFactory.decodeResource(getResources(),R.drawable.easylist);
            prepareTessData();
            startOCR(image);
            String allText = getText(image);
            String[] textArray = allText.split("\n");
            String title = textArray[0];
            textArray = Arrays.copyOfRange(textArray, 1, textArray.length);
            ArrayList items = new ArrayList();
            int index = 0;
            ArrayList temp = new ArrayList();
            for (int i=0; i<textArray.length; i++) {
                if (!textArray[i].isEmpty()){
                    String[] itemSplitArray = textArray[i].trim().split("\\s+", 2);
                    String confirmedKeyword = new String();
                    for (String keyword : keywords){

                        if (itemSplitArray[1].toLowerCase().contains(keyword.toLowerCase())){
                            confirmedKeyword = keyword;
                            break;
                        }
                        else{
                            confirmedKeyword = "Unknown";
                        }
                    }
                    if (itemSplitArray[0].matches(".*\\d.*")){
                        items.add(new Item(items.size(), Integer.parseInt(itemSplitArray[0]), confirmedKeyword, textArray[i], false, -1));
                    }
                }
            }
            Intent intent = new Intent(UploadFileActivity.this, GenerateListActivity.class);
            intent.putExtra("items",items);
            intent.putExtra("listId", -1);
            UploadFileActivity.this.startActivity(intent);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        switch (requestCode) {
            case PERMISSION_CODE: {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //permission was granted
                    pickImgFromGallery();
                }
                else{
                   //permission was denied
                    Toast.makeText(this, "Pemission denied...!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    private void prepareTessData(){
        try{
            File dir = getExternalFilesDir(TESS_DATA);
            if(!dir.exists()){
                if (!dir.mkdir()) {
                    Toast.makeText(getApplicationContext(), "The folder " + dir.getPath() + "was not created", Toast.LENGTH_SHORT).show();
                }
            }
            String fileList[] = getAssets().list("");
            for(String fileName : fileList){
                String pathToDataFile = dir + "/" + fileName;
                if(!(new File(pathToDataFile)).exists()){
                    InputStream in = getAssets().open(fileName);
                    OutputStream out = new FileOutputStream(pathToDataFile);
                    byte [] buff = new byte[1024];
                    int len ;
                    while(( len = in.read(buff)) > 0){
                        out.write(buff,0,len);
                    }
                    in.close();
                    out.close();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void startOCR(Bitmap image){
        try{
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            options.inSampleSize = 6;
            String result = this.getText(image);
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
    }

    private String getText(Bitmap bitmap){
        try{
            tessBaseAPI = new TessBaseAPI();
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
        String dataPath = getExternalFilesDir("/").getPath() + "/";
        tessBaseAPI.init(dataPath, "eng");
        tessBaseAPI.setImage(bitmap);
        String retStr = "No result";
        try{
            retStr = tessBaseAPI.getUTF8Text();
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
        tessBaseAPI.end();
        return retStr;
    }
}