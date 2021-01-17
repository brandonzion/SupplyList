package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class GeneratePhotoActivity extends AppCompatActivity {
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

        if(requestCode==CAMERA_PIC_REQUEST){
            Bitmap thumbnail = (Bitmap)data.getExtras().get("data");
            Drawable drawable = new BitmapDrawable(getResources(), thumbnail);

        }
        else{
            Toast.makeText(GeneratePhotoActivity.this,"Picture not taken", Toast.LENGTH_LONG);
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
}