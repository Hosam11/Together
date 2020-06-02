package com.example.together.NavigationFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.example.together.data.model.UserGroup;
import com.example.together.data.storage.Storage;
import com.example.together.group_screens.AddGroup;
import com.example.together.view_model.UserViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    FloatingActionButton fab;
    RecyclerView recyclerView;
    HomeRecyclarViewAdapter adapter;
    ArrayList<UserGroup> userGroupsList = new ArrayList<>();
    UserViewModel userViewModel;
    CustomProgressDialog progressDialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home,container,false);
        fab = v.findViewById(R.id.add_group_FAB);
        fab.setOnClickListener(v1 -> {
            //Intent To Create Group Screen
            Intent createGroup = new Intent(getContext(), AddGroup.class);
            getContext().startActivity(createGroup);
        });
        recyclerView=v.findViewById(R.id.home_groups_rv);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
progressDialog=CustomProgressDialog.getInstance(getContext());
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((BottomNavigationView)getActivity()).setActionBarTitle("Home");
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(recyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        adapter= new HomeRecyclarViewAdapter(userGroupsList, this.getContext());
        recyclerView.setAdapter(adapter);
       getGroups();


        getActivity().findViewById(R.id.btn_create_group_fragment).setOnClickListener(v -> {
            Intent createGroup = new Intent(getContext(), AddGroup.class);
            startActivity(createGroup);
        });
        CustomProgressDialog.getInstance(getContext()).show();



    }



    public void getGroups(){

        Storage storage = new Storage(getContext());
        userViewModel.getAllUserGroups(storage.getId(), storage.getToken()).observe(this, new Observer<ArrayList<UserGroup>>() {
            @Override
            public void onChanged(ArrayList<UserGroup> userGroups) {
                Toast.makeText(getContext(),"Hey"+userGroups.size(),Toast.LENGTH_LONG).show();
                userGroupsList.clear();
                userGroupsList.addAll(userGroups);
                adapter.notifyDataSetChanged();
                progressDialog.cancel();
            }
        });




    }
}
