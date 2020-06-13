package com.example.together.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.together.R;
import com.example.together.data.model.Group;
import com.example.together.data.storage.Storage;
import com.example.together.group_screens.single_group.GroupViewPager;

import java.util.ArrayList;

import static com.example.together.utils.HelperClass.TAG;

public class HomeRecyclarViewAdapter extends
        RecyclerView.Adapter<HomeRecyclarViewAdapter.MyViewHolder> {


    ArrayList<Group> userGroups ;
    Context context;

    public HomeRecyclarViewAdapter(ArrayList<Group> userGroups, Context context) {
        this.userGroups = userGroups;

        this.context = context;

    }

    @Override
    public HomeRecyclarViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                   int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.home_group_cell, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.title.setText(userGroups.get(position).getGroupName());
        holder.description.setText(userGroups.get(position).getGroupDesc());
        Glide.with(context).load(userGroups.get(position).getImage()).
                placeholder(R.drawable.group_image).into(holder.groupImage);



        holder.groupCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToGroup = new Intent(context, GroupViewPager.class);
                //goToGroup.putExtra("group",userGroups.get(position));
                Storage storage = new Storage();
                Log.i(TAG, "AboutMembersRecyclerAdapter -- onClick: groupBeforeSave >> "
                        + userGroups.get(position));
                storage.saveGroup(userGroups.get(position), context);
                context.startActivity(goToGroup);
            }
        });


    }

    @Override
    public int getItemCount() {
        return userGroups.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView description;
        public ImageView groupImage;
        public FrameLayout groupCardView;
        LinearLayout groupExpirationStatus;
        public View layout;

        public MyViewHolder(View v) {
            super(v);
            layout = v;
            groupImage = v.findViewById(R.id.group_image);
            title = v.findViewById(R.id.group_title);
            description = v.findViewById(R.id.group_description);
            groupCardView = v.findViewById(R.id.group_card);
            groupExpirationStatus = v.findViewById(R.id.group_expiration_status);
        }
    }
}
