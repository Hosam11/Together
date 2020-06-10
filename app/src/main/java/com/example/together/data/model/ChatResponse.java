package com.example.together.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChatResponse {


    public List<MessageContent> getChatMsgList() {
        return chatMsgList;
    }

    public void setChatMsgList(List<MessageContent> chatMsgList) {
        this.chatMsgList = chatMsgList;
    }

    @SerializedName("response")
   private List<MessageContent> chatMsgList;

     public static class MessageContent {
        // "sender": "hos",
        // "content": "hi bro"
        private String sender;
        private String content;

         public int getSenderID() {
             return senderID;
         }

         @SerializedName("id")
        private int senderID;

         public String getSender() {
             return sender;
         }

         public void setSender(String sender) {
             this.sender = sender;
         }

         public String getContent() {
             return content;
         }

         public void setContent(String content) {
             this.content = content;
         }
     }

}
