package com.victory.game.models;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class GameResultResponseModel {
    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private List<ResultModel> data;

    @SerializedName("message")
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public List<ResultModel> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
