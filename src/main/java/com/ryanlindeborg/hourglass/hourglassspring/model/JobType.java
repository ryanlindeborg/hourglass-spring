package com.ryanlindeborg.hourglass.hourglassspring.model;

public enum JobType {

    CURRENT_JOB("Current Job"),
    FIRST_POST_COLLEGE_JOB("First Post-College Job"),
    DREAM_JOB("Dream Job");

    private String type;

    private JobType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
