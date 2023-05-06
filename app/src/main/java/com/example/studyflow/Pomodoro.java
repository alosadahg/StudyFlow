package com.example.studyflow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Pomodoro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro);

        ImageView imgTheme = findViewById(R.id.imgTheme);
        ImageView catTheme = findViewById(R.id.catTheme);

        int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (nightModeFlags == Configuration.UI_MODE_NIGHT_NO) {
            // Night mode is not active
            catTheme.setBackgroundResource(R.drawable.pomodoro_cat);
//            imgTheme.setBackgroundResource(R.drawable.theme_light);
        } else {
            // Night mode is active
            catTheme.setBackgroundResource(R.drawable.pomodoro_cat_night);
//            imgTheme.setBackgroundResource(R.drawable.theme);
        }
        imgTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                if (nightModeFlags == Configuration.UI_MODE_NIGHT_NO) {
                    // Night mode is not active
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    // Night mode is active
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });
    }
}