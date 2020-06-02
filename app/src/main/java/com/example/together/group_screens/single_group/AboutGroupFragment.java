package com.example.together.group_screens.single_group;

import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.together.Adapters.AboutMembersRecyclerAdapter;
import com.example.together.Adapters.POJO;
import com.example.together.R;
import com.example.together.data.model.GeneralResponse;
import com.example.together.data.model.Group;
import com.example.together.data.model.GroupDetails;
import com.example.together.data.model.UserGroup;
import com.example.together.data.storage.Storage;
import com.example.together.view_model.UserViewModel;

import java.util.ArrayList;

public class AboutGroupFragment extends Fragment {

    RecyclerView members_recycler;
    ArrayList<GroupDetails.Member> groupMembersList = new ArrayList<>();
    AboutMembersRecyclerAdapter adapter;
    ImageView groupImgView;
    TextView nameTv;
    TextView groupDescriptionTv;
    TextView editGroupTv;
    UserViewModel userViewModel;
    Storage storage;


    boolean isAdmin = false;

    public AboutGroupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        storage= new Storage(getContext());
        UserGroup receivedGroup=(UserGroup)getActivity().getIntent().getSerializableExtra("group");


        View view = inflater.inflate(R.layout.fragment_about_group,
                container, false);

        members_recycler = view.findViewById(R.id.members_recycler);
        groupImgView=view.findViewById(R.id.group_img_tv);
        nameTv=view.findViewById(R.id.name_tv);
        groupDescriptionTv=view.findViewById(R.id.group_description_tv);
        editGroupTv=view.findViewById(R.id.edit_group_tv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        members_recycler.setLayoutManager(layoutManager);


        if(receivedGroup.getAdmin_id()==storage.getId()){
            isAdmin=true;
            editGroupTv.setVisibility(View.VISIBLE);
            editGroupTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO hossam Edit Group

                    Toast.makeText(getContext(),"Here",Toast.LENGTH_LONG).show();
                }
            });
        }
        adapter = new AboutMembersRecyclerAdapter(groupMembersList, isAdmin, getContext());
        members_recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new AboutMembersRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position,receivedGroup.getId());
            }
        });


        nameTv.setText(receivedGroup.getName());
        groupDescriptionTv.setText(receivedGroup.getDescription());
      //  groupImgView TODO HERE Getting Image

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        // getSpecificGroupDetails
        getGroupDetails(receivedGroup.getId());



        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    public void removeItem(int position,int groupId) {
        Toast.makeText(getContext(), "GID"+groupId,Toast.LENGTH_LONG).show();
        Toast.makeText(getContext(), "UID"+groupMembersList.get(position).getId(),Toast.LENGTH_LONG).show();
        Toast.makeText(getContext(), "CID"+storage.getId(),Toast.LENGTH_LONG).show();



        userViewModel.removeMemberFromGroup(groupId, groupMembersList.get(position).getId(),storage.getId(),storage.getToken()).observe(this, new Observer<GeneralResponse>() {
            @Override
            public void onChanged(GeneralResponse response) {
              //  if (response.response.equalsIgnoreCase("")){
                    Toast.makeText(getContext(), response.response,Toast.LENGTH_LONG).show();

                    groupMembersList.remove(position);
                    adapter.notifyItemRemoved(position);
               // }

            }
        });





    }

    public void getGroupDetails(int groupId){
        userViewModel.getSpecificGroupDetails(groupId,storage.getToken()).observe(this, new Observer<GroupDetails>() {
            @Override
            public void onChanged(GroupDetails groupDetails) {
                groupMembersList.clear();
                groupMembersList.addAll(groupDetails.getMembers());
                adapter.notifyDataSetChanged();

            }
        });


    }
    public void removeMember(int groupId){


    }



}
