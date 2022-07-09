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
        this.goalCategories = goalCategories;
    }

    public static WeeklyChallengeModel asyncCreateWeeklyChallengeModel (String playerName, String teamCode) {
        ArrayList<GoalCategoryModel<UserGoalModel>> goalCategories = GoalCategoryModel.asyncCreateUserGoalCategoryModel(playerName ,teamCode);
        return new WeeklyChallengeModel(playerName, teamCode, goalCategories);
    }

    // Getters

    public ArrayList<GoalCategoryModel<UserGoalModel>> getGoalCategories() {
        return goalCategories;
    }

    public int getTotalScore() {
        float totalScore = 0;
        int numberOfGoals = 0;
        for (GoalCategoryModel<UserGoalModel> goalCategory : goalCategories) {
            totalScore += goalCategory.getGoals().get(0).getPercentageComplete();
            numberOfGoals++;
        }
        return (int) (totalScore / numberOfGoals);
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
