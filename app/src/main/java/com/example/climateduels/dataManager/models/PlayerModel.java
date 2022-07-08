package com.example.climateduels.dataManager.models;

import com.example.climateduels.dataManager.DatabaseObject;

public class PlayerModel extends DatabaseObject {

    protected final String name;
    protected int totalScore;

    protected WeeklyChallengeModel weeklyChallenge;

    public PlayerModel(String name, int totalScore, WeeklyChallengeModel weeklyChallenge) {
        this.name = name;
        this.totalScore = totalScore;
        this.weeklyChallenge = weeklyChallenge;
    }

    public PlayerModel() {
        this.name = "Test name";
        this.totalScore = 30;
        this.weeklyChallenge = new WeeklyChallengeModel();
    }

    public String getName() {
        return name;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public WeeklyChallengeModel getWeeklyChallenge() {
        return weeklyChallenge;
    }

    @Override
    public void refreshData() {

    }

    @Override
    protected void saveData() {

    }
}
