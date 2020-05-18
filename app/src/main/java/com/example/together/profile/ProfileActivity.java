package com.example.together.profile;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.together.R;

public class ProfileActivity extends AppCompatActivity {
    TextView interestTv;
    String [] interests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().hide();
        interestTv=findViewById(R.id.interest_tv);
        String intereststr = "aaa-AAA-xAaax-abc-hkgjghaaA-jhkjhk-xxx";
        interests =intereststr.split("-");
        StringBuilder builder=new StringBuilder();
        for(int i = 0; i< this.interests.length; i++){

            builder.append("√ "+ this.interests[i]+"<br/>");


        }
        intereststr=builder.toString();
        interestTv.setText(Html.fromHtml(intereststr));
    }
}
