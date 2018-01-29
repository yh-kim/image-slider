package com.pickth.imageslider.example.java;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pickth.imageslider.view.ImageSlider;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList items = new ArrayList<Integer>();
        items.add(R.drawable.a);
        items.add(R.drawable.b);
        items.add(R.drawable.c);

        ImageSlider is = findViewById(R.id.is_main);
        is.addItems(items);
    }
}
