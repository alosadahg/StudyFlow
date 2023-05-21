package com.example.studyflow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

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
    private ToDoAdapter adapter;
    SharedPreferences sharedPreferences = getSharedPreferences("current_user", Context.MODE_PRIVATE);
    String savedUsername = sharedPreferences.getString("username", "");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        ImageView imgTheme = findViewById(R.id.imgTheme);
        mRecyclerview = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.btnAddTask);
        myDB = new TaskDBHelper(Todo.this);
        mList = new ArrayList<>();
        adapter = new ToDoAdapter(myDB, Todo.this);

        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerview.setAdapter(adapter);

        mList = myDB.getAllTasks(savedUsername);
        Collections.reverse(mList);
        adapter.setTasks(mList);

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
            mList = myDB.getAllTasks(savedUsername);
            Collections.reverse(mList);
            adapter.setTasks(mList);
            adapter.notifyDataSetChanged();
        }
}