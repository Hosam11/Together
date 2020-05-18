package com.example.together.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.together.R;

import java.util.ArrayList;

public class HomeRecyclarViewAdapter extends RecyclerView.Adapter<HomeRecyclarViewAdapter.MyViewHolder> {


    ArrayList<POJO>pojos=new ArrayList<>();
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title;
        public TextView description;
        public ImageView groupImage;
        public ConstraintLayout constraintLayout;
        public View layout;
        public MyViewHolder(View v) {
            super(v);
            layout = v;
            groupImage =v.findViewById(R.id.group_image);
            title = v.findViewById(R.id.group_title);
            description = v.findViewById(R.id.group_description);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public HomeRecyclarViewAdapter(ArrayList<POJO> pojos) {

        this.pojos=pojos;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public HomeRecyclarViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v =inflater.inflate(R.layout.home_group_cell,parent,false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }




    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.title.setText(pojos.get(position).title);
        holder.description.setText(pojos.get(position).description);
        holder.groupImage.setImageResource(pojos.get(position).image);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return pojos.size();
    }
}
