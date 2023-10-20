package com.victory.game.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class UserRecordResponseModel {

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<UserRecordModel> data;

    public UserRecordResponseModel(boolean success, String message, List<UserRecordModel> data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<UserRecordModel> getData() {
        return data;
    }

    public void setData(List<UserRecordModel> data) {
        this.data = data;
    }
}
