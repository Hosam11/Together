package com.example.together.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.together.R;

import java.util.ArrayList;

public class NotificationRecyclarViewAdapter extends RecyclerView.Adapter<NotificationRecyclarViewAdapter.MyViewHolder> {


    ArrayList<POJO>pojos=new ArrayList<>();
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView not_title;
        public TextView not_description;
        public ImageView not_image;
        public LinearLayout friendRequestLayout;
        public View layout;
        public MyViewHolder(View v) {
            super(v);
            layout = v;
            not_image =v.findViewById(R.id.notification_img);
            not_title = v.findViewById(R.id.notification_title);
            not_description = v.findViewById(R.id.notification_description);
            friendRequestLayout= v.findViewById(R.id.friend_request_layout);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public NotificationRecyclarViewAdapter(ArrayList<POJO> pojos) {

        this.pojos=pojos;

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
        holder.not_title.setText(pojos.get(position).title);
        holder.not_description.setText(pojos.get(position).description);
        holder.not_image.setImageResource(pojos.get(position).image);
        if(position%2>0) {
            holder.friendRequestLayout.setVisibility(View.GONE);
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return pojos.size();
    }
}
