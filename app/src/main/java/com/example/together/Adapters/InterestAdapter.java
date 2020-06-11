package com.example.together.Adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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
        return interests.get(position);
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
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.interest_default)
                .error(R.drawable.interest_default);
        Glide.with(context).load(interests.get(position).getImg()).apply(options).into(interestImage);
        interestName.setText(interests.get(position).getName());
        return  convertView;
    }

}
