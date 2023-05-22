package com.example.studyflow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

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
            SharedPreferences sharedPreferences = getSharedPreferences("offline_access", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
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
                        // Hark: Use for querying the current user's document ID.
                        QueryDocumentSnapshot documentSnapshot = (QueryDocumentSnapshot) queryDocumentSnapshots.getDocuments().get(0);
                        String documentId = documentSnapshot.getId();
                        SharedPreferences sharedPreferences2 = getSharedPreferences("current_user", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences2.edit();
                        editor.putString("username", username);
                        editor.putString("password", password);
                        // Hark: To be used in Notes.
                        editor.putString("userDocumentID", documentId);
                        editor.apply();
                        Toast.makeText(Login.this, "Welcome " + username + "!", Toast.LENGTH_SHORT).show();
                        Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                        mainIntent.putExtra("name", username);
                        startActivity(mainIntent);
                    } else {
                        if(NetworkCheck.isNetworkAvailable(getApplicationContext())==false) {
                            SharedPreferences sharedPreferences = getSharedPreferences("offline_access", Context.MODE_PRIVATE);
                            String savedUsername = sharedPreferences.getString("username","");
                            String savedPassword = sharedPreferences.getString("password","");
                            if(username.equals(savedUsername) && password.equals(savedPassword)) {
                                SharedPreferences current_user = getSharedPreferences("current_user", Context.MODE_PRIVATE);
                                SharedPreferences.Editor current_editor = current_user.edit();
                                current_editor.putString("username", username);
                                current_editor.putString("password", password);
                                current_editor.apply();
                                Toast.makeText(Login.this, "Welcome " + username + "!", Toast.LENGTH_SHORT).show();
                                Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                                mainIntent.putExtra("name", username);
                                startActivity(mainIntent);
                            } else {
                                Toast.makeText(Login.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                });
    }

    @Override
    public void onBackPressed() {
        Intent splash = new Intent(getApplicationContext(),SplashScreen.class);
        startActivity(splash);
    }
}
