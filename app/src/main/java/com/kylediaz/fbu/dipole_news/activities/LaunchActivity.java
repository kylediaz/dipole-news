package com.kylediaz.fbu.dipole_news.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.kylediaz.fbu.dipole_news.R;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}