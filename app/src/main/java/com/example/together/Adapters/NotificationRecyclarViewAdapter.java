package com.example.together.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.together.CustomProgressDialog;
import com.example.together.R;
import com.example.together.data.model.GeneralResponse;
import com.example.together.data.model.Group;
import com.example.together.data.model.Notification;
import com.example.together.data.storage.Storage;
import com.example.together.group_screens.ViewGroupInfo;
import com.example.together.group_screens.single_group.GroupViewPager;
import com.example.together.utils.HelperClass;
import com.example.together.view_model.GroupViewModel;

import java.util.ArrayList;

import static com.example.together.utils.HelperClass.ALERT;
import static com.example.together.utils.HelperClass.GROUP_FULL;
import static com.example.together.utils.HelperClass.TAG;
import static com.example.together.utils.HelperClass.USER_NOT_MEMBER;
import static com.example.together.utils.HelperClass.USER_WAITING_JOIN_GROUP;
import static com.example.together.utils.HelperClass.showAlert;

public class NotificationRecyclarViewAdapter extends RecyclerView.Adapter<NotificationRecyclarViewAdapter.MyViewHolder> {
    private Storage userStorage;
    private Storage commonStorage;
    private GroupViewModel groupViewModel;
    private Group curGroup;

    ArrayList<Notification>notificationArrayList=new ArrayList<>();
    public Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView not_title;
        private TextView not_description;
        private ImageView not_image;
       private FrameLayout frameLayout;

        public View layout;
        public MyViewHolder(View v) {
            super(v);
            layout = v;
            not_image =v.findViewById(R.id.notification_img);
            not_title = v.findViewById(R.id.notification_title);
            not_description = v.findViewById(R.id.notification_description);
            frameLayout=v.findViewById(R.id.notiFrame);


        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public NotificationRecyclarViewAdapter(ArrayList<Notification> notificationArrayList,Context context) {

        this.notificationArrayList=notificationArrayList;
        this.context = context;
        userStorage = new Storage(context);
        commonStorage = new Storage();

        //Log.i(TAG, "GroupsAdapter -- onBindViewHolder: curGroup >> " + curGroup);
        groupViewModel = new ViewModelProvider((ViewModelStoreOwner) context)
                .get(GroupViewModel.class);

    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v =inflater.inflate(R.layout.notification_row,parent,false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }




    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.not_title.setText(notificationArrayList.get(position).getTitle());
        holder.not_description.setText(notificationArrayList.get(position).getInfo());
        Glide.with(context).load(notificationArrayList.get(position).getImg()).placeholder(R.drawable
                .together_notification_logo).into(holder.not_image);
        holder.frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Check notification type

                curGroup = notificationArrayList.get(position).getGroup();
                Log.i(TAG, "GroupsAdapter -- onBindViewHolder: onClick() curGroup >> " + curGroup);
                CustomProgressDialog.getInstance(context).show();
                if (HelperClass.checkInternetState(context)) {
                    if(curGroup!=null) {
                        Log.i(TAG, "GroupsAdapter -- onClick: curGroup.getGroupID() >> " + curGroup.getGroupID()
                                + "\n@@userStorage.getId() >> " + userStorage.getId());
                        groupViewModel.userRequestJoinStatus(curGroup.getGroupID(),
                                userStorage.getId(), userStorage.getToken())
                                .observe((LifecycleOwner) context,
                                        NotificationRecyclarViewAdapter.this::observeJoinStatus);

                    }else {

                        CustomProgressDialog.getInstance(context).cancel();
                        HelperClass.showAlert("Warning","This group seems to be removed",context);

                    }

                } else {
                    CustomProgressDialog.getInstance(context).cancel();
                    HelperClass.showAlert(ALERT, HelperClass.checkYourCon,
                            context);

                }
            }



        });


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return notificationArrayList.size();
    }


    private void observeJoinStatus(GeneralResponse generalRes) {
        // [cancel] dialoge of user status
        CustomProgressDialog.getInstance(context).cancel();
        // User Not Member
        if (generalRes.response.equals(USER_NOT_MEMBER)) {
            Log.i(HelperClass.TAG, "GroupsAdapter -- observeJoinStatus: user not member");
            goViewGroupWithUserGroupStatus(USER_NOT_MEMBER);
        } // User Waiting
        else if (generalRes.response.equals(USER_WAITING_JOIN_GROUP)) {
            Log.i(HelperClass.TAG, "GroupsAdapter -- observeJoinStatus: user waiting to response");
            goViewGroupWithUserGroupStatus(USER_WAITING_JOIN_GROUP);
        } // User In Group
        else if (generalRes.response.equals(HelperClass.USER_IN_GROUP)) {
            // go to view pager
            Log.i(HelperClass.TAG, "GroupsAdapter -- observeJoinStatus:  user in group");
            Intent goGroup = new Intent(context, GroupViewPager.class);
            context.startActivity(goGroup);

        } // Group Is Full
        else if (generalRes.response.equals(HelperClass.GROUP_FULL)) {
            goViewGroupWithUserGroupStatus(GROUP_FULL);
        } else {
            showAlert("Error!", generalRes.response, context);
        }
    }

    /**
     * got to ViewGroupInfo with userGroupStatus to avoid call api again to know it
     *
     * @param userGroupStatus 4 possible response String
     */
    private void goViewGroupWithUserGroupStatus(String userGroupStatus) {
        Intent goViewGroup = new Intent(context, ViewGroupInfo.class);
        goViewGroup.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        curGroup.setUserGroupStatus(userGroupStatus);
        commonStorage.saveGroup(curGroup, context);
        context.startActivity(goViewGroup);
    }





}
