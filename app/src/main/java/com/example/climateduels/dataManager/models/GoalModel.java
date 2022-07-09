package com.example.climateduels.dataManager.models;


import com.example.climateduels.dataManager.DatabaseObject;

public class GoalModel extends DatabaseObject {
    int id;

    String title;
    int targetCount;

    public GoalModel(int id, String title, int targetCount) {
        this.id = id;
        this.title = title;
        this.targetCount = targetCount;
    }

    // Getters

    public String getTitle() {
        return title;
    }

    public int getTargetCount() {
        return targetCount;
    }

    @Override
    public void refreshData(Void callback) {
        throw new UnsupportedOperationException("Not implemented");

    }

    @Override
    protected void saveData(Void callback) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
