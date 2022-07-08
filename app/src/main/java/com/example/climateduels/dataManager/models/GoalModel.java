package com.example.climateduels.dataManager.models;


public class GoalModel {
    int id;

    String title;
    int targetCount;

    public GoalModel(int id, String title, int targetCount) {
        this.id = id;
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
