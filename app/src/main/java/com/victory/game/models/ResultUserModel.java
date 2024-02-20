package com.victory.game.models;

import com.google.gson.annotations.SerializedName;

public class ResultUserModel {
    private String uid;
    private String  showId;
    private String name;
    private String phone;
    private  String mail;

    public ResultUserModel(String uid, String showId, String name, String phone, String mail) {
        this.uid = uid;
        this.showId = showId;
        this.name = name;
        this.phone = phone;
        this.mail = mail;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setShowId(String showId) {
        this.showId = showId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUid() {
        return uid;
    }

    public String getShowId() {
        return showId;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getMail() {
        return mail;
    }
}
