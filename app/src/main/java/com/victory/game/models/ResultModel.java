package com.victory.game.models;

import com.google.gson.annotations.SerializedName;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ResultModel {
    @SerializedName("_id")
    private String id;

    @SerializedName("winColor")
    private List<String> winColor;

    @SerializedName("winShowId")
    private String winShowId;

    @SerializedName("winPrice")
    private String winPrice;

    @SerializedName("winNumber")
    private String winNumber;

    @SerializedName("winTime")
    private String winTime; // Keep it as a string for parsing

    @SerializedName("colorPriceArray")
    private List<String> colorPriceArray;

    @SerializedName("numberPriceArray")
    private List<String> numberPriceArray;

    public String getId() {
        return id;
    }

    public List<String> getWinColor() {
        return winColor;
    }

    public String getWinShowId() {
        return winShowId;
    }

    public String getWinPrice() {
        return winPrice;
    }

    public String getWinNumber() {
        return winNumber;
    }

    public Date getWinTime() {
        // Parse the winTime string into a Date object
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z (zzzz)", Locale.US);
        try {
            return sdf.parse(winTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Handle parsing error as needed
        }
    }

    public List<String> getColorPriceArray() {
        return colorPriceArray;
    }

    public List<String> getNumberPriceArray() {
        return numberPriceArray;
    }
}
