package com.example.test;

import androidx.annotation.ArrayRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextDetector;

import java.util.ArrayList;
import java.util.List;

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

        if(requestCode==CAMERA_PIC_REQUEST && resultCode == RESULT_OK){
            //Bitmap image = (Bitmap)data.getExtras().get("data");
            Bitmap image = BitmapFactory.decodeResource(getResources(),R.drawable.supplylistsinglecolumn);
            FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(image);
            FirebaseVisionTextDetector firebaseVisionTextDetector = FirebaseVision.getInstance().getVisionTextDetector();
            firebaseVisionTextDetector.detectInImage(firebaseVisionImage).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                @Override
                public void onSuccess(FirebaseVisionText firebaseVisionText) {
                    ArrayList<String> lines = new ArrayList<String>();
                    ArrayList<Rect> rectangles = new ArrayList<Rect>();
                    String allText = new String();
                    for (FirebaseVisionText.Block block : firebaseVisionText.getBlocks()){
                        allText += block.getText();
                        allText += '\n';
                        rectangles.add(block.getBoundingBox());
                    }
                    Intent intent = new Intent(GeneratePhotoActivity.this,TextTest.class);
                    intent.putExtra("textList",allText);
                    intent.putExtra("rectangles",rectangles);
                    GeneratePhotoActivity.this.startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(GeneratePhotoActivity.this,"Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("Error: ", e.getMessage());
                }
            });
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