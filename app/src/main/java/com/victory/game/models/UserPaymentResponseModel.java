package com.victory.game.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserPaymentResponseModel {
   private String message;
   private boolean success;

   private List<UserPaymentModel> data;

    public UserPaymentResponseModel(String message, boolean success, List<UserPaymentModel> data) {
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

    public List<UserPaymentModel> getData() {
        return data;
    }

    public void setData(List<UserPaymentModel> data) {
        this.data = data;
    }
}
