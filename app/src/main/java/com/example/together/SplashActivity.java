package com.example.together;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.together.Login_Signup.StartActivity;
import com.example.together.utils.TypeWriter;

public class SplashActivity extends AppCompatActivity {
     TypeWriter tw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();
        tw = (TypeWriter) findViewById(R.id.slogan);
        tw.setText("");
        tw.setCharacterDelay(100);
        tw.animateText("Knowledge for Everyone");
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(SplashActivity.this, StartActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}
