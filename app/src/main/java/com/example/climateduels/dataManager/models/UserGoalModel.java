package com.example.climateduels.dataManager.models;

public class UserGoalModel extends GoalModel {
    int user_goal_id;
    int currentCount;

    public UserGoalModel(int user_goal_id, int goal_id, String title, int targetCount, int currentCount) {
        super(goal_id, title, targetCount);
        this.user_goal_id = user_goal_id;
        this.currentCount = currentCount;
    }

    // Getters

    public int getCurrentCount() {
        return currentCount;
    }

    public int getPercentageComplete() {
        return (int) (((double) currentCount / (double) targetCount) * 100);
    }

    // Overrides
    @Override
    public void refreshData(Void callback) {
        throw new UnsupportedOperationException("Not implemented");

    }

    @Override
    protected void saveData(Void callback) {
        throw new UnsupportedOperationException("Not implemented");

    }
}