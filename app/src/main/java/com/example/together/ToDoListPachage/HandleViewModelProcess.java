package com.example.together.ToDoListPachage;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.example.together.CustomProgressDialog;
import com.example.together.R;
import com.example.together.data.model.ListTask;
import com.example.together.data.storage.Storage;
import com.example.together.utils.HelperClass;
import com.example.together.view_model.UserViewModel;

import java.util.ArrayList;


public class HandleViewModelProcess {
    UserViewModel userViewModel;
    BoardFragment boardFragment;
    ArrayList<ListTask> list = new ArrayList<>();
    boolean toDoListGetted=false;
    boolean doingListGetted=false;
    boolean doneListGetted=false;
    Storage s = new Storage();
    Storage st;


    public HandleViewModelProcess(UserViewModel userViewModel,BoardFragment boardFragment) {
        this.userViewModel = userViewModel;
        this.boardFragment = boardFragment;
         st = new Storage(boardFragment.getContext());

    }

    public void getToDoListTask(){
        if(HelperClass.checkInternetState(boardFragment.getContext())) {
            CustomProgressDialog customProgressDialog = new CustomProgressDialog(boardFragment.getContext());
            customProgressDialog.show();
            userViewModel = new ViewModelProvider(boardFragment).get(UserViewModel.class);
            Log.i("tamer", ""+st.getToken());
            Log.i("tamer","mahmoud"+boardFragment.storage.getToken());
            userViewModel.getToDoListTasks(s.getGroup(boardFragment.getContext()).getGroupID(), st.getToken()).observe(boardFragment, toDoListTask -> {

                if (toDoListTask != null) {
                    boardFragment.toDoList = toDoListTask;
                    boardFragment.toDoListAdapter.setList(boardFragment.toDoList);
                    customProgressDialog.cancel();
                    TextView itemCount1 = boardFragment.mBoardView.getHeaderView(0).findViewById(R.id.item_count);
                    itemCount1.setText(String.valueOf(boardFragment.toDoList.size()));
                    toDoListGetted=true;
                    setProgressSize();
                }
                else {
                    customProgressDialog.cancel();
                    HelperClass.showAlert("Error",HelperClass.SERVER_DOWN,boardFragment.getContext());

                }
            });
        }
        else {
            HelperClass.showAlert("Error","Please check your internet connection",boardFragment.getContext());
        }
    }


    public void getInProgressTasks(){

            userViewModel = new ViewModelProvider(boardFragment).get(UserViewModel.class);
            userViewModel.getInProgressTasks(s.getGroup(boardFragment.getContext()).getGroupID(),st.getToken()).observe(boardFragment, doingListTasks->{

                if(doingListTasks!=null){
                    boardFragment.doingList =doingListTasks;
                    boardFragment.doingListAdapter.setList(doingListTasks);
                    TextView itemCount1 = boardFragment.mBoardView.getHeaderView(1).findViewById(R.id.item_count);
                    itemCount1.setText(String.valueOf(boardFragment.doingList.size()));
                    doingListGetted=true;
                    setProgressSize();

                }
            });


    }

    public void moveToProgressList(int toRow){
        if(HelperClass.checkInternetState(boardFragment.getContext())) {
            CustomProgressDialog customProgressDialog = new CustomProgressDialog(boardFragment.getContext());
            customProgressDialog.show();
            userViewModel.moveToProgressList(toRow, st.getToken()).observe(boardFragment, moveToProgress -> {
                if (moveToProgress.response.equals(HelperClass.MOVING_SUCCESS)) {
                    Toast.makeText(boardFragment.getContext(), HelperClass.MOVING_SUCCESS, Toast.LENGTH_SHORT).show();
                    customProgressDialog.cancel();
                    setProgressSize();
                    Log.i("samir", moveToProgress.response.toString());

                } else {
                    customProgressDialog.cancel();
                    HelperClass.showAlert("Error","Invalid request, please try again later",boardFragment.getContext());
                    Log.i("samir", moveToProgress.response.toString());

                }
            });
        }
        else {
            HelperClass.showAlert("Error","Please check your internet connection",boardFragment.getContext());
        }


    }

