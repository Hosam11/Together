package com.example.together.Login_Signup;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.together.BottomNavigationView;
import com.example.together.CustomProgressDialog;
import com.example.together.R;
import com.example.together.data.model.LoginResponse;
import com.example.together.data.model.UserLogin;
import com.example.together.data.storage.Storage;
import com.example.together.view_model.UsersViewModel;

import static com.example.together.utils.HelperClass.TAG;
import static com.example.together.utils.HelperClass.showAlert;

public class LoginActivity extends AppCompatActivity {
    EditText emailEt, passEt;
    Button loginBtn;
    UsersViewModel usersViewModel;
    // private ProgressBar pbLogin;
    CustomProgressDialog progressdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        emailEt = findViewById(R.id.email_et);
        passEt = findViewById(R.id.password_et);

        // pbLogin = findViewById(R.id.progressbar);
        loginBtn = findViewById(R.id.login_btn);


        loginBtn.setOnClickListener(v -> {
            if (validateForm()) {
                login();
            }
        });


        usersViewModel = new ViewModelProvider(this).get(UsersViewModel.class);
    }

    private void login() {
//        Log.i(TAG, "login: isButtonEnabled?  " + loginBtn.isEnabled());

        String email = emailEt.getText().toString().trim();
        String pass = passEt.getText().toString();

        UserLogin userLogin = new UserLogin(email, pass);
        
        CustomProgressDialog.getInstance(LoginActivity.this).show();
        loginBtn.setEnabled(false);
        usersViewModel.login(userLogin).observe(this, this::logObserve);


    }

    private void logObserve(LoginResponse logRes) {

        if (logRes.isConFailed()) {
//            showAlert("Failed connect to host!", this);
            showAlert("Error!","Failed connect to host!", this);
            CustomProgressDialog.getInstance(LoginActivity.this).cancel();
            loginBtn.setEnabled(true);
        } else {
            if (logRes.isSuccess()) {
                Log.i(TAG, "LoginActivity -- signUpObservable: go to home");
                //  store id and token in shared preferences
                Storage storage = new Storage(this);
                storage.saveUserData(logRes.getToken(), logRes.getId());
                // TODO change it to home screen

                Intent intent = new Intent(this, BottomNavigationView.class);
                // TODO INTENT ADD FLAG
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                LoginActivity.this.finish();

                CustomProgressDialog.getInstance(LoginActivity.this).cancel();
                loginBtn.setEnabled(true);

            } else {
//                 // not valid user
//                 pbLogin.setVisibility(View.GONE);
                loginBtn.setEnabled(false);
                CustomProgressDialog.getInstance(this).cancel();
                Log.i(TAG, "LoginActivity -- signUpObservable: not valid ");
                showAlert("Error",logRes.getResponse(), this);
                loginBtn.setEnabled(true);

            }
        }
    }

    private boolean validateForm() {
        boolean valid = true;
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        String email = emailEt.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailEt.setError("Required.");
            valid = false;
        } else {
            emailEt.setError(null);
        }

        if (!email.matches(emailPattern)) {
            emailEt.setError("Enter a valid e-mail!");
            valid = false;

        } else {
            emailEt.setError(null);

        }


        String pass = passEt.getText().toString();
        if (TextUtils.isEmpty(pass)) {
            passEt.setError("Required.");
            valid = false;
        } else {
            passEt.setError(null);
        }

        return valid;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, StartActivity.class);
        startActivity(i);
        LoginActivity.this.finish();

    }
}
