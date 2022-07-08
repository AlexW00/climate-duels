package com.example.climateduels.dataManager.models;

import com.example.climateduels.dataManager.DatabaseObject;

import java.util.ArrayList;

public class TeamModel extends DatabaseObject {
    String name;
    String code;

    ArrayList<PlayerModel> normalPlayers;
    ArrayList<PlayerModel> admins;

    ArrayList<GoalCategoryModel<GoalModel>> goalCategories;

    public TeamModel(String name, String code, ArrayList<PlayerModel> normalPlayers, ArrayList<PlayerModel> admins, ArrayList<GoalCategoryModel<GoalModel>> goalCategories) {
        this.name = name;
        this.code = code;
        this.normalPlayers = normalPlayers;
        this.admins = admins;
        this.goalCategories = goalCategories;
    }

    // mock data constructor
    public TeamModel() {
        this.name = "Test name";
        this.code = "abcdef";
        this.normalPlayers = new ArrayList<PlayerModel>();
        this.admins = new ArrayList<PlayerModel>();

        this.normalPlayers.add(new PlayerModel());
        this.normalPlayers.add(new PlayerModel());
        this.normalPlayers.add(new PlayerModel());

        this.admins.add(new PlayerModel());
        this.admins.add(new PlayerModel());
        this.admins.add(new PlayerModel());

        this.goalCategories = new ArrayList<GoalCategoryModel<GoalModel>>();
        this.goalCategories.add(new GoalCategoryModel<GoalModel>());
        this.goalCategories.add(new GoalCategoryModel<GoalModel>());
    }

    // Getters

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public ArrayList<PlayerModel> getNormalPlayers() {
        return normalPlayers;
    }

    public ArrayList<PlayerModel> getAdmins() {
        return admins;
    }

    public ArrayList<GoalCategoryModel<GoalModel>> getGoalCategories() {
        return goalCategories;
    }

    // Special getters

    public ArrayList<PlayerModel> getPlayers() {
        ArrayList<PlayerModel> players = new ArrayList<PlayerModel>();
        players.addAll(normalPlayers);
        players.addAll(admins);
        return players;
    }

    @Override
    public void refreshData() {

    }

    @Override
    protected void saveData() {

    }
}
