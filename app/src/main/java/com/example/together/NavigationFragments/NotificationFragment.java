package com.example.together.NavigationFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.together.Adapters.NotificationRecyclarViewAdapter;
import com.example.together.Adapters.POJO;
import com.example.together.BottomNavigationView;
import com.example.together.R;

import java.util.ArrayList;

public class NotificationFragment extends Fragment {
    RecyclerView recyclerView;
    NotificationRecyclarViewAdapter adapter;
    ArrayList<POJO> pojos = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notification,container,false);
        recyclerView=v.findViewById(R.id.notification_rv);
        pojos.add(new POJO("Android Developing","Hello, my name is mahmoud, I live in KafrEldawar",R.drawable.default_img));
        pojos.add(new POJO("IOS Developing","Hello, my name is mahmoud, I live in KafrEldawar",R.drawable.default_img));
        pojos.add(new POJO("Kotlin Developing","Hello, my name is mahmoud, I live in KafrEldawar",R.drawable.default_img));
        pojos.add(new POJO("Flutter Developing","Hello, my name is mahmoud, I live in KafrEldawar",R.drawable.default_img));
        pojos.add(new POJO("React Developing","Hello, my name is mahmoud, I live in KafrEldawar",R.drawable.default_img));
        pojos.add(new POJO("Ionic Developing","Hello, my name is mahmoud, I live in KafrEldawar",R.drawable.default_img));
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((BottomNavigationView)getActivity()).setActionBarTitle("Notification");
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(recyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter= new NotificationRecyclarViewAdapter(pojos);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.notification_status,menu);
         final TextView status = menu.findItem(R.id.switch_btn).getActionView().findViewById(R.id.status);
        final Switch sw = menu.findItem(R.id.switch_btn).getActionView().findViewById(R.id.notification_sw);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    status.setText("On");
                }
                else{
                    status.setText("Off");
                }
            }
        });
    }


}
