package com.victory.game.models;

import com.google.gson.annotations.SerializedName;

public class CurrentUserResponseModel {
    private boolean success;

    private ResultUserModel data;

    public boolean isSuccess() {
        return success;
    }

    public ResultUserModel getData() {
        return data;
    }
}


