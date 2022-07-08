package com.example.climateduels.dataManager.models;

import com.example.climateduels.dataManager.DatabaseObject;

import java.util.ArrayList;

public class WeeklyChallengeModel extends DatabaseObject {
    protected ArrayList<GoalCategoryModel<UserGoalModel>> goalCategories;

    private String playerName;
    private String teamCode;

    public WeeklyChallengeModel(ArrayList<GoalCategoryModel<UserGoalModel>> goalCategories) {
        this.goalCategories = goalCategories;
    }

    // mock data constructor
    public WeeklyChallengeModel() {
        this.goalCategories = new ArrayList();

        this.goalCategories.add(new GoalCategoryModel<UserGoalModel>());
        this.goalCategories.add(new GoalCategoryModel<UserGoalModel>());
    }

    public WeeklyChallengeModel(String playerName, String teamCode) {
        this.playerName = playerName;
        this.teamCode = teamCode;
        initData();
    }

    // Getters

    public ArrayList<GoalCategoryModel<UserGoalModel>> getGoalCategories() {
        return goalCategories;
    }

    @Override
    protected String getTableName() {
        return null;
    }

    @Override
    public void refreshData() {

    }

    @Override
    protected void saveData() {

    }

    @Override
    protected void initData() {

    }
}
