package com.example.climateduels.dataManager;

import com.example.climateduels.dataManager.models.PlayerModel;
import com.example.climateduels.dataManager.models.TeamModel;
import com.example.climateduels.database.Database;

public abstract class DataManager {

    private static Database database;

    // must be called once when app is started
    public static void init() {
        database = new Database();
    }

    public static PlayerModel getSelfPlayer(String teamCode) {
        return new PlayerModel();
    }

    public static TeamModel getTeam(String teamCode) {
        return new TeamModel();
    }

}