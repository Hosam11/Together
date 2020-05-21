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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.together.R;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import static com.example.together.utils.HelperClass.TAG;


public class ChatFragment extends Fragment implements TextWatcher {

    private String name1 = "amr";
    private String name2 = "may";

    private String name;

    private WebSocket webSocket;
    private String SERVER_PATH = "ws://192.168.1.7:3000";
    private EditText messageEdit;
    private View sendBtn, pickImgBtn;
    private RecyclerView recyclerView;
    private int IMAGE_REQUEST_ID = 1;
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

        initiateSocketConnection();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_chat, container, false);
    }

    private void initiateSocketConnection() {
        Log.i(TAG, "ChatFragment -- initiateSocketConnection()  # ");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(SERVER_PATH).build();
        webSocket = client.newWebSocket(request, new SocketListener());

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

    private void initializeView() {
        Log.i(TAG, "ChatFragment -- initializeView()  ");

        messageEdit = getActivity().findViewById(R.id.messageEdit);
//        sendBtn = getActivity().findViewById(R.id.sendBtn);
//        pickImgBtn = getActivity().findViewById(R.id.pickImgBtn);

        recyclerView = getActivity().findViewById(R.id.recyclerView);

        messageAdapter = new MessageAdapter(getLayoutInflater());
        recyclerView.setAdapter(messageAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        messageEdit.addTextChangedListener(this);

        getActivity().findViewById(R.id.ib_send_msg).setOnClickListener(v -> {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("name", name1);
                jsonObject.put("message", messageEdit.getText().toString());

                webSocket.send(jsonObject.toString());

                jsonObject.put("isSent", true);
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

        messageEdit.removeTextChangedListener(this);

        messageEdit.setText("");
//        sendBtn.setVisibility(View.INVISIBLE);
//        pickImgBtn.setVisibility(View.VISIBLE);

        messageEdit.addTextChangedListener(this);

    }

    private class SocketListener extends WebSocketListener {

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            super.onOpen(webSocket, response);

            if (getActivity() != null) {
                Log.i(TAG, "SocketListener -- onOpen() getActivity() != null ");

                getActivity().runOnUiThread(() -> {

                    Toast.makeText(getActivity(),
                            "Socket Connection Successful!",
                            Toast.LENGTH_SHORT).show();

                    initializeView();
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
                        jsonObject.put("isSent", false);

                        messageAdapter.addItem(jsonObject);

                        recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);

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



