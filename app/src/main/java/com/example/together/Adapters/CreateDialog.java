package com.example.together.Adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.content.ContextCompat;

import com.example.together.R;
import com.example.together.ToDoListPachage.BoardFragment;
import com.example.together.ToDoListPachage.ItemAdapter;

public class CreateDialog  extends AppCompatDialogFragment {
    String dialogType;
    Button add;
    Button yes;
    Button no;
    EditText title;
    EditText description;
    BoardFragment boardFragment;
    ItemAdapter itemAdapter;
    int position;
    boolean textChangedInTitle=false;
    boolean textChangedInDescription=false;

    public CreateDialog(String dialogType, BoardFragment boardFragment){
       this.boardFragment=boardFragment;
       this.dialogType=dialogType;
    }
    public CreateDialog(String dialogType, ItemAdapter itemAdapter,int position){
        this.boardFragment=boardFragment;
        this.dialogType=dialogType;
        this.itemAdapter=itemAdapter;
        this.position=position;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder adb=new AlertDialog.Builder(getActivity());
        final LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        switch (dialogType){
            case "addTask":
                return toAddTaskDialog(adb,layoutInflater);

               case ("editTask"):
               return toAddDeleteDialog(adb,layoutInflater);

        }
      return null;
    }

    public Dialog toAddTaskDialog( AlertDialog.Builder adb, LayoutInflater layoutInflater){
        View v=layoutInflater.inflate(R.layout.add_new_task_dialog,null);
        add=v.findViewById(R.id.add_task);
        title= v.findViewById(R.id.task_title_edit_text);
        description= v.findViewById(R.id.task_description_edit_text);
        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()!=0) {
                    textChangedInTitle=true;
                    if(textChangedInDescription==textChangedInTitle==true) {
                        add.setBackground(ContextCompat.getDrawable(boardFragment.getContext(), R.drawable.corners_from_all_without_stroke));
                        add.setClickable(true);
                    }
                }
                else {
                    add.setBackground(ContextCompat.getDrawable(boardFragment.getContext(), R.drawable.corners_from_all_without_stroke_grey));
                    add.setClickable(false);
                    textChangedInTitle=false;

                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()!=0) {
                    textChangedInDescription=true;
                    if(textChangedInDescription==textChangedInTitle==true) {
                        add.setBackground(ContextCompat.getDrawable(boardFragment.getContext(), R.drawable.corners_from_all_without_stroke));
                        add.setClickable(true);
                    }
                }
                else {
                    add.setBackground(ContextCompat.getDrawable(boardFragment.getContext(), R.drawable.corners_from_all_without_stroke_grey));
                    add.setClickable(false);
                    textChangedInDescription=false;

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(add.isClickable()==true) {
                    String t = title.getText().toString();
                    String d = description.getText().toString();
                    POJO pojo = new POJO(t, d, 0);
                    boardFragment.addTask(pojo);

                    CreateDialog.this.dismissAllowingStateLoss();
                }

            }
        });
        adb.setView(v);
        return adb.create();

    }

    public Dialog toAddDeleteDialog( AlertDialog.Builder adb, LayoutInflater layoutInflater){
        View v=layoutInflater.inflate(R.layout.add_new_task_dialog,null);
        add=v.findViewById(R.id.add_task);
        add.setText("Edit");
        title= v.findViewById(R.id.task_title_edit_text);
        description= v.findViewById(R.id.task_description_edit_text);
        title.setText(itemAdapter.list.get(position).title);
        description.setText(itemAdapter.list.get(position).description);
        int id = itemAdapter.list.get(position).image;
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t = title.getText().toString();
                String d = description.getText().toString();
                POJO pojo = new POJO(t,d,id);
                itemAdapter.editTask(pojo,position);
                itemAdapter.notifyDataSetChanged();
                CreateDialog.this.dismissAllowingStateLoss();


            }
        });
        adb.setView(v);
        return adb.create();
    }

}
