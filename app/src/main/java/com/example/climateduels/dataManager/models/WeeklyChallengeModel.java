package com.example.climateduels.dataManager.models;

import com.example.climateduels.dataManager.DatabaseObject;

import java.util.ArrayList;

public class WeeklyChallengeModel extends DatabaseObject {
    protected ArrayList<GoalCategoryModel<UserGoalModel>> goalCategories;

    public WeeklyChallengeModel(ArrayList<GoalCategoryModel<UserGoalModel>> goalCategories) {
        this.goalCategories = goalCategories;
    }

    // mock data constructor
    public WeeklyChallengeModel() {
        this.goalCategories = new ArrayList();

        this.goalCategories.add(new GoalCategoryModel<UserGoalModel>());
        this.goalCategories.add(new GoalCategoryModel<UserGoalModel>());
    }

    // Getters

    public ArrayList<GoalCategoryModel<UserGoalModel>> getGoalCategories() {
        return goalCategories;
    }

    @Override
    public void refreshData() {

    }

    @Override
    protected void saveData() {

    }
}
