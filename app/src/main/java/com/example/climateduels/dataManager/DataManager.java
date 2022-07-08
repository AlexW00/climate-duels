package com.example.climateduels.dataManager;

import com.example.climateduels.dataManager.models.PlayerModel;
import com.example.climateduels.dataManager.models.TeamModel;
import com.example.climateduels.dataManager.models.WeeklyChallengeModel;

public abstract class DataManager {

    public static PlayerModel getOwnPlayer() {
        return new PlayerModel();
    }

    public static TeamModel getOwnTeam() {
        return new TeamModel();
    }
}