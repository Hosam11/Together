package com.example.together.group_screens.single_group.chat;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.together.CustomProgressDialog;
import com.example.together.R;
import com.example.together.data.model.ChatResponse;
import com.example.together.data.model.Group;
import com.example.together.data.storage.Storage;
import com.example.together.utils.HelperClass;
import com.example.together.view_model.GroupViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import static com.example.together.utils.HelperClass.GROUP_ID;
import static com.example.together.utils.HelperClass.IS_SEND;
import static com.example.together.utils.HelperClass.TAG;


public class ChatFragment extends Fragment implements TextWatcher {

    final String TAG2 = "lifecycle";
    List<JSONObject> messagesJsonList;
    private Storage userStorage;
    private Storage commonStorage;
    private GroupViewModel groupViewModel;
    private WebSocket webSocket;
    private String SERVER_PATH = "ws://192.168.1.6:3000";
    private EditText messageEdit;
    private View sendBtn, pickImgBtn;
    private RecyclerView recyclerView;
    private int IMAGE_REQUEST_ID = 1;
    private Group savedGroup;
    private MessageAdapter messageAdapter;


    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG2, "##ChatFragment -- onCreateView: ");
        View view = inflater.inflate(R.layout.activity_chat, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        messagesJsonList = new ArrayList<>();

        userStorage = new Storage(Objects.requireNonNull(getContext()));

        commonStorage = new Storage();
        savedGroup = commonStorage.getGroup(getContext());
        Log.i(TAG, "##ChatFragment -- onCreateView: gpID >> " + savedGroup.getGroupID());
        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);

        initializeView(view);

        if (HelperClass.checkInternetState(getContext())) {
            CustomProgressDialog.getInstance(getContext()).show();
            Log.i(TAG, "onCreateView: after CustomProgressDialog.show()");
            groupViewModel.getChatMessages(savedGroup.getGroupID()).observe(this,
                    this::getChatMessagesObserve);
        } else {
            //   CustomProgressDialog.getInstance(this).cancel();
            HelperClass.showAlert("Error", HelperClass.checkYourCon,
                    getContext());
        }

        initiateSocketConnection();
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG2, "##ChatFragment -- onStart: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG2, "onResume: ");
        if (HelperClass.checkInternetState(getContext())) {
            CustomProgressDialog.getInstance(getContext()).show();
            Log.i(TAG, "onCreateView: after CustomProgressDialog.show()");
            groupViewModel.getChatMessages(savedGroup.getGroupID()).observe(this,
                    this::getChatMessagesObserve);
        } else {
            //   CustomProgressDialog.getInstance(this).cancel();
            HelperClass.showAlert("Error", HelperClass.checkYourCon,
                    getContext());
        }
        //  initiateSocketConnection();
    }

    private void getChatMessagesObserve(ChatResponse chatResponse) {
        messagesJsonList.clear();
        for (ChatResponse.MessageContent msg : chatResponse.getChatMsgList()) {
            Log.i(TAG, "##ChatFragment -- getChatMessagesObserve: "
                    + " @@ msgContent >> " + msg.getContent()
                    + " @@ userName >> " + msg.getSender()
                    + " @@ senderID" + msg.getSenderID());
            JSONObject msgJSONObj = new JSONObject();

            try {
                msgJSONObj.put(HelperClass.NAME, msg.getSender());
                msgJSONObj.put(HelperClass.MESSAGE, msg.getContent());
                msgJSONObj.put(IS_SEND, userStorage.getId() == msg.getSenderID());
//                msgJSONObj.put(IS_STORED_MESSAGE, userStorage.getId() == msg.getSenderID());
                Log.i(TAG, "##ChatFragment --  getChatMessagesObserve: msgJSONObj >>  "
                        + msgJSONObj.toString());
                messagesJsonList.add(msgJSONObj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.i(TAG, "##ChatFragment -- getChatMessagesObserve: messagesJsonList.size() >> " + messagesJsonList.size());
        messageAdapter.setMessages(messagesJsonList);
        if (!messagesJsonList.isEmpty()) {
            recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
        }
        //  messageAdapter.notifyDataSetChanged();
        CustomProgressDialog.getInstance(getContext()).cancel();

    }

    private void initiateSocketConnection() {
        Log.i(TAG, "##ChatFragment -- initiateSocketConnection()  # ");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(SERVER_PATH).build();
        webSocket = client.newWebSocket(request, new SocketListener());


    }


    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG2, "##ChatFragment -- onPause: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG2, "onDestroyView: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG2, "onDestroy: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG2, "onDetach: ");
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void initializeView(View view) {
        Log.i(TAG, "##ChatFragment -- initializeView: ");

        messageEdit = view.findViewById(R.id.ed_type_msg);
//        sendBtn = getActivity().findViewById(R.id.sendBtn);
//        pickImgBtn = getActivity().findViewById(R.id.pickImgBtn);

        recyclerView = view.findViewById(R.id.recyclerView);

        messageAdapter = new MessageAdapter(getLayoutInflater());

        recyclerView.setAdapter(messageAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

       /* messageAdapter.setMessages(messagesJsonList);
        messageAdapter.notifyDataSetChanged();*/
        //testDummyData();


        messageEdit.addTextChangedListener(this);

        view.findViewById(R.id.tv_send_msg).setOnClickListener(v -> {
            JSONObject jsonObject = new JSONObject();
            try {
                Log.i(TAG, "ChatFragment -- initializeView: " + "" +
                        "@@ user id  >> " + userStorage.getId() +
                        " -- @@ groupID >> " + commonStorage.getGroup(getContext()).getGroupID() +
                        " -- @@ content >> " + messageEdit.getText().toString());

                jsonObject.put(HelperClass.NAME, commonStorage.getUserName(getContext()));
                jsonObject.put(HelperClass.MESSAGE, messageEdit.getText().toString());
                jsonObject.put(HelperClass.USER_ID, userStorage.getId());
                jsonObject.put(HelperClass.GROUP_ID, commonStorage.getGroup(getContext()).getGroupID());

                //jsonObject.put("content", messageEdit.getText().toString());

                webSocket.send(jsonObject.toString());

                jsonObject.put(IS_SEND, true);

                messageAdapter.addItem(jsonObject);

                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);

                resetMessageEdit();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

      /*  pickImgBtn.setOnClickListener(v -> {

            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");

            startActivityForResult(Intent.createChooser(intent, "Pick image"),
                    IMAGE_REQUEST_ID);

        });*/

    }

    private void resetMessageEdit() {
        Log.i(TAG, "##ChatFragment -- resetMessageEdit: ");
        messageEdit.removeTextChangedListener(this);

        messageEdit.setText("");
//        sendBtn.setVisibility(View.INVISIBLE);
//        pickImgBtn.setVisibility(View.VISIBLE);

        messageEdit.addTextChangedListener(this);

    }

    private class SocketListener extends WebSocketListener {

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            Log.i(TAG, "##ChatFragment -- onOpen: ");
            super.onOpen(webSocket, response);

            if (getActivity() != null) {
                Log.i(TAG, "SocketListener -- onOpen() getActivity() != null ");

                getActivity().runOnUiThread(() -> {

                    Toast.makeText(getActivity(),
                            "Connected ",
                            Toast.LENGTH_SHORT).show();

                    //initializeView();
                });
            } else {
                Log.i(TAG, "SocketListener -- onOpen() getActivity() == null ");
            }

        }


        @Override
        public void onMessage(WebSocket webSocket, String text) {
            super.onMessage(webSocket, text);

            if (getActivity() != null) {
                Log.i(TAG, "SocketListener -- onMessage() getActivity() != null ");

                getActivity().runOnUiThread(() -> {

                    try {
                        JSONObject jsonObject = new JSONObject(text);
                        jsonObject.put(IS_SEND, false);
                        String msg = jsonObject.getString(GROUP_ID);
                        // if recived message in that belong to your group
                        if (msg.equals(String.valueOf(savedGroup.getGroupID()))) {
                            Log.i(TAG, "onMessage: msg is in same group");
                            messageAdapter.addItem(jsonObject);
                            recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
                        } else {
                            Log.i(TAG, "onMessage: msg is not in same group");
                        }
                       /* messageAdapter.addItem(jsonObject);
                        recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);*/
                        Log.i(TAG, "onMessage: text received >> " + text);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
            } else {
                Log.i(TAG, "SocketListener -- onMessage() getActivity() == null ");

            }


        }
    }


}



