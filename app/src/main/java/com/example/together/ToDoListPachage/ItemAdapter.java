/*
 * Copyright 2014 Magnus Woxblom
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.together.ToDoListPachage;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.together.Adapters.CreateDialog;
import com.example.together.Adapters.POJO;
import com.example.together.CustomProgressDialog;
import com.example.together.R;
import com.example.together.data.model.ListTask;
import com.example.together.data.storage.Storage;
import com.example.together.utils.HelperClass;
import com.example.together.view_model.UserViewModel;
import com.woxthebox.draglistview.DragItemAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class ItemAdapter extends DragItemAdapter<ListTask, ItemAdapter.ViewHolder> {

    private int mLayoutId;
    private int mGrabHandleId;
    private boolean mDragOnLongPress;
    public ArrayList<ListTask> list = new ArrayList<>();
    public Context context;
    BoardFragment boardFragment;
    Storage storage;

    public void setList(ArrayList<ListTask> list) {
        this.list = list;
        setItemList(list);
    }

    ItemAdapter(ArrayList<ListTask> list, int layoutId, int grabHandleId, boolean dragOnLongPress, Context context,BoardFragment boardFragment) {
        mLayoutId = layoutId;
        mGrabHandleId = grabHandleId;
        mDragOnLongPress = dragOnLongPress;
        this.list=list;
        this.context=context;
        this.boardFragment=boardFragment;
        storage = new Storage(Objects.requireNonNull(context));
        setItemList(list);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        holder.title.setText(mItemList.get(position).getTitle());
        holder.description.setText(mItemList.get(position).getDescription());
        holder.itemView.setTag(mItemList.get(position));
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (HelperClass.checkInternetState(context)){
                    CustomProgressDialog customProgressDialog = new CustomProgressDialog(boardFragment.getContext());
                    customProgressDialog.show();
                    UserViewModel userViewModel = new ViewModelProvider(boardFragment).get(UserViewModel.class);
                userViewModel.deleteTask(list.get(position).getId(), storage.getToken()).observe(boardFragment, deleteTaskresp -> {
                    if (deleteTaskresp.response.equals(HelperClass.deleteTaskSuccess)) {
                        Toast.makeText(context, HelperClass.deleteTaskSuccess, Toast.LENGTH_SHORT).show();
                        TextView itemCount1 = boardFragment.mBoardView.getHeaderView(0).findViewById(R.id.item_count);
                        itemCount1.setText(String.valueOf(boardFragment.toDoList.size()));
                        customProgressDialog.cancel();
                        list.remove(position);
                        ItemAdapter.this.notifyDataSetChanged();
                    } else {
                        Toast.makeText(context, deleteTaskresp.response, Toast.LENGTH_SHORT).show();
                    }

                });

            }
                else{
                    HelperClass.showAlert("Error","Please check your internet connection",context);
                }
                }

        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(HelperClass.checkInternetState(context)) {
                    CreateDialog editDialoge = new CreateDialog("editTask", ItemAdapter.this, position, list.get(position).getId());
                    editDialoge.show(((AppCompatActivity) ItemAdapter.this.context).getSupportFragmentManager(), "example");
                }
                else {
                    HelperClass.showAlert("Error","Please check your internet connection",context);

                }
            }
        });
    }

    @Override
    public long getUniqueItemId(int position) {
        return mItemList.get(position).getId();
    }

    class ViewHolder extends DragItemAdapter.ViewHolder {
        TextView title;
        TextView description;
        FrameLayout delete;
        FrameLayout edit;

        ViewHolder(final View itemView) {
            super(itemView, mGrabHandleId, mDragOnLongPress);
            title = (TextView) itemView.findViewById(R.id.task_title);
            description = (TextView) itemView.findViewById(R.id.task_description);
            delete= itemView.findViewById(R.id.task_delete);
            edit = itemView.findViewById(R.id.task_edit);
        }

        @Override
        public void onItemClicked(View view) {
            Toast.makeText(view.getContext(), "Item clicked", Toast.LENGTH_SHORT).show();
        }

        @Override
        public boolean onItemLongClicked(View view) {
            Toast.makeText(view.getContext(), "Item long clicked", Toast.LENGTH_SHORT).show();
            return true;
        }


    }
    public void editTask(ListTask task,int currentPosition){
       UserViewModel userViewModel = new ViewModelProvider(boardFragment).get(UserViewModel.class);
       if(HelperClass.checkInternetState(context)) {
           CustomProgressDialog customProgressDialog = new CustomProgressDialog(boardFragment.getContext());
           customProgressDialog.show();
           userViewModel.editTask(list.get(currentPosition).getId(), task, storage.getToken()).observe(boardFragment, addTaskResp -> {
               if (addTaskResp.response.equals(HelperClass.updatedTaskSuccess)) {
                   Toast.makeText(context, HelperClass.updatedTaskSuccess, Toast.LENGTH_SHORT).show();
                   list.set(currentPosition, task);
                   this.notifyDataSetChanged();
                   customProgressDialog.cancel();
               } else {
                   Toast.makeText(context, addTaskResp.response, Toast.LENGTH_SHORT).show();
               }
           });

       }
       else {
           HelperClass.showAlert("Error","Please check your internet connection",context);
       }
    }
}