    public void moveToDoneList(int toRow){
        if(HelperClass.checkInternetState(boardFragment.getContext())) {
            CustomProgressDialog customProgressDialog = new CustomProgressDialog(boardFragment.getContext());
            customProgressDialog.show();
            userViewModel.moveToDoneList(toRow, st.getToken()).observe(boardFragment, moveToDoneList -> {
                if (moveToDoneList.response.equals(HelperClass.MOVING_SUCCESS)) {
                    Toast.makeText(boardFragment.getContext(), HelperClass.MOVING_SUCCESS, Toast.LENGTH_SHORT).show();
                    customProgressDialog.cancel();
                    setProgressSize();
                    Log.i("samir", moveToDoneList.response.toString());



                } else {
                    customProgressDialog.cancel();
                    HelperClass.showAlert("Error","Invalid request, please try again later",boardFragment.getContext());
                    Log.i("samir", moveToDoneList.response.toString());

                }
            });
        }
        else {
            HelperClass.showAlert("Error","Please check your internet connection",boardFragment.getContext());
        }


    }

    public void moveToDoList(int toRow){
        if(HelperClass.checkInternetState(boardFragment.getContext())) {
            CustomProgressDialog customProgressDialog = new CustomProgressDialog(boardFragment.getContext());
            customProgressDialog.show();
            userViewModel.moveToDoList(toRow, st.getToken()).observe(boardFragment, moveToDoList -> {
                if (moveToDoList.response.equals(HelperClass.MOVING_SUCCESS)) {
                    Toast.makeText(boardFragment.getContext(), HelperClass.MOVING_SUCCESS, Toast.LENGTH_SHORT).show();
                    setProgressSize();
                    customProgressDialog.cancel();
                    Log.i("samir", moveToDoList.response.toString());


                } else {
                    customProgressDialog.cancel();
                    HelperClass.showAlert("Error","Invalid request, please try again later",boardFragment.getContext());
                    Log.i("samir", moveToDoList.response.toString());

                }
            });
        }
        else {
            HelperClass.showAlert("Error","Please check your internet connection",boardFragment.getContext());
        }

    }



    public void getDoneTasks(){
        userViewModel = new ViewModelProvider(boardFragment).get(UserViewModel.class);
        userViewModel.getDoneTasks(s.getGroup(boardFragment.getContext()).getGroupID(),st.getToken()).observe(boardFragment, doneListTasks->{

            if(doneListTasks!=null){
                boardFragment.doneList =doneListTasks;
                boardFragment.doneListAdapter.setList(doneListTasks);
                TextView itemCount1 = boardFragment.mBoardView.getHeaderView(2).findViewById(R.id.item_count);
                itemCount1.setText(String.valueOf(boardFragment.doneList.size()));
                doneListGetted=true;
                setProgressSize();

            }
        });
    }

    public void addTask(ListTask task){
        if (HelperClass.checkInternetState(boardFragment.getContext())) {
            CustomProgressDialog customProgressDialog = new CustomProgressDialog(boardFragment.getContext());
            customProgressDialog.show();
            userViewModel = new ViewModelProvider(boardFragment).get(UserViewModel.class);
            userViewModel.addTask(task, st.getToken()).observe(boardFragment, addTaskResp -> {
                if (addTaskResp.response.equals(HelperClass.ADD_TASK_RESPONSE_SUCCESS)) {
                    Toast.makeText(boardFragment.getContext(), "Task Added Successfully", Toast.LENGTH_SHORT).show();
                    customProgressDialog.cancel();
                    if (HelperClass.checkInternetState(boardFragment.getContext())) {
                        customProgressDialog.show();
                        userViewModel.getToDoListTasks(s.getGroup(boardFragment.getContext()).getGroupID(), st.getToken()).observe(boardFragment, toDoListTask -> {


                            if (toDoListTask != null) {
                                boardFragment.toDoList = toDoListTask;
                                boardFragment.toDoListAdapter.setList(toDoListTask);
                                boardFragment.toDoListAdapter.notifyDataSetChanged();
                                customProgressDialog.cancel();
                               updateHeader(0);
                                setProgressSize();
                            }
                            else {
                                customProgressDialog.cancel();
                                HelperClass.showAlert("Error","Invalid request, please try again later",boardFragment.getContext());
                            }
                        });
                    }
                    else {
                        HelperClass.showAlert("Error","Please check your internet connection",boardFragment.getContext());
                    }
                } else {
                    customProgressDialog.cancel();
                    HelperClass.showAlert("Error","Invalid request, please try again later",boardFragment.getContext());                }
            });

        }
//        TextView itemCount1 = mBoardView.getHeaderView(0).findViewById(R.id.item_count);
//        itemCount1.setText(String.valueOf(mBoardView.getAdapter(0).getItemCount()));

        else {
            HelperClass.showAlert("Error","Please check your internet connection",boardFragment.getContext());
        }





    }

