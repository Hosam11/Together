package com.example.together.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.CompoundButtonCompat;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.together.R;

public class EditInterests extends AppCompatActivity {
    LinearLayout containerLayout;
    Button saveBtn;
    final String[] interests = {" PHP", " JAVA", " JSON", " C#", " Objective-C"};

    ColorStateList colorStateList = new ColorStateList(
            new int[][]{
                    new int[]{-android.R.attr.state_checked}, // unchecked
                    new int[]{android.R.attr.state_checked} , // checked
            },
            new int[]{
                    Color.parseColor("#000000"),
                    Color.parseColor("#1278da")
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_interests);
        getSupportActionBar().hide();

        containerLayout=findViewById(R.id.container_layout);
        saveBtn=findViewById(R.id.save_btn);
        CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    Toast.makeText(getApplicationContext(),buttonView.getText(),Toast.LENGTH_SHORT).show() ;

                    //TODO


                } else {
                    Toast.makeText(getApplicationContext(),buttonView.getText()+"Removed",Toast.LENGTH_SHORT).show() ;

                    //TODO

                }
            }
        };

        for(int i = 0; i < interests.length; i++) {
            CheckBox ch = new CheckBox(this);
            ch.setTextColor(getResources().getColor(R.color.black));
            CompoundButtonCompat.setButtonTintList(ch,colorStateList);

            ch.setText(interests[i]);
            ch.setOnCheckedChangeListener(listener);
            containerLayout.addView(ch);


            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) ch.getLayoutParams();

            lp.setMargins(0,0,0,17);
            ch.setLayoutParams(lp);

        }
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"save",Toast.LENGTH_SHORT).show() ;


            }
        });

    }
}
