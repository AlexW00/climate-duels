package com.example.climateduels.dataManager;

import com.example.climateduels.dataManager.models.PlayerModel;
import com.example.climateduels.dataManager.models.TeamModel;
import com.example.climateduels.database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public abstract class DataManager {

    private static Database database;

    // must be called once when app is started
    public static void init() {
        database = new Database();
    }

    public static PlayerModel getSelfPlayer(String teamCode, String playerName) {
        String sql = "SELECT * FROM players WHERE team_code = ? AND player_name = ?";
        PlayerModel player = null;

        try {
            PreparedStatement statement = database.connection.prepareStatement(sql);
            statement.setString(1, teamCode);
            statement.setString(2, playerName);
            ResultSet rs = statement.executeQuery();
//            if (rs.next()) {
//                player = new PlayerModel(
//                        rs.getString("player_name"),
//                        rs.getInt("total_score"),
//                        getWeeklyChallenge(teamCode, playerName)
//                );
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new PlayerModel();
    }

    public static TeamModel getTeam(String teamCode) {
        return new TeamModel();
    }

}