package com.example.together.Login_Signup;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.together.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

    }
}
