package com.victory.game.models;

public class LoginRequestModel {
    private String phone;
    private String password;

    public LoginRequestModel(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }
}
