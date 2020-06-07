package com.example.together.data.api.group_apis;

import androidx.lifecycle.MutableLiveData;

import com.example.together.data.model.GeneralResponse;
import com.example.together.data.model.Group;
import com.example.together.data.model.JoinGroupResponse;

import java.util.List;

public class GroupRepo {

    private GroupApiProvider groupApiProvider;

    public GroupRepo() {
        groupApiProvider = new GroupApiProvider();
    }


    public MutableLiveData<GeneralResponse> createGroup(Group group, String token) {
        return groupApiProvider.createGroup(group, token);
    }

    public MutableLiveData<GeneralResponse> joinGroup(int gpId, int userID, String token) {
        return groupApiProvider.requestJoinGroup(gpId, userID, token);
    }

    public MutableLiveData<List<JoinGroupResponse>> getAllResponsesForGroup(int gpId, String token) {
        return groupApiProvider.getAllResponsesForGroup(gpId, token);
    }

    public MutableLiveData<GeneralResponse> addGroupMember(int gpID, int userID, int adminID, String token) {
        return groupApiProvider.addGroupMember(gpID, userID, adminID, token);
    }

    public MutableLiveData<GeneralResponse> updateGroupInfo(int gpID, int adminID, Group group, String token) {
        return groupApiProvider.updateGroupInfo(gpID, adminID, group, token);
    }

    public MutableLiveData<GeneralResponse> acceptJoinReqGroup(int reqID, int adminID, String token) {
        return groupApiProvider.acceptJoinReqGroup(reqID, adminID, token);
    }

    public MutableLiveData<GeneralResponse> rejectJoinReqGroup(int reqID, String token) {
        return groupApiProvider.rejectJoinReqGroup(reqID, token);
    }

    public MutableLiveData<GeneralResponse> userRequestJoinStatus(int gpID, int userID, String token) {
        return groupApiProvider.userRequestJoinStatus(gpID, userID, token);
    }
}

