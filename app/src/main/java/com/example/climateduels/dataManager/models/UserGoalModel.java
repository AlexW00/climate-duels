package com.example.climateduels.dataManager.models;

public class UserGoalModel extends GoalModel {
    int currentCount;

    public UserGoalModel(String title, int targetCount, int currentCount) {
        super(title, targetCount);
        this.currentCount = currentCount;
    }

    // mock data constructor
    public UserGoalModel() {
        super();
        this.currentCount = 0;
    }

    // Getters

    public int getCurrentCount() {
        return currentCount;
    }
}