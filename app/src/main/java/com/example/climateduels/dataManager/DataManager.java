package com.example.climateduels.dataManager;

import com.example.climateduels.dataManager.models.PlayerModel;
import com.example.climateduels.dataManager.models.TeamModel;

public abstract class DataManager {

    public static PlayerModel getSelfPlayer(String teamCode) {
        return new PlayerModel();
    }

    public static TeamModel getTeam(String teamCode) {
        return new TeamModel();
    }

}