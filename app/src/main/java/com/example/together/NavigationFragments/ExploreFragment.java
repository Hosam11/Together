package com.example.together.NavigationFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.together.Adapters.HomeRecyclarViewAdapter;
import com.example.together.Adapters.InterestAdapter;
import com.example.together.BottomNavigationView;
import com.example.together.GroupsUnderInterest;
import com.example.together.R;
import com.example.together.data.model.Interest;
import com.example.together.group_screens.AddGroup;

import java.util.ArrayList;

public class ExploreFragment extends Fragment {
    private ArrayList<Interest> interests = new ArrayList<Interest>();
    GridView gridView;
    InterestAdapter interestAdapter;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_explore,container,false);
        interests.add(new Interest("Development", R.drawable.development));
        interests.add(new Interest("Handmade", R.drawable.handmade));
        interests.add(new Interest("Music", R.drawable.music));
        interests.add(new Interest("Sports", R.drawable.sports));
        interests.add(new Interest("Religious", R.drawable.religious));
        interests.add(new Interest("Dancing", R.drawable.dancing));
        interests.add(new Interest("Development", R.drawable.development));
        interests.add(new Interest("Handmade", R.drawable.handmade));
        interests.add(new Interest("Music", R.drawable.music));
        interests.add(new Interest("Sports", R.drawable.sports));
        interests.add(new Interest("Religious", R.drawable.religious));
        interests.add(new Interest("Dancing", R.drawable.dancing));
        interests.add(new Interest("Development", R.drawable.development));
        interests.add(new Interest("Handmade", R.drawable.handmade));
        interests.add(new Interest("Music", R.drawable.music));
        interests.add(new Interest("Sports", R.drawable.sports));
        interests.add(new Interest("Religious", R.drawable.religious));
        interests.add(new Interest("Dancing", R.drawable.dancing));
        gridView = fragmentView.findViewById(R.id.categories);
        interestAdapter = new InterestAdapter(getActivity().getApplicationContext(), interests);
        return fragmentView;

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        gridView.setAdapter(interestAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                //Toast.makeText(getActivity(),""+position,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), GroupsUnderInterest.class);
                startActivity(intent);
            }
        });
    }

}
