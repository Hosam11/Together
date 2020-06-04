package com.example.together.Adapters;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.together.R;
import com.example.together.data.model.Group;

import java.util.List;

public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.MyViewHolder> {

    private List<Group> groupsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,description;
        public ImageView image;
        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.groupName);
            description = view.findViewById(R.id.groupDescription);
            image = view.findViewById(R.id.groupImage);
        }
    }


    public GroupsAdapter(List<Group> groupsList) {
        this.groupsList = groupsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_row_group, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Group group = groupsList.get(position);
        holder.name.setText(group.getGroupName());
        holder.description.setText(group.getGroupDesc());
//        holder.image.setImageResource(group.getImage());
    }

    @Override
    public int getItemCount() {
        return groupsList.size();
    }
}