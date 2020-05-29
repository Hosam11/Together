package com.example.together.Login_Signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.together.R;
import com.example.together.data.model.LoginResponse;
import com.example.together.data.model.UserLogin;
import com.example.together.data.storage.Storage;
import com.example.together.profile.ProfileActivity;
import com.example.together.utils.HelperClass;
import com.example.together.utils.TestApis;
import com.example.together.view_model.UserViewModel;

import static com.example.together.utils.HelperClass.TAG;
import static com.example.together.utils.HelperClass.showAlert;

public class LoginActivity extends AppCompatActivity {
    EditText emailEt,passEt;
    Button loginBtn;

    

    UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        emailEt=findViewById(R.id.email_et);
        passEt=findViewById(R.id.password_et);
        
       
        findViewById(R.id.login_btn).setOnClickListener(v -> login());

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
    }

    private void login() {
        String email = emailEt.getText().toString().trim();
        String pass = passEt.getText().toString();
        UserLogin userLogin = new UserLogin(email, pass);

        if (email.isEmpty() || pass.isEmpty()) {
            showAlert(HelperClass.ERROR_MISSING_FILEDS, this);
        } else {
            userViewModel.login(userLogin).observe(this, this::logObserve);
        }

    }

    private void logObserve(LoginResponse logRes) {
        if (logRes.isConFailed()) {
            showAlert("Failed connect to host!", this);
        } else {
            if (logRes.isSuccess()) {
                Log.i(TAG, "LoginActivity -- signUpObservable: go to home");
                //  store id and token in shared preferences
                Storage storage = new Storage(this);
                storage.saveUserData(logRes.getToken(), logRes.getId());
                // TODO change it to home screen
//                Intent intent = new Intent(this, ProfileActivity.class);
                Intent intent = new Intent(this, TestApis.class);
                startActivity(intent);

            } else { // not valid user
                Log.i(TAG, "LoginActivity -- signUpObservable: not valid ");
                showAlert(logRes.getResponse(), this);
            }
        }
    }


}
