package com.example.studyflow.Model;

import com.google.firebase.Timestamp;

public class NoteModel {
    public String title;
    public String content;

    public Timestamp timestamp;

    public NoteModel(String title, String content, com.google.firebase.Timestamp timestamp) {
        this.title = title;
        this.content = content;
        this.timestamp = timestamp;
    }

    public NoteModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public com.google.firebase.Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(com.google.firebase.Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
