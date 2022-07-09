package com.example.climateduels.dataManager.models;

import com.example.climateduels.dataManager.DataManager;
import com.example.climateduels.dataManager.DatabaseObject;
import com.example.climateduels.database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PlayerModel extends DatabaseObject {

    protected final String name;
    protected final String teamCode;

    protected boolean isAdmin;
    protected int totalScore;
    protected WeeklyChallengeModel weeklyChallenge;

    private PlayerModel(String name, int totalScore, String teamCode, boolean isAdmin, WeeklyChallengeModel weeklyChallenge) {
        this.name = name;
        this.teamCode = teamCode;
        this.isAdmin = isAdmin;
        this.totalScore = totalScore;
        this.weeklyChallenge = weeklyChallenge;
    }


    public static PlayerModel asyncCreatePlayerModel(String name, String teamCode) {
        String sql = "SELECT is_admin, score FROM players JOIN player_scores ON players.name=player_scores.player_name WHERE name=? AND players.team_code=?";

        try {
            PreparedStatement statement = DataManager.getConnection().prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, teamCode);

            // print the statement
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                WeeklyChallengeModel weeklyChallenge = WeeklyChallengeModel.asyncCreateWeeklyChallengeModel(name, teamCode);
                return new PlayerModel(name, rs.getInt("score"), teamCode, rs.getBoolean("is_admin"), weeklyChallenge);
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<PlayerModel> asyncCreatePlayerModel(String teamCode) {
        String sql = "SELECT is_admin as is_admin, players.name as player_name, score FROM players JOIN player_scores ON players.name=player_scores.player_name WHERE players.team_code=?";

        try {
            PreparedStatement statement = DataManager.getConnection().prepareStatement(sql);
            statement.setString(1, teamCode);

            ResultSet rs = statement.executeQuery();

            ArrayList<PlayerModel> players = new ArrayList<>();
            while (rs.next()) {
                String playerName = rs.getString("player_name");
                int score = rs.getInt("score");
                boolean isAdmin = rs.getBoolean("is_admin");

                WeeklyChallengeModel weeklyChallengeModel = new WeeklyChallengeModel(playerName, teamCode, GoalCategoryModel.asyncCreateUserGoalCategoryModel(playerName, teamCode));
                players.add(new PlayerModel(playerName, score, teamCode, isAdmin, weeklyChallengeModel));
            }

            return players;
            } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public String getName() {
        return name;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public boolean getIsAdmin () {
        return isAdmin;
    }

    public WeeklyChallengeModel getWeeklyChallenge() {
        return weeklyChallenge;
    }

    @Override
    public void refreshData(Void callback) {
    }

    @Override
    protected void saveData(Void callback) {

    }
}
