package com.example.together.ToDoListPachage;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.together.R;

import java.util.ArrayList;

public class ToDoListMain extends AppCompatActivity implements GetAddTaskButton {
    ArrayList<Pair<Long, String>> list = new ArrayList<Pair<Long, String>>();
    public Button addTask;
    TextView emptyListPlaceHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_do_list_main);
        getSupportActionBar().hide();

        addTask=findViewById(R.id.add_task);


        if (savedInstanceState == null) {
            showFragment(BoardFragment.newInstance());
        }

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.app_color)));
    }

    private void showFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment, "fragment").commit();
    }

    public Button getAddTask(){
        return addTask;
    }


}

