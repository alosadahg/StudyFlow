package com.example.studyflow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studyflow.Model.NoteModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class NoteDetailsActivity extends AppCompatActivity {

    EditText titleEditText, contentEditText;
    ImageButton saveNoteBtn;
    String userDocumentID;

    TextView pageTitleTextView;
    String title, content, docId;
    boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);


        titleEditText = findViewById(R.id.notes_title_text);
        contentEditText = findViewById(R.id.notes_content_text);
        saveNoteBtn = findViewById(R.id.save_note_btn);
        pageTitleTextView = findViewById(R.id.page_title);

        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        docId = getIntent().getStringExtra("docId");

        if(docId != null && !docId.isEmpty()) {
            isEditMode = true;
        }
        titleEditText.setText(title);
        contentEditText.setText(content);

        if(isEditMode) {
            pageTitleTextView.setText("Edit your note");
        }
        saveNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
    }

    void saveNote() {
        String noteTitle = titleEditText.getText().toString();
        String noteContent = contentEditText.getText().toString();
        if(noteTitle == null || noteTitle.isEmpty()) {
            titleEditText.setError("Title is require");
            return;
        }

        NoteModel note = new NoteModel(noteTitle, noteContent, Timestamp.now());
        saveNoteToFirebase(note);
    }

    void saveNoteToFirebase(NoteModel note) {
        SharedPreferences sharedPreferences = getSharedPreferences("current_user", Context.MODE_PRIVATE);
        userDocumentID = sharedPreferences.getString("userDocumentID", "");
        DocumentReference noteRef;
        if(isEditMode) {
            System.out.println("DocumentID: " + docId + " userDocumentID: " + userDocumentID);
            noteRef = FirebaseFirestore.getInstance().collection("notes")
                    .document(userDocumentID).collection("myNotes").document(docId);
        } else {
            noteRef = FirebaseFirestore.getInstance().collection("notes")
                    .document(userDocumentID).collection("myNotes").document();
        }

        noteRef.set(note)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(NoteDetailsActivity.this, "Note saved successfully.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(NoteDetailsActivity.this, "Failed while saving note.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}