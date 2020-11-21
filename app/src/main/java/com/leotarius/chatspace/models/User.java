package com.leotarius.chatspace.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class User {
    @SerializedName("uid")
    String uid;

    @SerializedName("name")
    String name;

    @SerializedName("email")
    String email;

    @SerializedName("rooms")
    ArrayList<String> rooms;

    public User(String uid, String name, String email) {
        this.uid = uid;
        this.name = name;
        this.email = email;
    }

    public ArrayList<String> getRooms() {
        return rooms;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
