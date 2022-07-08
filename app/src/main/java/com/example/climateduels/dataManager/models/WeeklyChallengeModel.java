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


    public WeeklyChallengeModel(String playerName, String teamCode, ArrayList<GoalCategoryModel<UserGoalModel>> goalCategories) {
        this.playerName = playerName;
        this.teamCode = teamCode;
    }

    private static WeeklyChallengeModel asyncCreateWeeklyChallengeModel (String playerName, String teamCode) {
        ArrayList<GoalCategoryModel<UserGoalModel>> goalCategories = GoalCategoryModel.asyncCreateUserGoalCategoryModel(playerName ,teamCode);
        return new WeeklyChallengeModel(playerName, teamCode, goalCategories);
    }

    // Getters

    public ArrayList<GoalCategoryModel<UserGoalModel>> getGoalCategories() {
        return goalCategories;
    }

    @Override
    public void refreshData(Void callback) {
    }

    @Override
    protected void saveData(Void callback) {

    }

}
