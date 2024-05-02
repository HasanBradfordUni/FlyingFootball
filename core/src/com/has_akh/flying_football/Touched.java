package com.has_akh.flying_football;

public class Touched {
    private static final int DURATION = 1000;

    private long activatedAt = DURATION;

    public void activate() {
        activatedAt = System.currentTimeMillis();
    }

    public boolean isActive() {
        long activeFor = System.currentTimeMillis() - activatedAt;

        return activeFor >= 0 && activeFor <= DURATION;
    }
}
