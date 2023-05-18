package com.example.studyflow.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.recyclerview.widget.RecyclerView;

import com.example.studyflow.Model.ToDoModel;
import com.example.studyflow.R;
import com.example.studyflow.Todo;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder>{

    private List<ToDoModel> todoList;
    private Todo todoActivity;
    private FirebaseFirestore firestore;

    public ToDoAdapter(Todo todoActivity, List<ToDoModel> todolist) {
        this.todoList = todolist;
        this.todoActivity = todoActivity;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_task,parent,false);
        firestore = FirebaseFirestore.getInstance();

        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        ToDoModel item = todoList.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(toBoolean(item.getStatus()));
    }

    public int getItemCount() {
        return todoList.size();
    }

    private boolean toBoolean(int status) {
        return status!=0;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox task;

        ViewHolder(View view) {
            super(view);
            task = view.findViewById(R.id.todo_checkbox);
        }
    }


}
