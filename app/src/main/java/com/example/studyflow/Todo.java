package com.example.studyflow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.example.studyflow.Adapter.ToDoAdapter;
import com.example.studyflow.Database.TaskDBHelper;
import com.example.studyflow.Model.ToDoModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Todo extends AppCompatActivity implements OnDialogCloseListner {

    private RecyclerView mRecyclerview;
    private FloatingActionButton fab;
    private TaskDBHelper myDB;
    private List<ToDoModel> mList;
    private List<ToDoModel> displayList;
    private ToDoAdapter adapter;

    private ImageView imgMenu;
    private PopupMenu menu;
    private boolean isMenuOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        ImageView imgTheme = findViewById(R.id.imgTheme);
        mRecyclerview = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.btnAddTask);
        myDB = new TaskDBHelper(Todo.this);
        mList = new ArrayList<>();
        displayList = new ArrayList<>();
        adapter = new ToDoAdapter(myDB, Todo.this);

        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerview.setAdapter(adapter);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        SharedPreferences sharedPreferences = getSharedPreferences("current_user", Context.MODE_PRIVATE);
        String savedUsername = sharedPreferences.getString("username","");
        mList = myDB.getAllTasks(savedUsername);
        Collections.reverse(mList);
        adapter.setTasks(mList);

        imgMenu = findViewById(R.id.imgMenu);
        isMenuOpen = false;

        int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (nightModeFlags == Configuration.UI_MODE_NIGHT_NO) {
            // Night mode is not active
            imgTheme.setBackgroundResource(R.drawable.theme_light);
        } else {
            // Night mode is active
            imgTheme.setBackgroundResource(R.drawable.theme);
        }

        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isMenuOpen)
                    showPopupMenu();
            }
        });

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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new TodoViewTouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(mRecyclerview);
    }


        @Override
        public void onDialogClose(DialogInterface dialogInterface) {
            SharedPreferences sharedPreferences = getSharedPreferences("current_user", Context.MODE_PRIVATE);
            String savedUsername = sharedPreferences.getString("username","");
            mList = myDB.getAllTasks(savedUsername);
            Collections.reverse(mList);
            adapter.setTasks(mList);
            adapter.notifyDataSetChanged();
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
                    case R.id.nav_reminder:
                        Intent reminder = new Intent(getApplicationContext(),Reminder.class);
                        startActivity(reminder);
                        return true;
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