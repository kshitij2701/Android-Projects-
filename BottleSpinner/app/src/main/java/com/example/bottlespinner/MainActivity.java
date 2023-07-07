package com.example.bottlespinner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {


    ImageView bottleImage;
    int currentpoint;
    int nextpoint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottleImage = findViewById(R.id.bottle);

        bottleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinTheBottle();

            }
        });
    }
    void spinTheBottle(){

        nextpoint = new Random().nextInt(5000);

        int px = bottleImage.getWidth()/2;
        int py = bottleImage.getHeight()/2;

        Animation rotate = new RotateAnimation(currentpoint,nextpoint,px,py);

        rotate.setDuration(4000);
        rotate.setFillAfter(true);

        currentpoint = nextpoint;

        bottleImage.startAnimation(rotate);
    }
}