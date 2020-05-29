package com.example.together.Adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.together.R;
import com.example.together.ToDoListPachage.BoardFragment;

public class CreateDialog  extends AppCompatDialogFragment {
    String dialogType;
    Button add;
    Button yes;
    Button no;
    EditText title;
    EditText description;
    BoardFragment boardFragment;

    public CreateDialog(String dialogType, BoardFragment boardFragment){
       this.boardFragment=boardFragment;
       this.dialogType=dialogType;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder adb=new AlertDialog.Builder(getActivity());
        final LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        switch (dialogType){
            case "addTask":
                return toAddTaskDialog(adb,layoutInflater);

//            case ("deleteTask"):
//                return toAddDeleteDialog(adb,layoutInflater);

        }
      return null;
    }

    public Dialog toAddTaskDialog( AlertDialog.Builder adb, LayoutInflater layoutInflater){
        View v=layoutInflater.inflate(R.layout.add_new_task_dialog,null);
        add=v.findViewById(R.id.add_task);
        title= v.findViewById(R.id.task_title_edit_text);
        description= v.findViewById(R.id.task_description_edit_text);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t = title.getText().toString();
                String d = description.getText().toString();
                POJO pojo = new POJO(t,d,0);
                boardFragment.addTask(pojo);

                CreateDialog.this.dismissAllowingStateLoss();


            }
        });
        adb.setView(v);
        return adb.create();

    }

//    public Dialog toAddDeleteDialog( AlertDialog.Builder adb, LayoutInflater layoutInflater){
//        View v =layoutInflater.inflate(R.layout.remove_task_dialog,null);
//        yes=v.findViewById(R.id.yes);
//        yes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                adapter.getPojos().remove(posision);
//                adapter.notifyDataChanged();
//                CreateDialog.this.dismissAllowingStateLoss();
//            }
//        });
//        no=v.findViewById(R.id.no);
//        no.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CreateDialog.this.dismissAllowingStateLoss();
//            }
//        });
//        adb.setView(v);
//        return adb.create();
//    }

}
