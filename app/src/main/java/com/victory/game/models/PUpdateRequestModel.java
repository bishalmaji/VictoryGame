package com.victory.game.models;

public class PUpdateRequestModel {
    private String tId;

    public PUpdateRequestModel(String tId) {
        this.tId = tId;
    }

    public String gettId() {
        return tId;
    }

    public void settId(String tId) {
        this.tId = tId;
    }
}
