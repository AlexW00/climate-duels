package com.example.climateduels.dataManager.models;

import com.example.climateduels.dataManager.DataManager;
import com.example.climateduels.dataManager.DatabaseObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class TeamModel extends DatabaseObject {
    private String name;
    private String code;

    private ArrayList<PlayerModel> players;

    private ArrayList<GoalCategoryModel<GoalModel>> goalCategories;

    private TeamModel(String name, String code, ArrayList<PlayerModel> players, ArrayList<GoalCategoryModel<GoalModel>> goalCategories) {
        this.name = name;
        this.code = code;
        this.players = players;
        this.goalCategories = goalCategories;
    }

    // DB Queries
    public static TeamModel asyncCreateTeamModel(String code) {
        String name = asyncGetTeamName(code);
        ArrayList<PlayerModel> players = PlayerModel.asyncCreatePlayerModel(code);
        ArrayList<GoalCategoryModel<GoalModel>> goalCategories = GoalCategoryModel.asyncCreateGoalCategoryModel(code);
        return new TeamModel(name, code, players, goalCategories);
    }

    private static String asyncGetTeamName(String code) {
        String sql = "SELECT name FROM teams WHERE code=?";

        try {
            PreparedStatement statement = DataManager.getConnection().prepareStatement(sql);
            statement.setString(1, code);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) return rs.getString("name");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    // Getters
    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public ArrayList<GoalCategoryModel<GoalModel>> getGoalCategories() {
        return goalCategories;
    }

    public ArrayList<PlayerModel> getPlayers() {
        return players;
    }

    // Special Getters

    public int getTotalScore() {
        int totalScore = 0;
        for (PlayerModel player : players) {
            totalScore += player.getTotalScore();
        }
        return totalScore;
    }

    public ArrayList<PlayerModel> getPlayersSorted () {
        ArrayList<PlayerModel> sortedPlayerModels = this.getPlayers();
        sortedPlayerModels.sort((playerModel1, playerModel2) -> {
            if (playerModel1.getTotalScore() > playerModel2.getTotalScore()) {
                return -1;
            } else if (playerModel1.getTotalScore() < playerModel2.getTotalScore()) {
                return 1;
            } else {
                return 0;
            }
        });
        return sortedPlayerModels;
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
