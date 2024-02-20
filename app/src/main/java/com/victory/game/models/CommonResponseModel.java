package com.victory.game.models;

import com.google.gson.annotations.SerializedName;

public class CommonResponseModel {

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    public CommonResponseModel(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
