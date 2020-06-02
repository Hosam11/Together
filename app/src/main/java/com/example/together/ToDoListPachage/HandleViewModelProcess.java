package com.example.together.ToDoListPachage;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.example.together.R;
import com.example.together.data.model.ListTask;
import com.example.together.utils.HelperClass;
import com.example.together.view_model.UserViewModel;

import java.util.ArrayList;


public class HandleViewModelProcess {
    UserViewModel userViewModel;
    BoardFragment boardFragment;
    ArrayList<ListTask> list = new ArrayList<>();


    public HandleViewModelProcess(UserViewModel userViewModel,BoardFragment boardFragment) {
        this.userViewModel = userViewModel;
        this.boardFragment = boardFragment;
    }

    public void getToDoListTask(){
        userViewModel = new ViewModelProvider(boardFragment).get(UserViewModel.class);
        userViewModel.getToDoListTasks(1,boardFragment.storage.getToken()).observe(boardFragment,toDoListTask->{
            if(toDoListTask!=null){
                boardFragment.toDoList=toDoListTask;
                boardFragment.toDoListAdapter.setList(boardFragment.toDoList);
        TextView itemCount1 = boardFragment.mBoardView.getHeaderView(0).findViewById(R.id.item_count);
        itemCount1.setText(String.valueOf(boardFragment.toDoList.size()));
            }
        });
    }

    public void moveToProgressList(int toRow){
        userViewModel.moveToProgressList(toRow,boardFragment.storage.getToken()).observe(boardFragment,moveToProgress->{
            if(moveToProgress.response.equals(HelperClass.SUCCESS)){
                Toast.makeText(boardFragment.getContext(), "Task moved to inprogress", Toast.LENGTH_SHORT).show();


            }
            else {
                Toast.makeText(boardFragment.getContext(), moveToProgress.response, Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void moveToDoneList(int toRow){
        userViewModel.moveToDoneList(toRow,boardFragment.storage.getToken()).observe(boardFragment,moveToDoneList->{
            if(moveToDoneList.response.equals(HelperClass.SUCCESS)){
                Toast.makeText(boardFragment.getContext(), "Task moved to inprogress", Toast.LENGTH_SHORT).show();


            }
            else {
                Toast.makeText(boardFragment.getContext(), moveToDoneList.response, Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void moveToDoList(int toRow){
        userViewModel.moveToDoList(toRow,boardFragment.storage.getToken()).observe(boardFragment,moveToDoList->{
            if(moveToDoList.response.equals(HelperClass.SUCCESS)){
                Toast.makeText(boardFragment.getContext(), "Task moved to inprogress", Toast.LENGTH_SHORT).show();


            }
            else {
                Toast.makeText(boardFragment.getContext(), moveToDoList.response, Toast.LENGTH_SHORT).show();
            }
        });


    }


    public void getInProgressTasks(){
        userViewModel = new ViewModelProvider(boardFragment).get(UserViewModel.class);
        userViewModel.getInProgressTasks(1,boardFragment.storage.getToken()).observe(boardFragment,doingListTasks->{
            if(doingListTasks!=null){
                boardFragment.doingList =doingListTasks;
                boardFragment.doingListAdapter.setList(doingListTasks);
                TextView itemCount1 = boardFragment.mBoardView.getHeaderView(1).findViewById(R.id.item_count);
                itemCount1.setText(String.valueOf(boardFragment.doingList.size()));
            }
        });
    }

    public void getDoneTasks(){
        userViewModel = new ViewModelProvider(boardFragment).get(UserViewModel.class);
        userViewModel.getDoneTasks(1,boardFragment.storage.getToken()).observe(boardFragment,doneListTasks->{
            if(doneListTasks!=null){
                boardFragment.doneList =doneListTasks;
                boardFragment.doneListAdapter.setList(doneListTasks);
                TextView itemCount1 = boardFragment.mBoardView.getHeaderView(2).findViewById(R.id.item_count);
                itemCount1.setText(String.valueOf(boardFragment.doneList.size()));
            }
        });
    }

}
