package com.victory.game.models;

public class ReferalResponseModel {
    private String userId;
    private String beneficiaryId;
    private String timestamp;

    public ReferalResponseModel(String userId, String beneficiaryId, String timestamp) {
        this.userId = userId;
        this.beneficiaryId = beneficiaryId;
        this.timestamp = timestamp;
    }

    public String getUserId() {
        return userId;
    }

    public String getBeneficiaryId() {
        return beneficiaryId;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
