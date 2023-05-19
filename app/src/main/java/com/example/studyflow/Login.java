package com.example.studyflow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {
    FirebaseFirestore firebase;
    Button btnSubmit, btnSignUp;
    EditText txtName;
    EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnSubmit = findViewById(R.id.btnSubmit);
        txtName = findViewById(R.id.editTextUsername);
        txtPassword = findViewById(R.id.editTextPassword);
        btnSignUp = findViewById(R.id.btnSignUp);

        FirebaseApp.initializeApp(this);
        firebase = FirebaseFirestore.getInstance();

        btnSubmit.setOnClickListener(view -> {
            String name = txtName.getText().toString();
            String password = txtPassword.getText().toString();
            if(name.isEmpty()) {
                Toast.makeText(Login.this, "Username cannot be empty.", Toast.LENGTH_SHORT).show();
            } else if(password.isEmpty()){
                Toast.makeText(Login.this, "Password cannot be empty.", Toast.LENGTH_SHORT).show();
            } else {
                login(name, password);
            }
        });

        btnSignUp.setOnClickListener(view -> {
            Intent intentSignUp = new Intent(getApplicationContext(), SignUp.class);
            startActivity(intentSignUp);
        });
    }

    private void login(String username, String password) {
        CollectionReference users = firebase.collection("users");

        users.whereEqualTo("username", username)
                .whereEqualTo("password", password)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        Toast.makeText(Login.this, "Welcome " + username + "!", Toast.LENGTH_SHORT).show();
                        Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                        mainIntent.putExtra("name", username);
                        startActivity(mainIntent);
                    } else {
                        Toast.makeText(Login.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
