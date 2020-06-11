package com.example.together.Adapters;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.together.R;
import com.example.together.data.model.Group;
import com.example.together.data.storage.Storage;
import com.example.together.group_screens.single_group.GroupViewPager;

import java.util.List;

public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.GroupViewHolder> {

    private List<Group> groupsList;
    private final Context context;

    public class GroupViewHolder extends RecyclerView.ViewHolder {
        public CardView groupCard;
        public TextView name,description;
        public ImageView image;
        public GroupViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.groupName);
            description = view.findViewById(R.id.groupDescription);
            image = view.findViewById(R.id.groupImage);
            groupCard = view.findViewById(R.id.groupCard);
        }
    }


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
        holder.groupCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO go to group view
                //Toast.makeText(context,groupsList.get(position).getGroupName(),Toast.LENGTH_SHORT).show();
                new Storage().saveGroup(groupsList.get(position),context);
//        Intent goViewGroup = new Intent(getApplicationContext(), ViewGroup.class);
//        goViewGroup.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        getApplicationContext().startActivity(goViewGroup);
            }
    });
    }

    @Override
    public int getItemCount() {
        return groupsList.size();
    }
}