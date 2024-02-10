package com.victory.game.models;

public class RegisterRequestModel {
    private String phone;
    private String otp;
    private String password;
    private  String code;

    public RegisterRequestModel(String phone, String otp, String password, String code) {
        this.phone = phone;
        this.otp = otp;
        this.password = password;
        this.code = code;
    }
}
