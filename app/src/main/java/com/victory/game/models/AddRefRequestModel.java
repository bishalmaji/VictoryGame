package com.victory.game.models;

public class AddRefRequestModel {
    private  String beneficiaryId;
    private  String userId;

    public AddRefRequestModel(String beneficiaryId, String userId) {
        this.beneficiaryId = beneficiaryId;
        this.userId = userId;
    }

}
