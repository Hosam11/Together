package com.example.together.NavigationFragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.together.Adapters.InterestAdapter;
import com.example.together.CustomProgressDialog;
import com.example.together.data.storage.Storage;
import com.example.together.groups.GroupsUnderInterestActivity;
import com.example.together.R;
import com.example.together.data.model.Interest;
import com.example.together.groups.SearchResultActivity;
import com.example.together.utils.HelperClass;
import com.example.together.view_model.ExploreViewModel;
import java.util.ArrayList;
import java.util.List;

public class ExploreFragment extends Fragment {

    private ArrayList<Interest> interests = new ArrayList<>();
    GridView gridView;
    InterestAdapter interestAdapter;
    ExploreViewModel exploreViewModel;
    CustomProgressDialog progressDialog;
    EditText searchEditText;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_explore,container,false);
        gridView = fragmentView.findViewById(R.id.categories);
        searchEditText = fragmentView.findViewById(R.id.search);

        //searchEditText.
        searchEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= searchEditText.getRight() -searchEditText.getTotalPaddingRight()) {
                        search(searchEditText.getText().toString());

                        return true;
                    }
                }
                return false;
            }
        });
        gridView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getActivity(), GroupsUnderInterestActivity.class);
            startActivity(intent);
            new Storage().saveInterest(interests.get(position),getActivity());
        });

        exploreViewModel = new ViewModelProvider(this).get(ExploreViewModel.class);
        interestAdapter = new InterestAdapter(getActivity().getApplicationContext(), interests);
        progressDialog= CustomProgressDialog.getInstance(getContext());
        return fragmentView;

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        gridView.setAdapter(interestAdapter);
        progressDialog.show();
        getInterests();

    }

    private void getInterests() {
        Storage storage = new Storage(getContext());
        exploreViewModel.getInterests(storage.getToken()).observe(this, interestsList -> {
            if(interestsList!=null){
                interests.clear();
                interests.addAll(interestsList);
                interestAdapter.notifyDataSetChanged();
                CustomProgressDialog.getInstance(getContext()).cancel();
            }
            else {
                CustomProgressDialog.getInstance(getContext()).cancel();
                HelperClass.showAlert("Error",HelperClass.SERVER_DOWN,getContext());}
        });

    }
    @Override
    public void onResume() {
        super.onResume();
        CustomProgressDialog.getInstance(getContext()).show();
        if(HelperClass.checkInternetState(getContext())){
            getInterests();
        }
        else {

            HelperClass.showAlert("Error",HelperClass.checkYourCon,getContext());
            CustomProgressDialog.getInstance(getContext()).cancel();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        CustomProgressDialog.getInstance(getContext()).cancel();
    }

    private void search(String word){
        Log.i("search", "search: "+word.length()+(word==null));
        if(!word.isEmpty()) {
            new Storage().saveKeyword(word, getActivity());
            Intent intent = new Intent(getActivity(), SearchResultActivity.class);
            startActivity(intent);
        }else{
            HelperClass.showAlert("Invalid input","please enter a search text ..",getContext());
        }
    }

}
