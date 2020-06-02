package com.example.together.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.together.R;
import com.example.together.data.model.Interest;

import java.util.ArrayList;

public class InterestAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<Interest> interests;

    public InterestAdapter(Context context, ArrayList<Interest> interests) {
        this.context = context;
        this.interests = interests;
    }

    @Override
    public int getCount() {
        return interests.size();
    }

    @Override
    public Object getItem(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.interest, null);
        }
        final ImageView interestImage = convertView.findViewById(R.id.interestImage);
        final TextView interestName = convertView.findViewById(R.id.interestName);
        interestImage.setImageResource(interests.get(position).getImage());
        interestName.setText(interests.get(position).getName());
        return  convertView;
    }
}
