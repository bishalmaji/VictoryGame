package com.victory.game.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddRecordRequestModel {
    @SerializedName("amount")
    private int amount;

    @SerializedName("gameId")
    private String gameId;

    @SerializedName("winOrLoss")
    private boolean winOrLoss;

    @SerializedName("winNumber")
    private String winNumber;

    @SerializedName("winColor")
    private List<String> winColor;

    @SerializedName("totalAmount")
    private String totalAmount;

    @SerializedName("userId")
    private String userId;

    public AddRecordRequestModel(int amount, String gameId, boolean winOrLoss, String winNumber, List<String> winColor, String totalAmount, String userId) {
        this.amount = amount;
        this.gameId = gameId;
        this.winOrLoss = winOrLoss;
        this.winNumber = winNumber;
        this.winColor = winColor;
        this.totalAmount = totalAmount;
        this.userId = userId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public boolean isWinOrLoss() {
        return winOrLoss;
    }

    public void setWinOrLoss(boolean winOrLoss) {
        this.winOrLoss = winOrLoss;
    }

    public String getWinNumber() {
        return winNumber;
    }

    public void setWinNumber(String winNumber) {
        this.winNumber = winNumber;
    }

    public List<String> getWinColor() {
        return winColor;
    }

    public void setWinColor(List<String> winColor) {
        this.winColor = winColor;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    // Constructor, getters, and setters
}
