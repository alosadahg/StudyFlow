package com.example.studyflow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String username = intent.getStringExtra("name");
        TextView msgWelcome = (TextView) findViewById(R.id.msgWelcome);

        msgWelcome.setText("Welcome " + username + "!");

        CardView cardPomodoro = findViewById(R.id.crdPomodoro);
        CardView cardTodo = findViewById(R.id.crdTodo);
        CardView cardQuizzes = findViewById(R.id.crdQuizzes);
        CardView cardFlashcards = findViewById(R.id.crdFlashcards);
        CardView cardNotes = findViewById(R.id.crdNotes);
        CardView cardStudySet = findViewById(R.id.crdStudySet);

        cardPomodoro.setOnClickListener(this);
        cardTodo.setOnClickListener(this);
        cardQuizzes.setOnClickListener(this);
        cardFlashcards.setOnClickListener(this);
        cardNotes.setOnClickListener(this);
        cardStudySet.setOnClickListener(this);


        ImageView imgTheme = findViewById(R.id.imgTheme);

        int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (nightModeFlags == Configuration.UI_MODE_NIGHT_NO) {
            // Night mode is not active
            imgTheme.setBackgroundResource(R.drawable.theme_light);
        } else {
            // Night mode is active
            imgTheme.setBackgroundResource(R.drawable.theme);
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

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.crdPomodoro:
                Intent pomodoroTimer = new Intent(this, Pomodoro.class);
                startActivity(pomodoroTimer);
                Toast.makeText(MainActivity.this,"Pomodoro timer is clicked!",Toast.LENGTH_SHORT).show();
                Intent pomodoro = new Intent(getApplicationContext(), Pomodoro.class);
                startActivity(pomodoro);
                break;
            case R.id.crdTodo:
                Toast.makeText(MainActivity.this,"To-do list is clicked!",Toast.LENGTH_SHORT).show();
                Intent todo = new Intent(getApplicationContext(), Todo.class);
                startActivity(todo);
                break;
            case R.id.crdQuizzes:
                Toast.makeText(MainActivity.this,"Quizzes is clicked!",Toast.LENGTH_SHORT).show();
                break;
            case R.id.crdFlashcards:
                Toast.makeText(MainActivity.this,"Flashcards is clicked!",Toast.LENGTH_SHORT).show();
                break;
            case R.id.crdNotes:
                Toast.makeText(MainActivity.this,"Notes is clicked!",Toast.LENGTH_SHORT).show();
                Intent notes = new Intent(getApplicationContext(), Notes.class);
                startActivity(notes);
                break;
            case R.id.crdStudySet:
                Toast.makeText(MainActivity.this,"Study sets is clicked!",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}