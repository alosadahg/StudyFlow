package com.example.studyflow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.studyflow.Adapter.ToDoAdapter;
import com.example.studyflow.Model.ToDoModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class Todo extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ToDoAdapter tasksAdapter;

    private FirebaseFirestore firestore;
    private List<ToDoModel> taskList;
    private FloatingActionButton btnAddTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        taskList = new ArrayList<>();

        ImageView imgTheme = findViewById(R.id.imgTheme);
        recyclerView = findViewById(R.id.recyclerView);
        btnAddTask = findViewById(R.id.btnAddTask);
        firestore = FirebaseFirestore.getInstance();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (nightModeFlags == Configuration.UI_MODE_NIGHT_NO) {
            // Night mode is not active
            imgTheme.setBackgroundResource(R.drawable.theme_light);
        } else {
            // Night mode is active
            imgTheme.setBackgroundResource(R.drawable.theme);
        }

        tasksAdapter = new ToDoAdapter(this, taskList);
        recyclerView.setAdapter(tasksAdapter);

//        ToDoModel task = new ToDoModel();
//        task.setTask("Test task");
//        task.setStatus(0);
//        task.setId(1);
//
//        taskList.add(task);
//
//        ToDoModel task1 = new ToDoModel();
//        task1.setTask("Sample task");
//        task1.setStatus(1);
//        task1.setId(2);
//
//        taskList.add(task1);
//
//        tasksAdapter.setTask(taskList);

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

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewTask.newInstance().show(getSupportFragmentManager(),AddNewTask.TAG);
            }
        });
    }
}