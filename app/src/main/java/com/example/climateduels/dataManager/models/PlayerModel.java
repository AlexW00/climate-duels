package com.example.climateduels.dataManager.models;

import com.example.climateduels.dataManager.DataManager;
import com.example.climateduels.dataManager.DatabaseObject;
import com.example.climateduels.database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PlayerModel extends DatabaseObject {

    protected final String name;
    protected final String teamCode;

    protected boolean isAdmin;
    protected int totalScore;
    protected WeeklyChallengeModel weeklyChallenge;



    public PlayerModel(String name, int totalScore, String teamCode, boolean isAdmin, WeeklyChallengeModel weeklyChallenge) {
        this.name = name;
        this.teamCode = teamCode;
        this.isAdmin = isAdmin;
        this.totalScore = totalScore;
        this.weeklyChallenge = weeklyChallenge;
    }

    public PlayerModel() {
        this.name = "Test name";
        this.teamCode ="abcde";
        this.isAdmin = false;
        this.totalScore = 30;
        this.weeklyChallenge = new WeeklyChallengeModel();
    }

    public PlayerModel(String name, String teamCode) {
        this.name = name;
        this.teamCode = teamCode;
        weeklyChallenge = new WeeklyChallengeModel();
        initData();
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
    protected String getTableName() {
        return "climate_duels.\"players\"";
    }

    @Override
    public void refreshData() {
        initData();
        this.weeklyChallenge.refreshData();
    }

    @Override
    protected void saveData() {

    }

    @Override
    protected void initData() {
        String sql = "SELECT * FROM " + getTableName() + " WHERE team_code = ? AND player_name = ?";
        try {
            PreparedStatement statement = DataManager.getConnection().prepareStatement(sql);
            statement.setString(1, this.teamCode);
            statement.setString(2, this.name);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                this.totalScore = rs.getInt("score");
                this.isAdmin = rs.getBoolean("isAdmin");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
