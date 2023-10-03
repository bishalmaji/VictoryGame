package com.victory.game.models;

public class RegisterRequestModel {
    private String phone;
    private String otp;
    private String password;

    public RegisterRequestModel(String phone, String otp, String password) {
        this.phone = phone;
        this.otp = otp;
        this.password = password;
    }
}
