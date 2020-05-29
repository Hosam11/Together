package com.example.together.Login_Signup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.CompoundButtonCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TableRow;
import android.widget.Toast;

import com.example.together.R;
import com.example.together.data.model.User;
import com.example.together.group_screens.AddGroup;
import com.example.together.utils.HelperClass;
import com.example.together.view_model.UserViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.example.together.utils.HelperClass.ERROR_MISSING_FILEDS;
import static com.example.together.utils.HelperClass.TAG;
import static com.example.together.utils.HelperClass.showAlert;


public class InterestsActivity extends AppCompatActivity {

    LinearLayout containerLayout;
    Button signupBtn;
    UserViewModel userViewModel;

    final String[] interests = {" PHP", " JAVA", " JSON", " C#", " Objective-C"};

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interests);
        getSupportActionBar().hide();

        containerLayout=findViewById(R.id.container_layout);
        signupBtn=findViewById(R.id.signup_btn);
        CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    Toast.makeText(getApplicationContext(),buttonView.getText(),Toast.LENGTH_SHORT).show() ;

                    //TODO


                } else {
                    Toast.makeText(getApplicationContext(),buttonView.getText()+"Removed",Toast.LENGTH_SHORT).show() ;

                    //TODO

                }
            }
        };

        for(int i = 0; i < interests.length; i++) {
            CheckBox ch = new CheckBox(this);
            ch.setTextColor(getResources().getColor(R.color.black));
            CompoundButtonCompat.setButtonTintList(ch,colorStateList);

            ch.setText(interests[i]);
         ch.setOnCheckedChangeListener(listener);
            containerLayout.addView(ch);


            LayoutParams lp = (LinearLayout.LayoutParams) ch.getLayoutParams();

            lp.setMargins(0,0,0,17);
            ch.setLayoutParams(lp);

        }

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

       // findViewById(R.id.signup_btn).setOnClickListener(v -> createAccount());


    }


    private void userSignUpObservable(User user) {

        //progressBar.setVisibility(View.VISIBLE);

        userViewModel.signUp(user).observe(this, res ->
                {
                    // remove that observable so when click sign up again dosen't fail
                    //       userViewModel.signUp(user).removeObservers(this);
                    //   userViewModel.clearSignUpRes();
                    Log.i(TAG, "SignUpActivity -- createAccount()  res >> " + res);

                    if (res.equals(HelperClass.SING_UP_SUCCESS)) {
                        showAlert(res, this);
                        Intent goHomeIntent = new Intent(this, AddGroup.class);
                        startActivity(goHomeIntent);
                    } else {
                        showAlert(res, this);
//                        Toast.makeText(this, HelperClass.SING_UP_FAILED,
//                                Toast.LENGTH_SHORT).show();
                    }
                }
        );

        //progressBar.setVisibility(View.INVISIBLE);
    }

//    private void createAccount() {
//
//        String uName = nameEt.getText().toString();
//        String uEmail = emailEt.getText().toString().trim();
//        String uPass = passEt.getText().toString();
//        // TODO will be String later in db
//        String uAge = nameEt.getText().toString();
//        String address = addressEt.getText().toString();
//        List<String> interests = new ArrayList<>();
//        interests.add("ios");
//        interests.add("php");
//
//        if (uName.isEmpty() || uEmail.isEmpty() || uPass.isEmpty()) {
//            showAlert(ERROR_MISSING_FILEDS, this);
//        } else {
//            User user = new User(uName, uEmail, uPass, 5, address, gender, interests);
//            userSignUpObservable(user);
//            Log.i(TAG, "SignUpActivity -- createAccount: #click#");
//        }
//
//    }


}
