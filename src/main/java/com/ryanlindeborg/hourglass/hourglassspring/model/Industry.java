package com.ryanlindeborg.hourglass.hourglassspring.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Industry {

    AEROSPACE("Aerospace"),
    AGRICTULRE("Agriculture"),
    CHEMICALS_AND_PHARMACEUTICALS("Chemicals and Pharmaceuticals"),
    SOFTWARE_AND_COMPUTERS("Software and Computers"),
    CONSTRUCTION("Construction"),
    DEFENSE("Defense"),
    ENERGY("Energy"),
    ENTERTAINMENT("Entertainment"),
    FOOD("Food"),
    HEALTHCARE("Healthcare"),
    HOSPITALITY("Hospitality"),
    INFORMATION("Information"),
    INSURANCE("Insurance"),
    MANUFACTURING("Manufacturing"),
    MEDIA("Media"),
    TELECOMMUNICATIONS("Telecommunications"),
    FOOD_AND_BEVERAGE("Food and Beverage"),
    TRANSPORATION("Transportation"),
    FINANCE("Finance");

    private String industryName;

    private Industry(String industryName) {
        this.industryName = industryName;
    }

    @JsonValue
    @Override
    public String toString() {
        return industryName;
    }
}
