package com.victory.game.models;

import java.util.List;

public class ReferralCommonModel {
    private String message;
    private boolean success;
    private List<ReferalResponseModel> data;

    public ReferralCommonModel(String message, boolean success, List<ReferalResponseModel> data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<ReferalResponseModel> getData() {
        return data;
    }

    public void setData(List<ReferalResponseModel> data) {
        this.data = data;
    }
}
