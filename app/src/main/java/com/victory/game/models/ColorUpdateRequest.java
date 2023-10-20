package com.victory.game.models;

public class ColorUpdateRequest {
    private String colorName; // Represents the name of the color being updated (e.g., "red", "green", "pink")
    private int value; // Represents the value to update for the specified color

    public ColorUpdateRequest(String colorName, int value) {
        this.colorName = colorName;
        this.value = value;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
