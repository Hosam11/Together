package com.example.together.Login_Signup;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.CompoundButtonCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.together.BottomNavigationView;
import com.example.together.CustomProgressDialog;
import com.example.together.R;
import com.example.together.data.model.Interests;
import com.example.together.data.model.LoginResponse;
import com.example.together.data.model.User;
import com.example.together.data.storage.Storage;
import com.example.together.utils.HelperClass;
import com.example.together.view_model.UserViewModel;
import com.example.together.view_model.UsersViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.example.together.utils.HelperClass.ERROR_INTERESTS;
import static com.example.together.utils.HelperClass.TAG;
import static com.example.together.utils.HelperClass.showAlert;


public class InterestsActivity extends AppCompatActivity {

    ArrayList<Interests> interestsList;
    LinearLayout containerLayout;
    Button signupBtn;
    UsersViewModel userViewModel;
    List<String> selectedInterest;
    UsersViewModel newUsersViewModel;
    ColorStateList colorStateList = new ColorStateList(
            new int[][]{
                    new int[]{-android.R.attr.state_checked}, // unchecked
                    new int[]{android.R.attr.state_checked}, // checked
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

        containerLayout = findViewById(R.id.container_layout);
        signupBtn = findViewById(R.id.signup_btn);
        selectedInterest = new ArrayList<>();


        userViewModel = new ViewModelProvider(this).get(UsersViewModel.class);
        newUsersViewModel = new ViewModelProvider(this).get(UsersViewModel.class);
        signupBtn.setOnClickListener(v -> createAccount());
        Toast.makeText(getApplicationContext(), "cre", Toast.LENGTH_LONG).show();
        CustomProgressDialog.getInstance(this).show();
        if (HelperClass.checkInternetState(this)) {
            getInterests();
        } else {

            HelperClass.showAlert("Error", HelperClass.checkYourCon, this);
            CustomProgressDialog.getInstance(this).cancel();

        }
    }

    private void getInterests() {
        //TODO : After token remove from getAllInterests

        userViewModel.getAllInterests().observe(this, new Observer<ArrayList<Interests>>() {
            @Override
            public void onChanged(ArrayList<Interests> interests) {
                if (interests != null) {
                    interestsList = interests;
                    if (interests.size() > 0) {
                        displayInterests();
                    }
                } else {
                    HelperClass.showAlert("Error", HelperClass.SERVER_DOWN, InterestsActivity.this);
                    CustomProgressDialog.getInstance(InterestsActivity.this).cancel();

                }
            }
        });


    }


    private void displayInterests() {
        CustomProgressDialog.getInstance(this).cancel();
        CompoundButton.OnCheckedChangeListener listener = (buttonView, isChecked) -> {
            if (isChecked) {
//                Toast.makeText(getApplicationContext(),buttonView.getText(), Toast.LENGTH_SHORT).show();
                //TODO
                selectedInterest.add(buttonView.getText().toString());
            } else {
//                Toast.makeText(getApplicationContext(), buttonView.getText() + "Removed", Toast.LENGTH_SHORT).show();
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


            LayoutParams lp = (LinearLayout.LayoutParams) ch.getLayoutParams();

            lp.setMargins(0, 0, 0, 17);
            ch.setLayoutParams(lp);

        }

    }

    private void createAccount() {
        CustomProgressDialog.getInstance(this).show();

        if (HelperClass.checkInternetState(getApplicationContext())) {
            if (selectedInterest.isEmpty()) {
                showAlert("Error", ERROR_INTERESTS, this);
                CustomProgressDialog.getInstance(InterestsActivity.this).cancel();
            } else {

                for (String i : selectedInterest) {
                    Log.i(TAG, "createAccount: " + i);
                }
                Storage s = new Storage();
                User user = s.getPassUser(this);
                user.setInterests(selectedInterest);

                newUsersViewModel.signUp(user).observe(this, this::userSignUpObservable);
            }

        } else {

            HelperClass.showAlert("Error", HelperClass.checkYourCon, this);
            CustomProgressDialog.getInstance(this).cancel();
        }
    }

    private void userSignUpObservable(String res) {

        Log.i(TAG, "SignUpActivity -- createAccount()  res >> " + res);

        if (res != null) {
            Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
            CustomProgressDialog.getInstance(this).cancel();

            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
//            InterestsActivity.this.finish();
        } else {
            HelperClass.showAlert("Error", HelperClass.SERVER_DOWN, this);
            CustomProgressDialog.getInstance(this).cancel();
        }
    }

    private void userSignUpObservable(LoginResponse loginRes) {

        Log.i(TAG, "SignUpActivity -- createAccount()  res >> " + loginRes);

        if (loginRes.isConFailed()) {
            showAlert("Error", HelperClass.checkYourCon, this);
            CustomProgressDialog.getInstance(this).cancel();
            //loginRes.setEnabled(true);
        } else {
            if (loginRes.isSuccess()) {
                Log.i(TAG, "SignUpActivity -- signUpObservable: go to home");
                CustomProgressDialog.getInstance(this).cancel();
                //  store id and token in shared preferences
                Storage storage = new Storage(this);
                storage.saveUserData(loginRes.getToken(), loginRes.getId());
                // TODO change it to home screen
                Intent intent = new Intent(this, BottomNavigationView.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                this.finish();
//                loginBtn.setEnabled(true);
            } else {
//                 // not valid user
//                 pbLogin.setVisibility(View.GONE);
                //       loginBtn.setEnabled(false);
                //       progressdialog.cancel();
                CustomProgressDialog.getInstance(this).cancel();

                Log.i(TAG, "LoginActivity -- signUpObservable: not valid ");
                showAlert(loginRes.getResponse(), this);
                //      loginBtn.setEnabled(true);

            }
        }

    }


}