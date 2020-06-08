package com.example.together.NavigationFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.together.Adapters.HomeRecyclarViewAdapter;
import com.example.together.BottomNavigationView;
import com.example.together.CustomProgressDialog;
import com.example.together.R;
import com.example.together.data.model.Group;
import com.example.together.data.storage.Storage;
import com.example.together.group_screens.CreateGroup;
import com.example.together.utils.HelperClass;
import com.example.together.view_model.UsersViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    FloatingActionButton fab;
    RecyclerView recyclerView;
    HomeRecyclarViewAdapter adapter;
    ArrayList<Group> userGroupsList = new ArrayList<>();
    UsersViewModel userViewModel;
    CustomProgressDialog progressDialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        fab = v.findViewById(R.id.add_group_FAB);
        fab.setOnClickListener(v1 -> {
            //Intent To Create Group Screen
            Intent createGroup = new Intent(getContext(), CreateGroup.class);
            getContext().startActivity(createGroup);
        });
        recyclerView = v.findViewById(R.id.home_groups_rv);
        userViewModel = new ViewModelProvider(this).get(UsersViewModel.class);
        progressDialog = CustomProgressDialog.getInstance(getContext());
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((BottomNavigationView) getActivity()).setActionBarTitle("Home");
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new HomeRecyclarViewAdapter(userGroupsList, this.getContext());
        recyclerView.setAdapter(adapter);
        CustomProgressDialog.getInstance(getContext()).show();
        getGroups();


        getActivity().findViewById(R.id.btn_create_group_fragment).setOnClickListener(v -> {
            Intent createGroup = new Intent(getContext(), CreateGroup.class);
            startActivity(createGroup);
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        CustomProgressDialog.getInstance(getContext()).show();

        if (HelperClass.checkInternetState(getContext())) {
            getGroups();
        } else {

            HelperClass.showAlert("Error", HelperClass.checkYourCon, getContext());
            CustomProgressDialog.getInstance(getContext()).cancel();
        }

    }

    public void getGroups() {

        Storage storage = new Storage(getContext());
        userViewModel.getAllUserGroups(storage.getId(), storage.getToken()).observe(this, new Observer<ArrayList<Group>>() {
            @Override
            public void onChanged(ArrayList<Group> userGroups) {
                if (userGroups != null) {
                    userGroupsList.clear();
                    userGroupsList.addAll(userGroups);
                    adapter.notifyDataSetChanged();
                    CustomProgressDialog.getInstance(getContext()).cancel();
                } else {
                    CustomProgressDialog.getInstance(getContext()).cancel();
                    HelperClass.showAlert("Error", HelperClass.SERVER_DOWN, getContext());
                }
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        CustomProgressDialog.getInstance(getContext()).cancel();
    }
}
