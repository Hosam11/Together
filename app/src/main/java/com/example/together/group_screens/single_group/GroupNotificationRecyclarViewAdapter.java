package com.example.together.group_screens.single_group;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.together.Adapters.POJO;
import com.example.together.R;
import com.example.together.data.model.JoinGroupResponse;
import com.example.together.data.storage.Storage;
import com.example.together.view_model.GroupViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.example.together.utils.HelperClass.TAG;

public class GroupNotificationRecyclarViewAdapter extends RecyclerView.Adapter<GroupNotificationRecyclarViewAdapter.MyViewHolder> {

    List<JoinGroupResponse> requests=new ArrayList<>();
    public Context context;
    GroupViewModel groupViewModel;
    Storage s = new Storage();
    Storage st;
    GroupNotificationFragment groupNotificationFragment;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView not_title;
        public TextView not_description;
        public LinearLayout friendRequestLayout;
        public Button acceptRequest;
        public Button declineRequest;
        public View layout;
        public MyViewHolder(View v) {
            super(v);
            layout = v;
            not_title = v.findViewById(R.id.notification_title);
            not_description = v.findViewById(R.id.notification_description);
            friendRequestLayout= v.findViewById(R.id.friend_request_layout);
            acceptRequest = v.findViewById(R.id.accept_join_request);

            declineRequest = v.findViewById(R.id.decline_join_request);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public GroupNotificationRecyclarViewAdapter(List<JoinGroupResponse> requests, Context context,GroupNotificationFragment g) {

        this.requests=requests;
        this.context = context;
        st = new Storage(context);
        groupNotificationFragment=g;
        groupViewModel = new ViewModelProvider(groupNotificationFragment).get(GroupViewModel.class);


    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v =inflater.inflate(R.layout.notification_cell,parent,false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }




    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.not_title.setText("request");
        holder.not_description.setText(requests.get(position).getReqContent());
        holder.declineRequest.setOnClickListener((e)->{
            rejectReqJoinGroup(requests.get(position).getId());
            groupNotificationFragment.showAllRequestsForGroup();
        });
        holder.acceptRequest.setOnClickListener((e)->{
            acceptReqJoinGroup(requests.get(position).getId());
            groupNotificationFragment.showAllRequestsForGroup();
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return requests.size();
    }

    private void acceptReqJoinGroup(int reqID) {

        groupViewModel.acceptJoinReqGroup(reqID, s.getGroup(context).getAdminID(), st.getToken()).observe(groupNotificationFragment, acceptRes -> {
            Log.i(TAG, "rejectReqJoinGrop: " + acceptRes);
        });
    }

    private void rejectReqJoinGroup(int reqID) {
        groupViewModel.rejectJoinReqGroup(reqID, st.getToken()).observe(groupNotificationFragment, rejecRes -> {
            Log.i(TAG, "acceptReqJoinGrop: " + rejecRes);
        });
    }
}
