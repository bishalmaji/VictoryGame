package com.victory.game.models;

public class LoginMailRequestModel {
    private String mail;
    private String password;

    public LoginMailRequestModel(String mail, String password) {
        this.mail = mail;
        this.password = password;
    }
}