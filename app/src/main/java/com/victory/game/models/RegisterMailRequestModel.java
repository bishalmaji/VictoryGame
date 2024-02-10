package com.victory.game.models;

public class RegisterMailRequestModel {
    private String mail;
    private String otp;
    private String password;
    private String code;

    public RegisterMailRequestModel(String mail, String otp, String password,String code) {
        this.mail = mail;
        this.otp = otp;
        this.password = password;
        this.code=code;
    }
}


