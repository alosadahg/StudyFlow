package com.example.studyflow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Login extends AppCompatActivity {
    FirebaseFirestore firebase;
    Button btnSubmit;
    EditText txtName;
    EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnSubmit = findViewById(R.id.btnSubmit);
        txtName = findViewById(R.id.editTextUsername);
        txtPassword = findViewById(R.id.editTextPassword);

        FirebaseApp.initializeApp(this);
        firebase = FirebaseFirestore.getInstance();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = txtName.getText().toString();
                String password = txtPassword.getText().toString();

                login(name, password);
            }
        });
    }

    private void login(String username, String password) {
        CollectionReference users = firebase.collection("users");

        users.whereEqualTo("username", username)
                .whereEqualTo("password", password)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();
                            Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                            mainIntent.putExtra("name", username);
                            startActivity(mainIntent);
                        } else {
                            Toast.makeText(Login.this, "Login failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this, "Invalid username/password", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
