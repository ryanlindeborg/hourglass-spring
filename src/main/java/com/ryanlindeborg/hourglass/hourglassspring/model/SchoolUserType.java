package com.ryanlindeborg.hourglass.hourglassspring.model;

public enum SchoolUserType {

    COLLEGE("College"),
    POST_GRAD("Post-Grad");

    private String type;

    private SchoolUserType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
