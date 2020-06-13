package com.example.together.group_screens.single_group;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.together.Adapters.NotificationRecyclarViewAdapter;
import com.example.together.Adapters.POJO;
import com.example.together.BottomNavigationView;
import com.example.together.R;
import com.example.together.data.model.JoinGroupResponse;
import com.example.together.data.storage.Storage;
import com.example.together.view_model.GroupViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.example.together.utils.HelperClass.TAG;

public class JoinRequestsFragment extends Fragment {

    RecyclerView recyclerView;
    GroupNotificationRecyclarViewAdapter adapter;
    ToggleButton toggleButton;
    List<JoinGroupResponse> requests = new ArrayList<>();
    GroupViewModel groupViewModel;
    Storage s = new Storage();
    Storage st;
    TextView requestsStatus;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_group_notification,container,false);
        recyclerView=v.findViewById(R.id.notification_rv);
        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);
        st = new Storage(this.getContext());
        showAllRequestsForGroup();
        requestsStatus=v.findViewById(R.id.requests_status);



        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(recyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter= new GroupNotificationRecyclarViewAdapter(requests,getContext(),this);
        recyclerView.setAdapter(adapter);
    }

    public void showAllRequestsForGroup() {

        groupViewModel.getAllRequestJoinForGroup(s.getGroup(getContext()).getGroupID(),st.getToken()).observe(this, requestsJoinList -> {
                Log.i("aaa"," -- getAllResponsesForGroup() enqueue() a req >> " + requestsJoinList);
                requests=requestsJoinList;
            adapter= new GroupNotificationRecyclarViewAdapter(requests,getContext(),this);
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);

        });
    }

    @Override
    public void onResume() {
        super.onResume();
        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);
        st = new Storage(this.getContext());
        showAllRequestsForGroup();
    }
}
