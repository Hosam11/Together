package com.example.together.data.api.explore_apis;

import com.example.together.data.model.Group;
import com.example.together.data.model.Interest;

import java.util.HashMap;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ExploreAPIInterface {

    //to get all the interests
    @GET("interests")
    Call<HashMap<String, List<Interest>>> getInterests(@Header("Authorization") String header);

    //to get the groups in single interest
    @GET("interests/{interest}/groups")
    Call<HashMap<String, List<Group>>> getInterestGroups(@Header("Authorization") String header,
                                        @Path("interest") int interestID
    );

    //to search for group by name or keyword
    @GET("groups/search?")
    Call<HashMap<String, List<Group>>> search(@Header("Authorization") String header,
                                     @Query("q") String searchKeyWord
    );
}
