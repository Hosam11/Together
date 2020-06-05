package com.example.together.ToDoListPachage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.content.ContextCompat;

import com.example.together.R;
import com.example.together.data.model.ListTask;
import com.example.together.utils.HelperClass;

public class CreateDialog  extends AppCompatDialogFragment {
    String dialogType;
    Button add;
    Button yes;
    Button no;
    EditText title;
    EditText description;
    ImageView imageView;
    BoardFragment boardFragment;
    ItemAdapter itemAdapter;
    int position;
    boolean textChangedInTitle=false;
    boolean textChangedInDescription=false;
    int id;
    View v;
    Context context;


    public CreateDialog(String dialogType, BoardFragment boardFragment){
       this.boardFragment=boardFragment;
       this.dialogType=dialogType;
       context=boardFragment.getContext();
    }
    public CreateDialog(String dialogType, ItemAdapter itemAdapter,int position,int id ){
        this.boardFragment=boardFragment;
        this.dialogType=dialogType;
        this.itemAdapter=itemAdapter;
        this.position=position;
        this.id=id;
        context = itemAdapter.context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder adb=new AlertDialog.Builder(getActivity());
        final LayoutInflater layoutInflater = getActivity().getLayoutInflater();
         v=layoutInflater.inflate(R.layout.add_new_task_dialog,null);
        add=v.findViewById(R.id.add_task);
        title= v.findViewById(R.id.task_title_edit_text);
        description= v.findViewById(R.id.task_description_edit_text);
        imageView = v.findViewById(R.id.exit_dialog);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateDialog.this.getDialog().cancel();
            }
        });
        addTextChangeListners();
        addEnterAction(title);
        addEnterAction(description);
        switch (dialogType){
            case "addTask":
                return toAddTaskDialog(adb,layoutInflater);

               case ("editTask"):
               return toAddEditDialog(adb,layoutInflater);

        }
      return null;
    }

    public Dialog toAddTaskDialog( AlertDialog.Builder adb, LayoutInflater layoutInflater){
        add.setEnabled(false);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(add.isEnabled()==true) {
                    String t = title.getText().toString();
                    String d = description.getText().toString();
                    ListTask task = new ListTask(1,2,t,d,boardFragment.toDoList.size(),HelperClass.TODO);
                    boardFragment.addTask(task);

                    CreateDialog.this.dismissAllowingStateLoss();
                }

            }
        });
        adb.setView(v);
        return adb.create();

    }

    public Dialog toAddEditDialog( AlertDialog.Builder adb, LayoutInflater layoutInflater){
        add.setText("Edit");
        title.setText(itemAdapter.list.get(position).getTitle());
        description.setText(itemAdapter.list.get(position).getDescription());
        int id = itemAdapter.list.get(position).getId();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(add.isEnabled()==true) {
                    String t = title.getText().toString();
                    String d = description.getText().toString();
                    ListTask task = new ListTask(1,2,t,d, HelperClass.TODO,id);
                    itemAdapter.editTask(task, position);
                    itemAdapter.notifyDataSetChanged();
                    CreateDialog.this.dismissAllowingStateLoss();

                }
            }
        });

        adb.setView(v);
        return adb.create();
    }

    public void addEnterAction(EditText editText){
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    add.performClick();
                }
                return false;

            }

        });
    }

    public void addTextChangeListners(){
        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()!=0) {
                    textChangedInTitle=true;
                    if(textChangedInDescription==textChangedInTitle==true) {
                        add.setBackground(ContextCompat.getDrawable(context, R.drawable.corners_from_all_without_stroke));
                        add.setEnabled(true);
                    }
                }
                else {
                    add.setBackground(ContextCompat.getDrawable(context, R.drawable.corners_from_all_without_stroke_grey));
                    add.setEnabled(false);
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
                        add.setBackground(ContextCompat.getDrawable(context, R.drawable.corners_from_all_without_stroke));
                        add.setEnabled(true);
                    }
                }
                else {
                    add.setBackground(ContextCompat.getDrawable(context, R.drawable.corners_from_all_without_stroke_grey));
                    add.setEnabled(false);
                    textChangedInDescription=false;

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setOnKeyListener((arg0, keyCode, event) -> {
            // TODO Auto-generated method stub
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                this.dismiss();
            }
            return true;
        });
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
