package com.example.mymusic;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    Button loginBtn, registerBtn;
    EditText email, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.registerBtn);
        email=findViewById(R.id.email);
        pass=findViewById(R.id.password);
        if(email.equals("anurag123@gmail.com")&&pass.equals("pass@123")){
        }
        loginBtn.setOnClickListener(v -> {
            String userEmail = email.getText().toString().trim(); String userPass
                    = pass.getText().toString().trim();
            if(userEmail.equals("ankush911@gmail.com")&&userPass.equals("Pass@1234")){
                startActivity(new Intent(Login.this, Home.class));
            }
            else {
                Toast.makeText(this, "credentials fail", Toast.LENGTH_SHORT).show();
            }
        });

        registerBtn.setOnClickListener(v -> {
            startActivity(new Intent(Login.this, Register.class));
        });
    }
}