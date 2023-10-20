package com.victory.game.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserRecordModel {
    @SerializedName("_id")
    private String _id;


    @SerializedName("amount")
    private int amount;

    @SerializedName("gameId")
    private String gameId;

    @SerializedName("winOrLoss")
    private boolean winOrLoss;

    @SerializedName("winNumber")
    private String winNumber;

    @SerializedName("winColor")
    private String[] winColor;

    @SerializedName("totalAmount")
    private String totalAmount;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public  String[]  getWinColor() {
        return winColor;
    }

    public String getColor(int position) {
        if (winColor != null && position >= 0 && position < winColor.length) {
            return winColor[position];
        }
        return null;
    }
    public void setWinColor( String[] winColor) {
        this.winColor = winColor;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }


    public UserRecordModel(String _id, int amount, String gameId, boolean winOrLoss, String winNumber, String[] winColor, String totalAmount) {
        this._id = _id;
        this.amount = amount;
        this.gameId = gameId;
        this.winOrLoss = winOrLoss;
        this.winNumber = winNumber;
        this.winColor = winColor;
        this.totalAmount = totalAmount;
    }

}
