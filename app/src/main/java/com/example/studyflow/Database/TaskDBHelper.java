package com.example.studyflow.Database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.studyflow.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

public class TaskDBHelper extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    private static  final String DATABASE_NAME = "TODO_DATABASE";
    private static  final String TABLE_NAME = "TODO_TABLE";
    private static  final String COL_1 = "ID";
    private static  final String COL_2 = "TASK";
    private static  final String COL_3 = "STATUS";
    private static  final String COL_4 = "USERNAME";
    public TaskDBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT , TASK TEXT , STATUS INTEGER, USERNAME TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertTask(ToDoModel model){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2 , model.getTask());
        values.put(COL_3 , 0);
        values.put(COL_4 , model.getUsername());
        db.insert(TABLE_NAME , null , values);
    }

    public void updateTask(int id , String task){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2 , task);
        db.update(TABLE_NAME , values , "ID=?" , new String[]{String.valueOf(id)});
    }

    public void updateStatus(int id , int status){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_3 , status);
        db.update(TABLE_NAME , values , "ID=?" , new String[]{String.valueOf(id)});
    }

    public void deleteTask(int id ){
        db = this.getWritableDatabase();
        db.delete(TABLE_NAME , "ID=?" , new String[]{String.valueOf(id)});
    }

    public List<ToDoModel> getAllTasks(String username){

        db = this.getWritableDatabase();
        Cursor cursor = null;
        List<ToDoModel> modelList = new ArrayList<>();
        db.beginTransaction();
        String selection = COL_4 + "=?";
        String[] selectionArgs = {username};

        try {
            cursor = db.query(TABLE_NAME , null , selection , selectionArgs , null , null , null);
            if (cursor != null && cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex(COL_1);
                int taskIndex = cursor.getColumnIndex(COL_2);
                int statusIndex = cursor.getColumnIndex(COL_3);
                int usernameIndex = cursor.getColumnIndex(COL_4);

                do {
                    ToDoModel task = new ToDoModel();
                    if (idIndex != -1) {
                        task.setId(cursor.getInt(idIndex));
                    }
                    if (taskIndex != -1) {
                        task.setTask(cursor.getString(taskIndex));
                    }
                    if (statusIndex != -1) {
                        task.setStatus(cursor.getInt(statusIndex));
                    }
                    if (statusIndex != -1) {
                        task.setUsername(cursor.getString(usernameIndex));
                    }
                    modelList.add(task);
                } while (cursor.moveToNext());
            }

        }finally {
            db.endTransaction();
            cursor.close();
        }
        return modelList;
    }

}
