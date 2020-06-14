package com.example.together.view_model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.together.data.api.group_apis.GroupRepo;
import com.example.together.data.model.ChatResponse;
import com.example.together.data.model.GeneralResponse;
import com.example.together.data.model.Group;
import com.example.together.data.model.JoinGroupResponse;
import com.example.together.data.model.MessageId;

import java.util.List;

public class GroupViewModel extends ViewModel {
    private MutableLiveData<GeneralResponse> createGroupRes;
    private MutableLiveData<GeneralResponse> joinGroup;
    private MutableLiveData<List<JoinGroupResponse>> listRequestJoinForGroup;
    private MutableLiveData<GeneralResponse> addMember;

    private MutableLiveData<GeneralResponse> updateGroupRes;

    private MutableLiveData<GeneralResponse> resAcceptJoin;
    private MutableLiveData<GeneralResponse> resRejectJoin;

    private MutableLiveData<GeneralResponse> resUserJoinReq;

    private GroupRepo groupRepo;

    public GroupViewModel() {
        groupRepo = new GroupRepo();
    }

    public MutableLiveData<GeneralResponse> createGroup(Group group, String token) {
        createGroupRes = groupRepo.createGroup(group, token);
        return createGroupRes;
    }

    public MutableLiveData<GeneralResponse> requestJoinGroup(int gpId, int userID, String token) {
        joinGroup = groupRepo.joinGroup(gpId, userID, token);
        return joinGroup;
    }

    public MutableLiveData<List<JoinGroupResponse>> getAllRequestJoinForGroup(int gpId, String token) {
        listRequestJoinForGroup = groupRepo.getAllResponsesForGroup(gpId, token);
        return listRequestJoinForGroup;
    }

    public MutableLiveData<GeneralResponse> addGroupMember(int gpID, int userID,
                                                           int adminID, String token) {
        addMember = groupRepo.addGroupMember(gpID, userID, adminID, token);
        return addMember;
    }

    public MutableLiveData<GeneralResponse> updateGroupInfo(int gpID, int adminID,
                                                            Group group, String token) {
        updateGroupRes = groupRepo.updateGroupInfo(gpID, adminID, group, token);
        return updateGroupRes;
    }

    public MutableLiveData<GeneralResponse> acceptJoinReqGroup(int reqID, int adminID, String token) {
        resAcceptJoin = groupRepo.acceptJoinReqGroup(reqID, adminID, token);
        return resAcceptJoin;
    }

    public MutableLiveData<GeneralResponse> rejectJoinReqGroup(int reqID, String token) {
        resRejectJoin = groupRepo.rejectJoinReqGroup(reqID, token);
        return resRejectJoin;
    }

    public MutableLiveData<GeneralResponse> userRequestJoinStatus(int gpID, int userID, String token) {
        resUserJoinReq = groupRepo.userRequestJoinStatus(gpID, userID, token);
        return resUserJoinReq;
    }

    public MutableLiveData<ChatResponse> getChatMessages(int gpID,String token) {
        return groupRepo.getChatMessages(gpID,token);
    }

    public MutableLiveData<GeneralResponse> deleteChatMsg(MessageId msgID, int adminID, String token) {
        return groupRepo.deleteChatMsg(msgID, adminID, token);
    }

}
