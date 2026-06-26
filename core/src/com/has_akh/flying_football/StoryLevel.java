package com.has_akh.flying_football;

public class StoryLevel {
    private float[] barrierHeights;
    private float speed;
    private int lives;
    private int levelNumber;
    private StoryModeCategories category;
    private boolean unlocked;
    private boolean completed;

    public StoryLevel(float[] levelBarrierHeight, float levelSpeed, int levelLives,
                      int thisLevelNumber, StoryModeCategories levelCategory, boolean levelUnlocked) {
        barrierHeights = levelBarrierHeight;
        speed = levelSpeed;
        lives = levelLives;
        levelNumber = thisLevelNumber;
        category = levelCategory;
        unlocked = levelUnlocked;
        completed = false;
    }

    public float[] getBarrierHeights() {
        return barrierHeights;
    }

    public float getMaxBarrierHeight() {
        float maxHeight = 0;
        for (int i = 0; i < barrierHeights.length; i++) {
            if (barrierHeights[i] > maxHeight) {
                maxHeight = barrierHeights[i];
            }
        }
        return maxHeight;
    }

    public float getMinBarrierHeight() {
        float minHeight = Float.MAX_VALUE;
        for (int i = 0; i < barrierHeights.length; i++) {
            if (barrierHeights[i] < minHeight) {
                minHeight = barrierHeights[i];
            }
        }
        return minHeight;
    }

    public float getSpeed() {
        return speed;
    }

        return lives;
    }

    public boolean isCompleted() {
        return completed;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public StoryModeCategories getCategory() {
        return category;
    }

    public void setCategory(StoryModeCategories category) {
        this.category = category;
    }

    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    public void setBarrierHeights(float[] newBarrierHeights) {
        barrierHeights = newBarrierHeights;
    }

    public void decrementLives() {
        lives--;
    }

    public void incrementLives() {
        lives++;
    }

    public void setLives(int newLives) {
        lives = newLives;
    }

    public void changeSpeed(float newSpeed) {
        speed = newSpeed;
    }

    public void unlockLevel() {
        unlocked = true;
    }

    public void lockLevel() {
        unlocked = false;
    }

    public void completeLevel() {
        completed = true;
    }
