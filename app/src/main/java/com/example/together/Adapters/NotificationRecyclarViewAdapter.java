package com.example.together.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.together.R;
import com.example.together.data.model.Notification;

import java.util.ArrayList;

public class NotificationRecyclarViewAdapter extends RecyclerView.Adapter<NotificationRecyclarViewAdapter.MyViewHolder> {


    ArrayList<Notification>notificationArrayList=new ArrayList<>();
    public Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView not_title;
        public TextView not_description;
        public ImageView not_image;

        public View layout;
        public MyViewHolder(View v) {
            super(v);
            layout = v;
            not_image =v.findViewById(R.id.notification_img);
            not_title = v.findViewById(R.id.notification_title);
            not_description = v.findViewById(R.id.notification_description);


        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public NotificationRecyclarViewAdapter(ArrayList<Notification> notificationArrayList,Context context) {

        this.notificationArrayList=notificationArrayList;
        this.context = context;

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


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return notificationArrayList.size();
    }
}
