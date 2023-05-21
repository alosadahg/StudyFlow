package com.example.studyflow;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.studyflow.Model.UserModel;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUp extends AppCompatActivity {
    EditText txtConfirmPass, txtUsername, txtPassword;
    Button btnSignUp;

    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        txtConfirmPass = (EditText) findViewById(R.id.editTextConfirmPassword);
        txtUsername = (EditText) findViewById(R.id.editTextUsername);
        txtPassword = (EditText) findViewById(R.id.editTextPassword);
        btnSignUp = (Button) findViewById(R.id.btnRegister);

        FirebaseApp.initializeApp(this);
        database = FirebaseFirestore.getInstance();

        btnSignUp.setOnClickListener(view -> {
            String username = txtUsername.getText().toString();
            String password = txtPassword.getText().toString();
            String confirmPass = txtConfirmPass.getText().toString();

            if(username.isEmpty()) {
                Toast.makeText(SignUp.this, "Username cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            } else if(password.isEmpty()) {
                Toast.makeText(SignUp.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            if(!password.equals(confirmPass)) {
                Toast.makeText(SignUp.this, "Password Mismatch", Toast.LENGTH_SHORT).show();
                return;
            }

            if(NetworkCheck.isNetworkAvailable(getApplicationContext())==false) {
                SharedPreferences sharedPreferences = getSharedPreferences("offline_access", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", username);
                editor.putString("password", password);
                editor.apply();
                Toast.makeText(SignUp.this,"Account created", Toast.LENGTH_SHORT).show();
                Intent backToLogin = new Intent(getApplicationContext(), Login.class);
                startActivity(backToLogin);
            }

            CollectionReference users = database.collection("users");

            UserModel user = new UserModel(username, password);

            users.add(user).addOnSuccessListener(documentReference -> {
                Toast.makeText(SignUp.this,"Account created", Toast.LENGTH_SHORT).show();
                Intent backToLogin = new Intent(getApplicationContext(), Login.class);
                startActivity(backToLogin);
            }).addOnFailureListener(e -> Toast.makeText(SignUp.this,"Please connect to the internet and try again", Toast.LENGTH_SHORT).show());
        });
    }
}
