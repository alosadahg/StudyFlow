package com.example.studyflow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imgMenu;
    private PopupMenu menu;
    private boolean isMenuOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        SharedPreferences sharedPreferences = getSharedPreferences("current_user", Context.MODE_PRIVATE);
        String savedUsername = sharedPreferences.getString("username","");
        TextView msgWelcome = (TextView) findViewById(R.id.msgWelcome);

        msgWelcome.setText("Welcome " + savedUsername + "!");

        CardView cardPomodoro = findViewById(R.id.crdPomodoro);
        CardView cardTodo = findViewById(R.id.crdTodo);
        CardView cardQuizzes = findViewById(R.id.crdQuizzes);
        CardView cardNotes = findViewById(R.id.crdNotes);

        cardPomodoro.setOnClickListener(this);
        cardTodo.setOnClickListener(this);
        cardQuizzes.setOnClickListener(this);
        cardNotes.setOnClickListener(this);

        imgMenu = findViewById(R.id.imgMenu);
        ImageView imgTheme = findViewById(R.id.imgTheme);
        isMenuOpen = false;

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

        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isMenuOpen)
                    showPopupMenu();
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.crdPomodoro:
//                Toast.makeText(MainActivity.this,"Pomodoro timer is clicked!",Toast.LENGTH_SHORT).show();
                Intent pomodoro = new Intent(getApplicationContext(), Pomodoro.class);
                startActivity(pomodoro);
                break;
            case R.id.crdTodo:
//                Toast.makeText(MainActivity.this,"To-do list is clicked!",Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPreferences = getSharedPreferences("current_user", Context.MODE_PRIVATE);
                String savedUsername = sharedPreferences.getString("username","");
                Intent todo = new Intent(getApplicationContext(), Todo.class);
                todo.putExtra("name",savedUsername);
                startActivity(todo);
                break;
            case R.id.crdQuizzes:
//                Toast.makeText(MainActivity.this,"Quizzes is clicked!",Toast.LENGTH_SHORT).show();
                Intent quizzes = new Intent(getApplicationContext(), Quizzes.class);
                startActivity(quizzes);
                break;
            case R.id.crdNotes:
                Toast.makeText(MainActivity.this,"Notes is clicked!",Toast.LENGTH_SHORT).show();
                Intent notes = new Intent(getApplicationContext(), Note.class);
//                Toast.makeText(MainActivity.this,"Notes is clicked!",Toast.LENGTH_SHORT).show();

                startActivity(notes);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_drawer, menu);
        return true;
    }
    public void showPopupMenu() {
        menu = new PopupMenu(this, imgMenu);
        menu.inflate(R.menu.nav_drawer);

        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch(menuItem.getItemId()) {
                    case R.id.nav_logout:
                        SharedPreferences sharedPreferences = getSharedPreferences("current_user", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                        Intent login = new Intent(getApplicationContext(),Login.class);
                        startActivity(login);
                        return true;
                    default:
                        return false;
                }
            }
        });

        menu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu popupMenu) {
                isMenuOpen = false;
            }
        });
        menu.show();
        isMenuOpen = true;
    }

    @Override
    public void onBackPressed() {
        if(isMenuOpen) {
            menu.dismiss();
            isMenuOpen = false;
        }
        super.onBackPressed();
    }
}
