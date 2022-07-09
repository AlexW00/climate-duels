package com.example.climateduels.dataManager.models;

import android.os.AsyncTask;

import com.example.climateduels.dataManager.DataManager;
import com.example.climateduels.dataManager.DatabaseCallback;
import com.example.climateduels.dataManager.DatabaseObject;

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


    // Currently only score can be updated, but could be expanded to include other fields.
    private static boolean saveSelfSql(String playerName, String teamCode, int totalScore) {
        String sql = "UPDATE player_scores SET score=? WHERE player_name=? AND team_code=?";
        try {

            System.out.println("Saving player score: " + playerName + " " + teamCode + " " + totalScore);
            PreparedStatement statement = DataManager.getConnection().prepareStatement(sql);
            statement.setInt(1, totalScore);
            statement.setString(2, playerName);
            statement.setString(3, teamCode);

            int r = statement.executeUpdate();
            return r > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    // Getters
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


    // Setters

    public void addToScore(int amountToAdd) {
        totalScore += amountToAdd;
        saveData(data -> {
            // invalidate team cache because the score has changed
            // player cache does not need to be invalidated because it has just been updated
            DataManager.invalidateCachedTeamModel();
        });
    }

    // Overrides
    @Override
    public void refreshData(DatabaseCallback callback) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    protected void saveData(DatabaseCallback callback) {
        AsyncTask<String, Void, Boolean> task = new AsyncTask<String, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(String... params) {
                return saveSelfSql(params[0], params[1], Integer.parseInt(params[2]));
            }

            @Override protected void onPostExecute(Boolean wasSuccessful) {
                super.onPostExecute(wasSuccessful);
                if(callback!=null) callback.onComplete(wasSuccessful);
            }
        };
        task.execute(name, teamCode, Integer.toString(totalScore));
    }
}
