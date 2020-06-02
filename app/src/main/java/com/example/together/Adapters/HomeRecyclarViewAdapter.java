package com.example.together.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.together.R;
import com.example.together.ToDoListPachage.ToDoListMain;
import com.example.together.group_screens.EditGroupInfo;
import com.example.together.group_screens.single_group.GroupViewPager;
import com.example.together.utils.HelperClass;
import com.example.together.utils.TestApis;

import java.util.ArrayList;

public class HomeRecyclarViewAdapter extends RecyclerView.Adapter<HomeRecyclarViewAdapter.MyViewHolder> {


    ArrayList<POJO> pojos = new ArrayList<>();
    Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public HomeRecyclarViewAdapter(ArrayList<POJO> pojos, Context context) {

        this.pojos = pojos;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public HomeRecyclarViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                   int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.home_group_cell, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.title.setText(pojos.get(position).title);
        holder.description.setText(pojos.get(position).description);
        holder.groupImage.setImageResource(pojos.get(position).image);
        if (position == 0) {
            holder.groupCardView.setOnClickListener(v -> {
                Log.i(HelperClass.TAG, "onBindViewHolder: ");
                Intent goToGroup = new Intent(context,
                        com.example.together.group_screens.ViewGroup.class);
                context.startActivity(goToGroup);
            });
        } else if (position == 1) {
            holder.groupCardView.setOnClickListener(v -> {
                Intent goToGroup = new Intent(context, GroupViewPager.class);
                context.startActivity(goToGroup);
            });
        } else if (position == 2) {
            holder.groupCardView.setOnClickListener(v -> {
                Intent intent = new Intent(context, TestApis.class);
                context.startActivity(intent);
            });
        } else if (position == 3) {
            holder.groupCardView.setOnClickListener(v -> {
                Intent intent = new Intent(context, EditGroupInfo.class);
                context.startActivity(intent);
            });
        } else {
            holder.groupCardView.setOnClickListener(v -> {
                Intent intent = new Intent(context, ToDoListMain.class);
                context.startActivity(intent);
            });
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return pojos.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title;
        public TextView description;
        public ImageView groupImage;
        public LinearLayout groupCardView;
        public View layout;

        public MyViewHolder(View v) {
            super(v);
            layout = v;
            groupImage = v.findViewById(R.id.group_image);
            title = v.findViewById(R.id.group_title);
            description = v.findViewById(R.id.group_description);
            groupCardView = v.findViewById(R.id.group_card);
        }
    }
}
