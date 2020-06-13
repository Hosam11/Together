package com.example.together.group_screens.single_group.chat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.together.CustomProgressDialog;
import com.example.together.R;
import com.example.together.data.model.GeneralResponse;
import com.example.together.data.model.Group;
import com.example.together.data.storage.Storage;
import com.example.together.utils.HelperClass;
import com.example.together.view_model.GroupViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.together.utils.HelperClass.MSG_DELETED_SUCCUESS;

public class MessageAdapter extends RecyclerView.Adapter {

    private static final int TYPE_MESSAGE_SENT = 0;
    private static final int TYPE_MESSAGE_RECEIVED = 1;
    private static final int TYPE_IMAGE_SENT = 2;
    private static final int TYPE_IMAGE_RECEIVED = 3;
    Storage groupStoarge;
    Storage userStoarge;
    Context context;
    GroupViewModel groupViewModel;
    Group curGroup;

    private LayoutInflater inflater;
    private List<JSONObject> messages = new ArrayList<>();

    public MessageAdapter(LayoutInflater inflater, Context context) {
        this.inflater = inflater;
        this.context = context;
        groupViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(GroupViewModel.class);
    }

    public void setMessages(List<JSONObject> messages) {
        Log.i(HelperClass.TAG, "##MessageAdapter --setMessages: size "
                + messages.size());
        this.messages = messages;
        notifyDataSetChanged();
    }

/*    private class SentImageHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public SentImageHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
        }
    }*/

