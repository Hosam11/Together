package com.example.together.group_screens.single_group;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.together.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class JoinRequestsFragment extends Fragment {

    public JoinRequestsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_join_requests, container, false);
    }
}