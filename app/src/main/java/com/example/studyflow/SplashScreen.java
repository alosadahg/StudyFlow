package com.example.studyflow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences sharedPreferences = getSharedPreferences("offline_access", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String savedUsername = sharedPreferences.getString("username","");
        String savedPassword = sharedPreferences.getString("password","");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!savedUsername.isEmpty() && !savedPassword.isEmpty()) {
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                } else {
                    startActivity(new Intent(SplashScreen.this, Login.class));
                }
                finish();
            }
        }, 2000);


//        Thread thread = new Thread() {
//            @Override
//            public void run() {
//                try {
//                    sleep(2000);
//                    if(!savedUsername.isEmpty() && !savedPassword.isEmpty()) {
//                        startActivity(new Intent(SplashScreen.this, MainActivity.class));
//                    } else {
//                        startActivity(new Intent(SplashScreen.this, Login.class));
//                    }
//                    finish();
//                } catch (Exception e) {
//
//                }
//            }
//        }; thread.run();
    }
}