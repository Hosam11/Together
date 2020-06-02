package com.example.together.group_screens.single_group;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.together.ToDoListPachage.BoardFragment;
import com.example.together.ToDoListPachage.ToDoListMain;
import com.example.together.group_screens.single_group.chat.ChatFragment;

public class GroupPagerAdapter extends FragmentStateAdapter {

    Context context;
    public GroupPagerAdapter(@NonNull FragmentActivity fragment , Context context) {
        super(fragment);
        this.context = context;

    }


    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 1:
                return new ChatFragment();
            case 2:
                return new AboutGroupFragment();
            default:
                Intent i = new Intent(context, ToDoListMain.class);
//                context.startActivity(i);
                return new BoardFragment();
//                return null;
//                return new  ToDoListFragment();
        }

    }




    @Override
    public int getItemCount() {
        return 3;
    }
}

