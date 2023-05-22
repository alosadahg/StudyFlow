package com.example.studyflow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.example.studyflow.Adapter.NoteAdapter;
import com.example.studyflow.Model.NoteModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.sql.Timestamp;

public class Note extends AppCompatActivity {
    FloatingActionButton createNotes;
    String userDocumentID;

    private ImageView imgMenu;
    private PopupMenu menu;
    private boolean isMenuOpen;
    RecyclerView recyclerView;
    NoteAdapter noteAdapter;

    StaggeredGridLayoutManager staggeredGridLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        createNotes = findViewById(R.id.createNotes);

        imgMenu = findViewById(R.id.imgMenu);
        ImageView imgTheme = findViewById(R.id.imgTheme);
        recyclerView = findViewById(R.id.recycler_view);
        isMenuOpen = false;
        setupRecyclerView();
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
        createNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent noteDetailsIntent  = new Intent(Note.this, NoteDetailsActivity.class);
                noteDetailsIntent.putExtra("userDocumentID", userDocumentID);
                startActivity(noteDetailsIntent);
            }
        });
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

    void setupRecyclerView() {
        SharedPreferences sharedPreferences = getSharedPreferences("current_user", Context.MODE_PRIVATE);
        userDocumentID = sharedPreferences.getString("userDocumentID", "");

        CollectionReference notes = FirebaseFirestore.getInstance().collection("notes")
                .document(userDocumentID).collection("myNotes");

        Query query = notes.orderBy("timestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<NoteModel> options = new FirestoreRecyclerOptions.Builder<NoteModel>()
                .setQuery(query, NoteModel.class).build();
        recyclerView.setHasFixedSize(true);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        noteAdapter = new NoteAdapter(options, this);
        recyclerView.setAdapter(noteAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        noteAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        noteAdapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        noteAdapter.notifyDataSetChanged();
    }
}