package com.example.together.NavigationFragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.together.BottomNavigationView;
import com.example.together.R;
import com.example.together.data.model.User;
import com.example.together.data.storage.Storage;
import com.example.together.profile.EditInterests;
import com.example.together.profile.EditProfile;
import com.example.together.profile.UserPojo;
import com.example.together.view_model.UserViewModel;

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
    String[] interests;
    UserPojo user;
    UserViewModel userViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        ((BottomNavigationView) getActivity()).setActionBarTitle("Profile");
        ((BottomNavigationView) getActivity()).getSupportActionBar().hide();

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


        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        setProfileDataObservable();


        // TODO last changed was 31/5/2020
        return v;
    }

    private void setProfileDataObservable() {
        Storage storage = new Storage(getContext());
        Log.i(TAG, "ProfileFragment -- setProfileDataObservable: storage.getId()"
                + storage.getId());
        userViewModel.fetchUserData(storage.getId(), storage.getToken())
                .observe(this, userData -> {
                    // TODO Ghrabawi userData object that carry all info about user
                    //  set UI here with values
                    Log.i(TAG, "ProfileFragment -- setProfileDataObservable: userData >>  " + userData);
                    for (String interest : userData.getInterests()) {
                        Log.i(TAG, "setProfileDataObservable: #Interest# " + interest + "\n");
                    }

                    if (!userData.getGroups().isEmpty()) {
                        for (User.GroupReturned group : userData.getGroups()) {
                            Log.i(TAG, "setProfileDataObservable: #Groups# name: " +
                                    group.getName() +
                                    " -- id: " + group.getId() + "\n");
                        }
                    } else {
                        Log.i(TAG, "setProfileDataObservable: Groups is null");
                    }

                });
    }


    @Override
    public void onResume() {
        super.onResume();

        user = new UserPojo();
        String intereststr = user.getInterests();

        interests = intereststr.split("-");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < this.interests.length; i++) {

            builder.append("●   " + "<span> &nbsp; </span>" + "<span style=\"color:black;\">" + this.interests[i] + "</span>" + "<br/> ");


        }
        intereststr = builder.toString();
        interestTv.setText(Html.fromHtml(intereststr));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_profile_tv:
                Toast.makeText(getContext(), "Work", Toast.LENGTH_SHORT).show();
                Intent goToEdit = new Intent(getContext(), EditProfile.class);
                goToEdit.putExtra("userData", user);
                startActivity(goToEdit);

                break;

            case R.id.edit_interests_tv:

                Intent goToEditInterest = new Intent(getContext(), EditInterests.class);
                startActivity(goToEditInterest);

                break;


        }

    }
}

