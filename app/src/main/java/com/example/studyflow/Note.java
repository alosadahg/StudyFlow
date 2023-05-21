package com.example.studyflow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Timestamp;

public class Note extends AppCompatActivity {
    FloatingActionButton createNotes;
    String userDocumentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        createNotes = findViewById(R.id.createNotes);

        SharedPreferences sharedPreferences = getSharedPreferences("current_user", Context.MODE_PRIVATE);
        userDocumentID = sharedPreferences.getString("userDocumentID", "");
        createNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent noteDetailsIntent  = new Intent(Note.this, NoteDetailsActivity.class);
                noteDetailsIntent.putExtra("userDocumentID", userDocumentID);
                startActivity(noteDetailsIntent);
            }
        });
    }
}