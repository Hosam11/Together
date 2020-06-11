package com.example.together.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.together.CustomProgressDialog;
import com.example.together.R;
import com.example.together.data.model.GeneralResponse;
import com.example.together.data.model.Group;
import com.example.together.data.storage.Storage;
import com.example.together.group_screens.ViewGroupInfo;
import com.example.together.group_screens.single_group.GroupViewPager;
import com.example.together.utils.HelperClass;
import com.example.together.view_model.GroupViewModel;

import java.util.List;

import static com.example.together.utils.HelperClass.ALERT;
import static com.example.together.utils.HelperClass.GROUP_FULL;
import static com.example.together.utils.HelperClass.TAG;
import static com.example.together.utils.HelperClass.USER_NOT_MEMBER;
import static com.example.together.utils.HelperClass.USER_WAITING_JOIN_GROUP;
import static com.example.together.utils.HelperClass.showAlert;

public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.GroupViewHolder> {

    private final Context context;
    private List<Group> groupsList;
    // TODO Hossam Part
    private Storage userStorage;
    private Storage commonStorage;
    private GroupViewModel groupViewModel;
    private Group curGroup;

    public GroupsAdapter(List<Group> groupsList, Context context) {
        this.groupsList = groupsList;
        this.context = context;
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_row_group, parent, false);
        return new GroupViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GroupViewHolder holder, int position) {
        Group group = groupsList.get(position);
        holder.name.setText(group.getGroupName());
        holder.description.setText(group.getGroupDesc());
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.default_img)
                .error(R.drawable.default_img);
        Glide.with(context).load(groupsList.get(position).getImage()).apply(options).into(holder.image);
        // TODO Hossam Part
        // Storage objects
        userStorage = new Storage(context);
        commonStorage = new Storage();

        //Log.i(TAG, "GroupsAdapter -- onBindViewHolder: curGroup >> " + curGroup);
        groupViewModel = new ViewModelProvider((ViewModelStoreOwner) context)
                .get(GroupViewModel.class);

        holder.groupCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO go to group view
                // TODO Hossam Part
                curGroup = groupsList.get(position);
                Log.i(TAG, "GroupsAdapter -- onBindViewHolder: onClick() curGroup >> " + curGroup);
                CustomProgressDialog.getInstance(context).show();
                if (HelperClass.checkInternetState(context)) {
                    Log.i(TAG, "GroupsAdapter -- onClick: curGroup.getGroupID() >> " + curGroup.getGroupID()
                    + "\n@@userStorage.getId() >> " + userStorage.getId());
                    groupViewModel.userRequestJoinStatus(curGroup.getGroupID(),
                            userStorage.getId(), userStorage.getToken())
                            .observe((LifecycleOwner) context,
                                    GroupsAdapter.this::observeJoinStatus);

                } else {
                    CustomProgressDialog.getInstance(context).cancel();
                    HelperClass.showAlert(ALERT, HelperClass.checkYourCon,
                            context);

                }
            }
        });
    }

    /**
     * listen for response of server to see status user for group
     * if user in group go to viewpager else set value of userGroupStatus and
     * go to ViewGroupInfo
     *
     * @param generalRes 4 possible response String
     */
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


    @Override
    public int getItemCount() {
        return groupsList.size();
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder {
        public CardView groupCard;
        public TextView name, description;
        public ImageView image;

        public GroupViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.groupName);
            description = view.findViewById(R.id.groupDescription);
            image = view.findViewById(R.id.groupImage);
            groupCard = view.findViewById(R.id.groupCard);
        }
    }
}