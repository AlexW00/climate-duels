package com.example.climateduels.dataManager;

import com.example.climateduels.dataManager.models.PlayerModel;
import com.example.climateduels.dataManager.models.TeamModel;
import com.example.climateduels.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public abstract class DataManager {

    private static Database database;

    // must be called once when app is started
    public static void init() {
        database = new Database();
    }

    public static Connection getConnection() {
        return database.connection;
    }

    public static PlayerModel getPlayer(String teamCode, String playerName) {
        return new PlayerModel();
        //return new PlayerModel(playerName, teamCode);
    }

    public static TeamModel getTeam(String teamCode) {
        return new TeamModel();
    }

}