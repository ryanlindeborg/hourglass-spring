package com.ryanlindeborg.hourglass.hourglassspring.model;

public enum Degree {

    MD("MD"),
    PHD("PhD"),
    MBA("MBA"),
    MS("MS"),
    BA("BA"),
    BS("BS"),
    JD("JD"),
    MFA("MFA"),
    MPH("MPH"),
    LLM("LLM"),
    MPA("MPA"),
    DO("DO"),
    PSYD("PsyD"),
    PHARMD("PharmD");

    private String degreeName;

    private Degree(String degreeName) {
        this.degreeName = degreeName;
    }

    @Override
    public String toString() {
        return degreeName;
    }
}
