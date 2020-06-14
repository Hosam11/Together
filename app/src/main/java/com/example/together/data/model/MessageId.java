package com.example.together.data.model;

import com.google.gson.annotations.SerializedName;

public class MessageId {
    @SerializedName("msg_id")
    String messageID;

    public MessageId(String messageID) {
        this.messageID = messageID;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }
}
