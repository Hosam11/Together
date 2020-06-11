package com.example.together.data.api.explore_apis;

import androidx.lifecycle.MutableLiveData;
import com.example.together.data.model.Group;
import com.example.together.data.model.Interest;
import java.util.List;

public class ExploreRepo {
    public MutableLiveData<List<Interest>> getInterests(String header){
        return ExploreAPIProvider.getInstance().getInterests(header);
    }
    public MutableLiveData<List<Group>> getInterestGroups(String header, int interestID){
        return ExploreAPIProvider.getInstance().getInterestGroups(header,interestID);
    }
    public MutableLiveData<List<Group>> search(String header, String q){
        return ExploreAPIProvider.getInstance().search(header, q);
    }
}
