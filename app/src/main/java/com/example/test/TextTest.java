package com.example.test;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class TextTest extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.texttest);
        Intent intent = getIntent();
        Bundle allExtras = intent.getExtras();
        String allText = (String) allExtras.get("textList");
        ArrayList<Rect> rectangles = (ArrayList<Rect>) allExtras.get("rectangles");
        TextView myTextView = findViewById(R.id.myText);
        myTextView.setText(allText);
        Bitmap originalImage = BitmapFactory.decodeResource(getResources(),R.drawable.supplylistsinglecolumn);
        originalImage = originalImage.copy(Bitmap.Config.RGB_565, true);
        Canvas canvas = new Canvas(originalImage);
        Paint paint = new Paint();
        paint.setAlpha(0xA0); // the transparency
        paint.setColor(Color.RED); // color is red
        paint.setStyle(Paint.Style.STROKE); // stroke or fill or ...
        paint.setStrokeWidth(5); // the stroke width
        for(int i =0;i<rectangles.size();i++){
            canvas.drawRect(rectangles.get(i), paint);
        }
        ImageView imageView = findViewById(R.id.image);
        imageView.setImageBitmap(originalImage);

    }
}