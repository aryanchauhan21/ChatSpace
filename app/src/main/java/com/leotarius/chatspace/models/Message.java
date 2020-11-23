package com.leotarius.chatspace.models;

import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("uid")
    String uid;

    @SerializedName("text")
    String text;

    @SerializedName("name")
    String name;

    public Message(String uid, String text, String name) {
        this.uid = uid;
        this.text = text;
        this.name = name;
    }

    public Message() {
    }

    public String getUid() {
        return uid;
    }

    public String getText() {
        return text;
    }

    public String getName() {
        return name;
    }
}
