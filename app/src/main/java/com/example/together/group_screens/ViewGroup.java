package com.example.together.group_screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.together.R;
import com.example.together.data.storage.Storage;

import static com.example.together.utils.HelperClass.TAG;

public class ViewGroup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_group2);
        getSupportActionBar().hide();
        findViewById(R.id.btn_join_group).setOnClickListener(v -> joinGroup());

    }

    private void joinGroup() {
        Intent i = new Intent(this, AddGroup.class);
        startActivity(i);
    }


}
