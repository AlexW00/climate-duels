package com.example.climateduels.dataManager.models;

import com.example.climateduels.dataManager.DatabaseObject;

import java.util.ArrayList;

public class TeamModel extends DatabaseObject {
    String name;

    ArrayList<PlayerModel> normalPlayers;
    ArrayList<PlayerModel> admins;

    ArrayList<GoalCategoryModel<GoalModel>> goalCategories;

    public TeamModel(String name, ArrayList<PlayerModel> normalPlayers, ArrayList<PlayerModel> admins, ArrayList<GoalCategoryModel<GoalModel>> goalCategories) {
        this.name = name;
        this.normalPlayers = normalPlayers;
        this.admins = admins;
        this.goalCategories = goalCategories;
    }

    // mock data constructor
    public TeamModel() {
        this.name = "Test name";
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

    @Override
    public void refreshData() {

    }

    @Override
    protected void saveData() {

    }
}
