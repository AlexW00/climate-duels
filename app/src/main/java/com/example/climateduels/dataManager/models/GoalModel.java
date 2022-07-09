package com.example.climateduels.dataManager.models;


import com.example.climateduels.dataManager.DatabaseCallback;
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
    public void refreshData(DatabaseCallback callback) {
        throw new UnsupportedOperationException("Not implemented");

    }

    @Override
    protected void saveData(DatabaseCallback callback) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
