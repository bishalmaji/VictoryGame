package com.victory.game.models;

public class HomeItemModel {
    private String description;
    private String price;
    private int profile;

    public HomeItemModel(String description, String price, int profile) {
        this.description = description;
        this.price = price;
        this.profile = profile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getProfile() {
        return profile;
    }

    public void setProfile(int profile) {
        this.profile = profile;
    }
}
