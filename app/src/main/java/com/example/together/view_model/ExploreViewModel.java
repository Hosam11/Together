package com.example.together.view_model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.together.data.api.explore_apis.ExploreRepo;
import com.example.together.data.model.Group;
import com.example.together.data.model.Interest;
import java.util.List;

public class ExploreViewModel extends ViewModel {
            private ExploreRepo exploreRepo;
            private MutableLiveData<List<Group>> groups;
            private MutableLiveData<List<Interest>> interests;
            private String searchKeyword;
            public ExploreViewModel(){
                exploreRepo = new ExploreRepo();
            }
    public MutableLiveData<List<Interest>> getInterests(String header){
        return exploreRepo.getInterests(header);
    }

    public MutableLiveData<List<Group>> getInterestGroups(String header, int interestID){
        return exploreRepo.getInterestGroups(header,interestID);
    }

    public MutableLiveData<List<Group>> search(String header, String q){
        return exploreRepo.search(header, q);
    }

            public void search(){

            }
            public void viewInterestGroups(){

            }
            public void viewGroup(){

            }

}
