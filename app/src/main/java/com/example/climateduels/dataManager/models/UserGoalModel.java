package com.example.climateduels.dataManager.models;

public class UserGoalModel extends GoalModel {
    int user_goal_id;
    int currentCount;

    public UserGoalModel(int user_goal_id, int goal_id, String title, int targetCount, int currentCount) {
        super(goal_id, title, targetCount);
        this.user_goal_id = user_goal_id;
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