    public void sendPositionArrangment (ArrayList<ListTask> tasks)
    {
        if (HelperClass.checkInternetState(boardFragment.getContext())) {
            CustomProgressDialog customProgressDialog = new CustomProgressDialog(boardFragment.getContext());
            customProgressDialog.show();
            userViewModel = new ViewModelProvider(boardFragment).get(UserViewModel.class);
            userViewModel.sendPositionArrangment(tasks,st.getToken()).observe(boardFragment,getInProgressTasks->{
                if (getInProgressTasks.response.equals("All tasks moved successfully")) {
                    customProgressDialog.cancel();
                    Log.i("adham", getInProgressTasks.response.toString());

                } else {
                    customProgressDialog.cancel();
                    HelperClass.showAlert("Error","Invalid request, please try again later",boardFragment.getContext());
                }
            });

        }
        else {
            HelperClass.showAlert("Error","Please check your internet connection",boardFragment.getContext());
        }
    }

    public void setProgressSize(){
        if(toDoListGetted&&doneListGetted&&doingListGetted) {
            if(boardFragment.toDoList.size()!=0||boardFragment.doingList.size()!=0) {
                int todo=boardFragment.toDoList.size();
                int doing = boardFragment.doingList.size();
                int done = boardFragment.doneList.size();
                int total = doing+done+todo;
                double s = (float)done/total;
                double n = s*100;
                int percentage = (int)n;
                if(percentage<33){
                    boardFragment.b.setProgressDrawable(boardFragment.getContext().getResources().getDrawable(R.drawable.progress_bar_group_custom_view_red));
                    boardFragment.percentageView.setTextColor(boardFragment.getContext().getResources().getColor(R.color.red));

                }
                else if(percentage>=33&&percentage<66){
                    boardFragment.b.setProgressDrawable(boardFragment.getContext().getResources().getDrawable(R.drawable.progress_bar_group_custom_view_yellow));
                    boardFragment.percentageView.setTextColor(boardFragment.getContext().getResources().getColor(R.color.yellow));

                }
                else{
                    boardFragment.b.setProgressDrawable(boardFragment.getContext().getResources().getDrawable(R.drawable.progress_bar_group_custom_view_green));
                    boardFragment.percentageView.setTextColor(boardFragment.getContext().getResources().getColor(R.color.green));


                }
                boardFragment.b.setProgress(percentage);
                boardFragment.percentageView.setText(percentage+"%");
            }
            else if(boardFragment.toDoList.size()==0&&boardFragment.doingList.size()==0&&boardFragment.doneList.size()==0) {
                boardFragment.b.setProgress(0);
                boardFragment.percentageView.setText("0%");
            }
            else {
                boardFragment.b.setProgressDrawable(boardFragment.getContext().getResources().getDrawable(R.drawable.progress_bar_group_custom_view_green));
                boardFragment.percentageView.setTextColor(boardFragment.getContext().getResources().getColor(R.color.green));
                boardFragment.b.setProgress(100);
                boardFragment.percentageView.setText("100%");

            }

        }
    }
    public void updateHeader(int columnNumber){
        TextView itemCount1 = boardFragment.mBoardView.getHeaderView(columnNumber).findViewById(R.id.item_count);
        itemCount1.setText(String.valueOf(boardFragment.mBoardView.getAdapter(columnNumber).getItemCount()));
    }

}
