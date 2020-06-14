package com.example.together.NavigationFragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.together.BottomNavigationView;
import com.example.together.CustomProgressDialog;
import com.example.together.Login_Signup.StartActivity;
import com.example.together.R;
import com.example.together.data.model.GeneralResponse;
import com.example.together.data.model.Group;
import com.example.together.data.model.User;
import com.example.together.data.storage.Storage;
import com.example.together.profile.EditInterests;
import com.example.together.profile.EditProfile;
import com.example.together.utils.HelperClass;
import com.example.together.view_model.UsersViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;
import java.util.Objects;

import static com.example.together.utils.HelperClass.TAG;

public class ProfileFragment extends Fragment implements
        View.OnClickListener {
    TextView nameTv;
    TextView emailTv;
    TextView editProfile;
    EditText addressEt;
    EditText dateEt;
    EditText genderEt;
    TextView interestTv;
    TextView editInterests;
    Button logoutBtn;
    ImageView profileImage;
    String[] interests;
    User user;
    UsersViewModel usersViewModel;
    Storage storage;
    ShimmerFrameLayout shimmer;
    LinearLayout containerLayout;
    LinearLayout shimmerContainer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_profile,
                container, false);
        ((BottomNavigationView) getActivity()).setActionBarTitle("Profile");
        ((BottomNavigationView) getActivity()).getSupportActionBar().hide();
        profileImage = v.findViewById(R.id.profile_image);
        nameTv = v.findViewById(R.id.name_tv);
        emailTv = v.findViewById(R.id.email_tv);
        addressEt = v.findViewById(R.id.address_et);
        dateEt = v.findViewById(R.id.date_et);
        genderEt = v.findViewById(R.id.gender_et);
        editProfile = v.findViewById(R.id.edit_profile_tv);
        editProfile.setOnClickListener(this);
        editInterests = v.findViewById(R.id.edit_interests_tv);
        editInterests.setOnClickListener(this);
        interestTv = v.findViewById(R.id.interests_tv);
        logoutBtn = v.findViewById(R.id.logout_btn);
        shimmer = v.findViewById(R.id.shimmer_layout);
        containerLayout = v.findViewById(R.id.container_layout);
        shimmerContainer = v.findViewById(R.id.shimmer_container);
        showShimmer();
        storage = new Storage(getContext());



        usersViewModel = new ViewModelProvider(this).get(UsersViewModel.class);


        v.findViewById(R.id.tv_profile_logout).setOnClickListener(view -> {
            Intent i = new Intent(getContext(), StartActivity.class);
            startActivity(i);
        });



        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showYesNoAlert("Logout", "Do you really want to logout?");


            }
        });

        // TODO last changed was 31/5/2020
        return v;
    }

    private void logout() {
        CustomProgressDialog.getInstance(getContext()).show();
      
        if (HelperClass.checkInternetState(Objects.requireNonNull(getContext()))) {

            usersViewModel.logout(storage.getToken()).observe(getViewLifecycleOwner(), new Observer<GeneralResponse>() {
                @Override
                public void onChanged(GeneralResponse response) {
                    if (response != null) {
                        CustomProgressDialog.getInstance(getContext()).cancel();


                        storage.clearStorage();

                        Objects.requireNonNull(getActivity()).finish();
                        Intent backToStart = new Intent(getContext(), StartActivity.class);
                        Objects.requireNonNull(getContext()).startActivity(backToStart);

                    } else {
                        CustomProgressDialog.getInstance(getContext()).cancel();
                        HelperClass.showAlert("Error", HelperClass.SERVER_DOWN, getContext());

                    }

                }
            });
        } else {
            CustomProgressDialog.getInstance(getContext()).cancel();

            HelperClass.showAlert("Error", HelperClass.checkYourCon, getContext());

        }


    }

    public void showYesNoAlert(String description, String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View alertView = inflater.inflate(R.layout.custom_yes_no_dialouge, null);
        builder.setView(alertView);
        TextView alertDescription = alertView.findViewById(R.id.alert_description_edit_text);
        TextView alertMessage = alertView.findViewById(R.id.alert_message_edit_text);
        alertDescription.setText(description);
        alertMessage.setText(msg);
        TextView okBtn = alertView.findViewById(R.id.ok_button);
        TextView cancelBtn = alertView.findViewById(R.id.cancle_btn);

        AlertDialog alertDialog = builder.create();

        cancelBtn.setOnClickListener(v -> alertDialog.cancel());
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
                logout();

            }
        });
        alertDialog.show();


    }


    private void showShimmer() {
        shimmerContainer.setVisibility(View.VISIBLE);
        shimmer.setVisibility(View.VISIBLE);
        shimmer.startShimmer();
    }

    private void hideShimmer() {
        shimmer.setVisibility(View.GONE);
        shimmer.stopShimmer();
        shimmerContainer.setVisibility(View.GONE);

        containerLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResume() {

        super.onResume();
        showShimmer();
        CustomProgressDialog.getInstance(getContext()).show();


        if (HelperClass.checkInternetState(Objects.requireNonNull(getContext()))) {

            setProfileDataObservable();
        } else {

            HelperClass.showAlert("Error", HelperClass.checkYourCon, getContext());
            CustomProgressDialog.getInstance(getContext()).cancel();

        }


    }

    private void setProfileDataObservable() {

        Log.i("TOKEN", storage.getToken());


        Log.i(TAG, "ProfileFragment -- setProfileDataObservable: storage.getId()"
                + storage.getId());
        usersViewModel.fetchUserData(storage.getId(), storage.getToken())
                .observe(this, userData -> {
                    // TODO Ghrabawi userData object that carry all info about user
                    //  set UI here with values
                    if (userData != null) {


                            Log.i(TAG, "ProfileFragment -- setProfileDataObservable: userData >>  " + userData);
                            hideShimmer();
                            user = userData;

                            nameTv.setText(userData.getName());
                            emailTv.setText(userData.getEmail());
                            addressEt.setText(userData.getAddress());
                            dateEt.setText(userData.getBirthDate());
                            genderEt.setText(userData.getGender());

                            Glide.with(getContext()).load(userData.getImage()).placeholder(R.drawable
                                    .user_image).into(profileImage);


                            displayInterests(userData.getInterests());


                            CustomProgressDialog.getInstance(getContext()).cancel();
                          }
                     else {
                        HelperClass.showAlert("Error", HelperClass.SERVER_DOWN, getContext());
                        CustomProgressDialog.getInstance(getContext()).cancel();


                    }

                });
    }


    public void displayInterests(List<String> interests) {

        if(interests!=null) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < interests.size(); i++) {

                builder.append("●   " + "<span> &nbsp; </span>" + "<span style=\"color:black;\">" + interests.get(i) + "</span>" + "<br/> ");


            }
            interestTv.setText(Html.fromHtml(builder.toString()));
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_profile_tv:
                Intent goToEdit = new Intent(getContext(), EditProfile.class);
                // goToEdit.putExtra("userData", user);
                storage.savePassedUser(user, getContext());
                startActivity(goToEdit);

                break;

            case R.id.edit_interests_tv:

                Intent goToEditInterest = new Intent(getContext(), EditInterests.class);
                startActivity(goToEditInterest);

                break;


        }

    }
}