    @Override
    public int getItemViewType(int position) {

        JSONObject message = messages.get(position);

        try {
            if (message.getBoolean(HelperClass.IS_SEND)) {

                if (message.has(HelperClass.MESSAGE))
                    return TYPE_MESSAGE_SENT;
                else
                    return TYPE_IMAGE_SENT;
            } else {
                if (message.has(HelperClass.MESSAGE))
                    return TYPE_MESSAGE_RECEIVED;
                else
                    return TYPE_IMAGE_RECEIVED;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        Log.i(HelperClass.TAG, "##MessageAdapter -- onCreateViewHolder: ");
        switch (viewType) {
            case TYPE_MESSAGE_SENT:
                view = inflater.inflate(R.layout.item_sent_message, parent, false);
                return new SentMessageHolder(view);
            case TYPE_MESSAGE_RECEIVED:
                view = inflater.inflate(R.layout.item_received_message, parent, false);
                return new ReceivedMessageHolder(view);

            /*
            case TYPE_IMAGE_SENT:
                view = inflater.inflate(R.layout.item_sent_image, parent, false);
                return new SentImageHolder(view);
            case TYPE_IMAGE_RECEIVED:
                view = inflater.inflate(R.layout.item_received_photo, parent, false);
                return new ReceivedImageHolder(view);
            */

        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        holder.itemView.setOnLongClickListener(v -> {
            userStoarge = new Storage(context);
            groupStoarge = new Storage();
            curGroup = groupStoarge.getGroup(context);
            if (userStoarge.getId() == curGroup.getAdminID()) {
                showYesNoAlert("Wraning",
                        "Are you sure you want to delete that message?", position);
            } else {
                Toast.makeText(context, "You Not The Admin to Remove This Message", Toast.LENGTH_SHORT).show();
            }
            return true;
        });


        JSONObject message = messages.get(position);
        Log.i(HelperClass.TAG, "##MessageAdapter -- onBindViewHolder: ");

        try {
            if (message.getBoolean(HelperClass.IS_SEND)) {

                if (message.has(HelperClass.MESSAGE)) {

                    SentMessageHolder messageHolder = (SentMessageHolder) holder;
                    messageHolder.messageTxt.setText(message.getString(HelperClass.MESSAGE));
//                    messageHolder.tvSenderName.setText(message.getString("name"));
                } else {
              /*      SentImageHolder imageHolder = (SentImageHolder) holder;
                    Bitmap bitmap = getBitmapFromString(message.getString("image"));
                    imageHolder.imageView.setImageBitmap(bitmap);*/
                }
            } else {
                if (message.has(HelperClass.MESSAGE)) {
                    ReceivedMessageHolder messageHolder = (ReceivedMessageHolder) holder;
                    messageHolder.nameTxt.setText(message.getString(HelperClass.NAME));
                    messageHolder.messageTxt.setText(message.getString(HelperClass.MESSAGE));
                } else {

                    ReceivedImageHolder imageHolder = (ReceivedImageHolder) holder;
                    imageHolder.nameTxt.setText(message.getString(HelperClass.NAME));

                    Bitmap bitmap = getBitmapFromString(message.getString("image"));
                    imageHolder.imageView.setImageBitmap(bitmap);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void removeMsg(int position) {

        Log.i(HelperClass.TAG, "removeMsg: he is the admin ");
        try {
            CustomProgressDialog.getInstance(context).show();
            int msgID = (messages.get(position).getInt(HelperClass.MSG_ID));
            Log.i(HelperClass.TAG, "removeMsg: msgID >> " + msgID);
            groupViewModel.deleteChatMsg(msgID, curGroup.getAdminID(), userStoarge.getToken())
                    .observe((LifecycleOwner) context,
                            generalRes -> deleteMsgObserve(position, generalRes));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(HelperClass.TAG, "onBindViewHolder: msg clicked");
    }

    private void deleteMsgObserve(int position, GeneralResponse generalRes) {
        if (generalRes.response.equals(MSG_DELETED_SUCCUESS)) {
            Log.i(HelperClass.TAG, "deleteMsgObserve: " + generalRes.response);
            Toast.makeText(context, generalRes.response, Toast.LENGTH_SHORT).show();
            messages.remove(position);
            MessageAdapter.this.notifyDataSetChanged();
            CustomProgressDialog.getInstance(context).cancel();

        } else {
            CustomProgressDialog.getInstance(context).cancel();
            Toast.makeText(context, generalRes.response, Toast.LENGTH_SHORT).show();


        }

    }

    public void showYesNoAlert(String description, String msg, int listPostion) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View alertView = inflater.inflate(R.layout.custom_yes_no_dialouge, null);
        builder.setView(alertView);
        TextView alertDescription = alertView.findViewById(R.id.alert_description_edit_text);
        TextView alertMessage = alertView.findViewById(R.id.alert_message_edit_text);
        alertDescription.setText(description);
        alertMessage.setText(msg);
        TextView okBtn = alertView.findViewById(R.id.ok_button);
        TextView cancelBtn = alertView.findViewById(R.id.cancle_btn);

        AlertDialog alertDialog = builder.create();

        cancelBtn.setOnClickListener(v -> alertDialog.cancel());
        okBtn.setOnClickListener(v -> {
            alertDialog.cancel();
//                logout();
            removeMsg(listPostion);
        });
        alertDialog.show();

    }


    private Bitmap getBitmapFromString(String image) {

        byte[] bytes = Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void addItem(JSONObject jsonObject) {
        messages.add(jsonObject);
        notifyDataSetChanged();
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {

        TextView messageTxt;
        TextView tvSenderName;

        public SentMessageHolder(@NonNull View itemView) {
            super(itemView);

            messageTxt = itemView.findViewById(R.id.sentTxt);
//            tvSenderName = itemView.findViewById(R.id.tv_name_sender);
        }
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {

        TextView nameTxt, messageTxt;

        public ReceivedMessageHolder(@NonNull View itemView) {
            super(itemView);

            nameTxt = itemView.findViewById(R.id.nameTxt);
            messageTxt = itemView.findViewById(R.id.receivedTxt);
        }
    }

    private class ReceivedImageHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView nameTxt;

        public ReceivedImageHolder(@NonNull View itemView) {
            super(itemView);

//            imageView = itemView.findViewById(R.id.imageView);
            nameTxt = itemView.findViewById(R.id.nameTxt);

        }
    }

}
