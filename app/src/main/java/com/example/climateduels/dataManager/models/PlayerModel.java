package com.example.climateduels.dataManager.models;

import android.os.AsyncTask;

import com.example.climateduels.dataManager.DataManager;
import com.example.climateduels.dataManager.DatabaseCallback;
import com.example.climateduels.dataManager.DatabaseObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

    public static PlayerModel asyncCreateNewPlayer (String name, String team_code, int goal_id_1, int goal_id_2, int goal_target_count_1, int goal_target_count_2) {

        try {
            String createPlayerSql = "INSERT INTO players (name, team_code, is_admin) VALUES (?, ?, ?)";
            PreparedStatement createPlayerStatement = DataManager.getConnection().prepareStatement(createPlayerSql);
            createPlayerStatement.setString(1, name);
            createPlayerStatement.setString(2, team_code);
            createPlayerStatement.setBoolean(3, false);
            createPlayerStatement.executeUpdate();

            // player_scores
            String createPlayerScoreSql = "INSERT INTO player_scores (player_name, team_code, score) VALUES (?, ?, ?)";
            PreparedStatement createPlayerScoreStatement = DataManager.getConnection().prepareStatement(createPlayerScoreSql);
            createPlayerScoreStatement.setString(1, name);
            createPlayerScoreStatement.setString(2, team_code);
            createPlayerScoreStatement.setInt(3, 0);
            createPlayerScoreStatement.executeUpdate();

            // player_goals
            //int goal_id;
            //int goal_target_count;
            String createPlayerGoal1Sql = "INSERT INTO player_goals (player_name, team_code, goal_id, target_count) VALUES (?, ?, ?, ?)";
            PreparedStatement createPlayerGoal1Statement = DataManager.getConnection().prepareStatement(createPlayerGoal1Sql);
            createPlayerGoal1Statement.setString(1, name);
            createPlayerGoal1Statement.setString(2, team_code);
            createPlayerGoal1Statement.setInt(3, goal_id_1);
            createPlayerGoal1Statement.setInt(4, goal_target_count_1);
            createPlayerGoal1Statement.executeUpdate();

            String createPlayerGoal2Sql = "INSERT INTO player_goals (player_name, team_code, goal_id, target_count) VALUES (?, ?, ?, ?)";
            PreparedStatement createPlayerGoal2Statement = DataManager.getConnection().prepareStatement(createPlayerGoal2Sql);
            createPlayerGoal2Statement.setString(1, name);
            createPlayerGoal2Statement.setString(2, team_code);
            createPlayerGoal2Statement.setInt(3, goal_id_2);
            createPlayerGoal2Statement.setInt(4, goal_target_count_2);
            createPlayerGoal2Statement.executeUpdate();

            // retrieve the ids of the newly created player_goals
            String getPlayerGoalIdsSql = "SELECT id FROM player_goals WHERE player_name=? AND team_code=?";
            PreparedStatement getPlayerGoalIdsStatement = DataManager.getConnection().prepareStatement(getPlayerGoalIdsSql);
            getPlayerGoalIdsStatement.setString(1, name);
            getPlayerGoalIdsStatement.setString(2, team_code);
            ResultSet rs = getPlayerGoalIdsStatement.executeQuery();

            int player_goal_id_1 = -1;
            int player_goal_id_2 = -1;
            while (rs.next()) {
                if (player_goal_id_1 == -1) {
                    player_goal_id_1 = rs.getInt("id");
                } else {
                    player_goal_id_2 = rs.getInt("id");
                }
            }

            String createPlayerGoalCount1Sql = "INSERT INTO player_goal_count (player_goal_id, current_count, date) VALUES (?, ?, ?)";
            PreparedStatement createPlayerGoalCount1PreparedStatement = DataManager.getConnection().prepareStatement(createPlayerGoalCount1Sql);
            createPlayerGoalCount1PreparedStatement.setInt(1, player_goal_id_1);
            createPlayerGoalCount1PreparedStatement.setInt(2, 0);
            createPlayerGoalCount1PreparedStatement.setDate(3, new java.sql.Date(System.currentTimeMillis()));
            createPlayerGoalCount1PreparedStatement.executeUpdate();

            String createPlayerGoalCount2Sql = "INSERT INTO player_goal_count (player_goal_id, current_count, date) VALUES (?, ?, ?)";
            PreparedStatement createPlayerGoalCount2PreparedStatement = DataManager.getConnection().prepareStatement(createPlayerGoalCount2Sql);
            createPlayerGoalCount2PreparedStatement.setInt(1, player_goal_id_2);
            createPlayerGoalCount2PreparedStatement.setInt(2, 0);
            createPlayerGoalCount2PreparedStatement.setDate(3, new java.sql.Date(System.currentTimeMillis()));
            createPlayerGoalCount2PreparedStatement.executeUpdate();

            return PlayerModel.asyncCreatePlayerModel(name, team_code);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    return null;
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


    public static void createNewPlayer(String name, String team_code, int goal_id_1, int goal_id_2, int goal_target_count_1, int goal_target_count_2, ModelCallback<PlayerModel> callback) {
        AsyncTask<String, Void, PlayerModel> task = new AsyncTask<String, Void, PlayerModel>() {
            @Override
            protected PlayerModel doInBackground(String... params) {
                return asyncCreateNewPlayer(
                        params[0],
                        params[1],
                        Integer.parseInt(params[2]),
                        Integer.parseInt(params[3]),
                        Integer.parseInt(params[4]),
                        Integer.parseInt(params[5])
                );
            }

            @Override protected void onPostExecute(PlayerModel playerModel) {
                super.onPostExecute(playerModel);
                if(callback!=null) callback.onComplete(playerModel);
            }
        };
        task.execute(name, team_code, Integer.toString(goal_id_1),  Integer.toString(goal_id_2), Integer.toString(goal_target_count_1), Integer.toString(goal_target_count_2));
    }
}
