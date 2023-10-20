package com.victory.game.models;

public class AddWithdrawalRequestModel {
    private String userId;
    private String amount;
    private String bankAccountNo;
    private String ifsc;
    private String upiId;
    private String name;

    public AddWithdrawalRequestModel(String userId, String amount, String bankAccountNo, String ifsc, String upiId, String name) {
        this.userId = userId;
        this.amount = amount;
        this.bankAccountNo = bankAccountNo;
        this.ifsc = ifsc;
        this.upiId = upiId;
        this.name = name;
    }

    // Getters and setters for each field

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getUpiId() {
        return upiId;
    }

    public void setUpiId(String upiId) {
        this.upiId = upiId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
