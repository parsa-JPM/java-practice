package com.example.interview_practice.volvo;

public enum Vehicle {
    CAR(false),
    MOTORBIKE(false),
    Tractor(false),
    Emergency(true),
    Diplomat(true),
    Foreign(true),
    Military(true);

    private boolean isExempted;

    Vehicle(boolean isExempted) {
        this.isExempted = isExempted;
    }

    public boolean isExempted() {
        return isExempted;
    }
}