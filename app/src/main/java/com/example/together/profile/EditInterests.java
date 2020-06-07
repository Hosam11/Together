package com.example.together.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.CompoundButtonCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.together.CustomProgressDialog;
import com.example.together.R;
import com.example.together.data.model.GeneralResponse;
import com.example.together.data.model.Interests;
import com.example.together.data.model.UserInterests;
import com.example.together.data.storage.Storage;
import com.example.together.utils.HelperClass;
import com.example.together.view_model.UserViewModel;
import com.example.together.view_model.UsersViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.example.together.utils.HelperClass.ERROR_INTERESTS;
import static com.example.together.utils.HelperClass.showAlert;

public class EditInterests extends AppCompatActivity {
    LinearLayout containerLayout;
    Button saveBtn;
    ArrayList<Interests> interestsList;

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
    List<String> selectedInterest;
    UsersViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_interests);
        getSupportActionBar().hide();

        containerLayout=findViewById(R.id.container_layout);
        saveBtn=findViewById(R.id.save_btn);
        selectedInterest=new ArrayList<>();


        userViewModel=new ViewModelProvider(this).get(UsersViewModel.class);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(HelperClass.checkInternetState(EditInterests.this)){
                    CustomProgressDialog.getInstance(EditInterests.this).show();

                    save();}
                else {
                    HelperClass.showAlert("Error",HelperClass.checkYourCon,EditInterests.this);

                }

            }
        });
        if(HelperClass.checkInternetState(EditInterests.this)){

            getInterests();}
        else {
            HelperClass.showAlert("Error",HelperClass.checkYourCon,EditInterests.this);

        }

    }


public void getInterests(){
//TODO : After token remove from getAllInterests
        userViewModel.getAllInterests().observe(this, new Observer<ArrayList<Interests>>() {
            @Override
            public void onChanged(ArrayList<Interests> interests) {
                if(interests!=null) {
                    interestsList = interests;
                    if (interestsList.size() > 0) {
                        CustomProgressDialog.getInstance(EditInterests.this).cancel();

                        displayInterests();

                    }
                }
                else {
                    CustomProgressDialog.getInstance(EditInterests.this).cancel();

                    HelperClass.showAlert("Error",HelperClass.SERVER_DOWN,EditInterests.this);


                }
            }
        });

}
    private  void displayInterests(){
        CompoundButton.OnCheckedChangeListener listener = (buttonView, isChecked) -> {
            if (isChecked) {
    Toast.makeText(getApplicationContext(),buttonView.getText(), Toast.LENGTH_SHORT).show();
                //TODO
                selectedInterest.add(buttonView.getText().toString());
            } else {
            Toast.makeText(getApplicationContext(), buttonView.getText() + "Removed", Toast.LENGTH_SHORT).show();
                //TODO
                selectedInterest.remove(buttonView.getText().toString());

            }
        };
        for (int i = 0; i < interestsList.size(); i++) {
            CheckBox ch = new CheckBox(this);
            ch.setTextColor(getResources().getColor(R.color.black));
            CompoundButtonCompat.setButtonTintList(ch, colorStateList);

            ch.setText(interestsList.get(i).getName());
            ch.setOnCheckedChangeListener(listener);
            containerLayout.addView(ch);


            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) ch.getLayoutParams();

            lp.setMargins(0, 0, 0, 17);
            ch.setLayoutParams(lp);

        }

    }




    public void save(){

        if (selectedInterest.isEmpty()) {
            HelperClass.showAlert("Error", ERROR_INTERESTS,EditInterests.this);
        } else {
            Storage storage=new Storage(this);
            UserInterests userInterests=new UserInterests(selectedInterest);
           userViewModel.updateUserInterests(storage.getId(),HelperClass.BEARER_HEADER+storage.getToken(),userInterests).observe(this, new Observer<GeneralResponse>() {
               @Override
               public void onChanged(GeneralResponse gResponse) {
                   if(gResponse!=null) {
                       Toast.makeText(getApplicationContext(), gResponse.response, Toast.LENGTH_LONG).show();
                       EditInterests.this.finish();
                   }
                    else {

                       HelperClass.showAlert("Error",HelperClass.SERVER_DOWN,EditInterests.this);


                   }
               }
           });



        }
        CustomProgressDialog.getInstance(EditInterests.this).cancel();

    }
}
