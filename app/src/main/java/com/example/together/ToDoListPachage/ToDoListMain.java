package com.example.together.ToDoListPachage;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.together.R;
import com.example.together.data.storage.Storage;

import java.util.ArrayList;
import java.util.Objects;

public class ToDoListMain extends AppCompatActivity implements GetAddTaskButton {
    ArrayList<Pair<Long, String>> list = new ArrayList<Pair<Long, String>>();
    public Button addTask;
    TextView emptyListPlaceHolder;
    ProgressBar progressBar;
    TextView percentageView;
    CardView addTaskCardView;
    RelativeLayout addTaskAndProgressLayout;
    RelativeLayout progressLayout;
    Storage storage;
    boolean isAdmin=false;
    Storage storageForUserID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_do_list_main);
        getSupportActionBar().hide();
        storage = new Storage(Objects.requireNonNull(this));
        storageForUserID = new Storage();
        checkIsAdmin();
        addTaskCardView=findViewById(R.id.add_task_btn_cardView);
        addTaskAndProgressLayout = findViewById(R.id.parent_for_addTask_progress);
        progressLayout=findViewById(R.id.parent_for_progress);
        if(isAdmin) {
            ((ViewGroup)(progressLayout.getParent())).removeView(progressLayout);
            progressBar = findViewById(R.id.to_do_progress_admin);
            percentageView = findViewById(R.id.percentage_view_admin);
        }
        else {
            addTaskAndProgressLayout.setVisibility(View.GONE);
            progressBar = findViewById(R.id.to_do_progress_user);
            percentageView = findViewById(R.id.percentage_view_user);
        }
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

    public TextView getPercentageView(){
        return percentageView;
    }


    public RelativeLayout getProgressLayout() {
        return progressLayout;
    }



    @Override
    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void checkIsAdmin(){
        int adminID=storageForUserID.getGroup(this).getAdminID();
        int userId = storage.getId();
        if(adminID==userId){
            isAdmin=true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkIsAdmin();
    }
}

