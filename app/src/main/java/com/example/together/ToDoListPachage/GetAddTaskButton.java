package com.example.together.ToDoListPachage;

import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

public interface GetAddTaskButton {
    public Button getAddTask();
    public ProgressBar getProgressBar();
    public TextView getPercentageView();

}
