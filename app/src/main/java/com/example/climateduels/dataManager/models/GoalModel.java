package com.example.climateduels.dataManager.models;


public class GoalModel {
    String title;
    int targetCount;

    public GoalModel(String title, int targetCount) {
        this.title = title;
        this.targetCount = targetCount;
    }

    public GoalModel() {
        this.title = "Test title";
        this.targetCount = 5;
    }

    // Getters

    public String getTitle() {
        return title;
    }

    public int getTargetCount() {
        return targetCount;
    }
}
