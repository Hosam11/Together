package com.example.together.Login_Signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.together.BottomNavigationView;
import com.example.together.R;
import com.example.together.data.storage.Storage;

public class StartActivity extends AppCompatActivity {

    Button createAccountBtn;
    TextView loginTv;
    Storage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getSupportActionBar().hide();

        createAccountBtn=findViewById(R.id.create_acc_btn);
        loginTv=findViewById(R.id.login_tv);
        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoSingUp=new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(gotoSingUp);

            }
        });

        loginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoLogin=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(gotoLogin);

            }
        });
        storage=new Storage(this);
        Toast.makeText(getApplicationContext(),"I"+storage.getId(),Toast.LENGTH_LONG).show();
        if(storage.getId()!=0){

            Intent gotoHome=new Intent(this, BottomNavigationView.class);

            startActivity(gotoHome);
            StartActivity.this.finish();



        }

    }
}
