package com.example.together.group_screens.single_group;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.together.Adapters.AboutMembersRecyclerAdapter;
import com.example.together.CustomProgressDialog;
import com.example.together.R;
import com.example.together.data.model.GeneralResponse;
import com.example.together.data.model.Group;
import com.example.together.data.model.User;
import com.example.together.data.storage.Storage;
import com.example.together.group_screens.EditGroupInfo;
import com.example.together.utils.HelperClass;
import com.example.together.view_model.UsersViewModel;

import java.util.ArrayList;
import java.util.Objects;

import static com.example.together.utils.HelperClass.TAG;

public class AboutGroupFragment extends Fragment {

    RecyclerView members_recycler;
    ArrayList<User> groupMembersList = new ArrayList<>();
    AboutMembersRecyclerAdapter adapter;
    ImageView groupImgView;
    TextView nameTv;
    TextView groupDescriptionTv;
    TextView editGroupTv;
    Button leaveBtn;
    UsersViewModel userViewModel;
    Storage storage;


    boolean isAdmin = false;
    Group receivedGroup;

    public AboutGroupFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        storage = new Storage(getContext());
//        UserGroup receivedGroup=(UserGroup)getActivity().getIntent().getSerializableExtra("group");
        Storage s = new Storage();

        receivedGroup = s.getGroup(getContext());
// =======
//         Group receivedGroup = s.getGroup(getContext());
// >>>>>>> master

        Log.i(TAG, "onCreateView: id >> ");

        View view = inflater.inflate(R.layout.fragment_about_group,
                container, false);

        members_recycler = view.findViewById(R.id.members_recycler);
        groupImgView = view.findViewById(R.id.group_img_tv);
        nameTv = view.findViewById(R.id.name_tv);
        groupDescriptionTv = view.findViewById(R.id.group_description_tv);
        editGroupTv = view.findViewById(R.id.edit_group_tv);
        leaveBtn = view.findViewById(R.id.leave_btn);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        members_recycler.setLayoutManager(layoutManager);


        if (receivedGroup.getAdminID() == storage.getId()) {
            isAdmin = true;
            editGroupTv.setVisibility(View.VISIBLE);
            editGroupTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO hossam Edit Group
                    Intent goEditGroup = new Intent(getContext(), EditGroupInfo.class);

                    getContext().startActivity(goEditGroup);
                    Toast.makeText(getContext(), "Here", Toast.LENGTH_LONG).show();
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
                CustomProgressDialog.getInstance(getContext()).show();

                removeItem(position, receivedGroup.getGroupID());
            }
        });


        nameTv.setText(receivedGroup.getGroupName());
        groupDescriptionTv.setText(receivedGroup.getGroupDesc());
        //  groupImgView TODO HERE Getting Image

        if (receivedGroup.getImage() != null) {
//        groupImgView.setImageBitmap(HelperClass.decodeBase64(receivedGroup.getPhoto()));

            Glide.with(getContext()).load(receivedGroup.getImage()).into(groupImgView);
            Log.i(TAG, "AboutGroupFragment onCreateView: imgUrl" + receivedGroup.getImage());
        }


        userViewModel = new ViewModelProvider(this).get(UsersViewModel.class);

        if (HelperClass.checkInternetState(getContext())) {
            CustomProgressDialog.getInstance(getContext()).show();

            getGroupDetails(receivedGroup.getGroupID());
        } else {
            CustomProgressDialog.getInstance(getContext()).cancel();
            HelperClass.showAlert("Error", HelperClass.checkYourCon, getContext());


        }


        // Inflate the layout for this fragment

        leaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (HelperClass.checkInternetState(getContext())) {
                    //TODO:// represents Gid

                    userViewModel.leaveGroup(receivedGroup.getGroupID(), storage.getId(), storage.getToken()).observe(getViewLifecycleOwner(), new Observer<GeneralResponse>() {
                        @Override
                        public void onChanged(GeneralResponse response) {
                            if (response != null) {
                                CustomProgressDialog.getInstance(getContext()).show();

                                Toast.makeText(getContext(), response.response, Toast.LENGTH_LONG).show();
                                Objects.requireNonNull(getActivity()).finish();
                            } else {
                                CustomProgressDialog.getInstance(getContext()).cancel();
                                HelperClass.showAlert("Error", HelperClass.SERVER_DOWN, getContext());


                            }

                        }
                    });
                } else {
                    CustomProgressDialog.getInstance(getContext()).cancel();
                    HelperClass.showAlert("Error", HelperClass.checkYourCon, getContext());


                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    public void removeItem(int position, int groupId) {
        if (HelperClass.checkInternetState(getContext())) {
            userViewModel.removeMemberFromGroup(groupId, groupMembersList.get(position).getId(), storage.getId(), storage.getToken()).observe(this, new Observer<GeneralResponse>() {
                @Override
                public void onChanged(GeneralResponse response) {
                    if (response != null) {

                        Toast.makeText(getContext(), response.response, Toast.LENGTH_LONG).show();

                        groupMembersList.remove(position);
                        adapter.notifyItemRemoved(position);
                        CustomProgressDialog.getInstance(getContext()).cancel();

                    } else {
                        HelperClass.showAlert("Error", HelperClass.SERVER_DOWN, getContext());
                        CustomProgressDialog.getInstance(getContext()).cancel();


                    }

                }
            });

        } else {
            HelperClass.showAlert("Error", HelperClass.checkYourCon, getContext());
            CustomProgressDialog.getInstance(getContext()).cancel();

        }


    }

    public void getGroupDetails(int groupId) {
        userViewModel.getSpecificGroupDetails(groupId, storage.getToken())
                .observe(this, new Observer<Group>() {
                    @Override
                    public void onChanged(Group groupDetails) {
                        if (groupDetails != null) {
                            groupMembersList.clear();
                            groupMembersList.addAll(groupDetails.getMembers());
                            adapter.notifyDataSetChanged();
                            CustomProgressDialog.getInstance(getContext()).cancel();

                        } else {
                            HelperClass.showAlert("Error", HelperClass.SERVER_DOWN, getContext());
                            CustomProgressDialog.getInstance(getContext()).cancel();

                        }
                    }
                });


    }

    public void removeMember(int groupId) {


    }


}
