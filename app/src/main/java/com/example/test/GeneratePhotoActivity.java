package com.example.test;

import androidx.annotation.ArrayRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GeneratePhotoActivity extends AppCompatActivity {
    public static final String TESS_DATA = "/tessdata";
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String DATA_PATH = Environment.getExternalStorageDirectory().toString() + "/Tess";
    private TextView textView;
    private TessBaseAPI tessBaseAPI;
    private Uri outputFileDir;
    private String mCurrentPhotoPath;
    public static final String[] keywords = {"Colored Pencils", "Pencils", "Colored Markers", "Notebook", "Notebooks", "Highlighters", "Pens", "Index Cards", "Sharpener", "Sharpeners", "Folder", "Folders", "Glue Sticks", "Scissors", "Binder", "Binders", "Dividers", "Pencil Pouch", "Earbuds", "Hand Sanitizer",
            "Graph Paper", "Expo Markers", "Sticky Notes", "Crayons", "Sharpies", "Eraser", "Erasers", "Ruler", "Stapler", "Calculator", "Tissues"};
    int CAMERA_PIC_REQUEST = 1337;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_photo);

        configureTakePhotoButton();
        configurePickPhotoButton();
    }

    private void configureTakePhotoButton() {
        Button takePhotoButton = findViewById(R.id.takePhotoButton);
        int CAMERA_PIC_REQUEST = 1337;
        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent,CAMERA_PIC_REQUEST);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        if(requestCode==CAMERA_PIC_REQUEST && resultCode == RESULT_OK){
            //Bitmap image = (Bitmap)data.getExtras().get("data");
            Bitmap image = BitmapFactory.decodeResource(getResources(),R.drawable.supplylistsinglecolumn);
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
                        if (itemSplitArray[1].contains(keyword)){
                            confirmedKeyword = keyword;
                            break;
                        }
                        else{
                            confirmedKeyword = "Unknown";
                        }
                    }
                    if (itemSplitArray[0].matches(".*\\d.*")){
                        items.add(new Item(Integer.parseInt(itemSplitArray[0]), confirmedKeyword, textArray[i]));
                    }
                }
            }
            Intent intent = new Intent(GeneratePhotoActivity.this,TextTest.class);
            intent.putExtra("textList",allText);
            GeneratePhotoActivity.this.startActivity(intent);
        }
        super.onActivityResult(requestCode,resultCode,data);

    }


    private void configurePickPhotoButton() {
        Button pickPhotoButton = findViewById(R.id.pickPhotoButton);
        pickPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
