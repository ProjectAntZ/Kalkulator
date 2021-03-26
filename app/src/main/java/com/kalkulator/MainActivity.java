package com.kalkulator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button simple = (Button) findViewById(R.id.simpleBtn);
        simple.setOnClickListener(a -> {
            Intent intent = new Intent(this, Simple.class);
            startActivity(intent);
        });

        Button advanced = (Button) findViewById(R.id.advancedBtn);
        advanced.setOnClickListener(a -> {
            Intent intent = new Intent(this, Advanced.class);
            startActivity(intent);
        });

        Button about = (Button) findViewById(R.id.aboutBtn);
        about.setOnClickListener(a -> {
            Intent intent = new Intent(this, About.class);
            startActivity(intent);
        });

        Button exit = (Button) findViewById(R.id.exitBtn);
        exit.setOnClickListener(a -> {
            finish();
            System.exit(0);
        });
    }
}