package com.example.together.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.together.R;
import com.example.together.data.model.User;
import com.example.together.utils.HelperClass;

import java.util.ArrayList;

import static com.example.together.utils.HelperClass.TAG;


public class AboutMembersRecyclerAdapter extends RecyclerView.Adapter<AboutMembersRecyclerAdapter.MyViewHolder> {


    ArrayList<User> memberArrayList=new ArrayList<>();
    boolean isAdmin;
    Context context;
    private OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView name;
        public ImageView removeBtn;
        public ImageView userImage;
        public LinearLayout linearLayout;
        public View layout;
        public MyViewHolder(View v, final OnItemClickListener listener) {
            super(v);
            layout = v;
            name =v.findViewById(R.id.name_tv);
            removeBtn = v.findViewById(R.id.remove_btn);
            userImage = v.findViewById(R.id.user_img);
           removeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {

                        if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(getAdapterPosition());
                        }
                    }
                }
            });




        }
    }

    public AboutMembersRecyclerAdapter(ArrayList<User> memberArrayList ,boolean isAdmin,Context context) {

        this.memberArrayList=memberArrayList;
        this.isAdmin=isAdmin;
        this.context=context;


    }

    // Create new views (invoked by the layout manager)
    @Override
    public AboutMembersRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                   int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v =inflater.inflate(R.layout.member_row,parent,false);
        MyViewHolder vh = new MyViewHolder(v,mListener);
        return vh;
    }



    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.name.setText(memberArrayList.get(position).getName());


//        holder.userImage.setImageBitmap(HelperClass.decodeBase64(memberArrayList.get(position).getPhoto()));
        Log.i(TAG, "AboutMemberGroup onBindViewHolder: imgUrl" +memberArrayList.get(position).getImage() );
        if(isAdmin==false){

            holder.removeBtn.setVisibility(View.INVISIBLE);

        }









    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return memberArrayList.size();
    }
}
