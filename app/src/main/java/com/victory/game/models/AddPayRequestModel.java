package com.victory.game.models;

public class AddPayRequestModel {
    private String userId;
    private String pName;
    private String pType;
    private String amount;
    private String paymentMethod;
    private String transactionId;
    private String status;

    public AddPayRequestModel(String userId, String pName, String pType, String amount, String paymentMethod, String transactionId, String status) {
        this.userId = userId;
        this.pName = pName;
        this.pType = pType;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.transactionId = transactionId;
        this.status = status;
    }

}


