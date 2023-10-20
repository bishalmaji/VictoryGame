package com.victory.game.models;

import com.google.gson.annotations.SerializedName;

public class ResultUserModel {
    @SerializedName("uid")
    private String uid; // This field represents the MongoDB-generated ID

    @SerializedName("name")
    private String name;

    @SerializedName("phone")
    private String phone;

    @SerializedName("amount")
    private int amount;// Assuming 'amount' is an integer, change the type as needed
    @SerializedName("referal")

    private String referal;

    public String getReferal() {
        return referal;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public int getAmount() {
        return amount;
    }
}
