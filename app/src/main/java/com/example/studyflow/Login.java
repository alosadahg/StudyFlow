package com.example.studyflow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
        EditText txtName = (EditText) findViewById(R.id.editTextUsername);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = txtName.getText().toString();
                if(name.equals("")) {
                    Toast.makeText(Login.this, "Please enter a name", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                    mainIntent.putExtra("name", name);
                    startActivity(mainIntent);
                }
            }
        });
    }
}