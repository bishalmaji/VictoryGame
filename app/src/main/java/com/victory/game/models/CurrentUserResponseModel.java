package com.victory.game.models;

import com.google.gson.annotations.SerializedName;

public class CurrentUserResponseModel {
    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private ResultUserModel data;

    public CurrentUserResponseModel(boolean success, ResultUserModel data) {
        this.success = success;
        this.data = data;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setData(ResultUserModel data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public ResultUserModel getData() {
        return data;
    }
}


