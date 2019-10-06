package com.mobileprogramming.assignment1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class ThirdAcitivity extends AppCompatActivity {
    GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        gridLayout = findViewById(R.id.gridlayout);
        ImageButton imageButton = new ImageButton(this);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.resource);
//        bitmap = Bitmap.createBitmap(bitmap, 0, 0, 39, 69);
        imageButton.setImageBitmap(bitmap);
        imageButton.setBackground(null);
        gridLayout.addView(imageButton);
    }
}
