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

import com.example.together.Adapters.CreateDialog;
import com.example.together.Adapters.POJO;
import com.example.together.R;
import com.woxthebox.draglistview.DragItemAdapter;

import java.util.ArrayList;

public class ItemAdapter extends DragItemAdapter<POJO, ItemAdapter.ViewHolder> {

    private int mLayoutId;
    private int mGrabHandleId;
    private boolean mDragOnLongPress;
    public ArrayList<POJO> list = new ArrayList<>();
    public Context context;

    ItemAdapter(ArrayList<POJO> list, int layoutId, int grabHandleId, boolean dragOnLongPress, Context context) {
        mLayoutId = layoutId;
        mGrabHandleId = grabHandleId;
        mDragOnLongPress = dragOnLongPress;
        this.list=list;
        this.context=context;
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
        holder.title.setText(mItemList.get(position).title);
        holder.description.setText(mItemList.get(position).description);
        holder.itemView.setTag(mItemList.get(position));
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
                ItemAdapter.this.notifyDataSetChanged();
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateDialog editDialoge = new CreateDialog("editTask",ItemAdapter.this,position);
                editDialoge.show(((AppCompatActivity)ItemAdapter.this.context).getSupportFragmentManager(),"example");
            }
        });
    }

    @Override
    public long getUniqueItemId(int position) {
        return mItemList.get(position).image;
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
    public void editTask(POJO pojo,int currentPosition){
        list.set(currentPosition,pojo);
    }
}
