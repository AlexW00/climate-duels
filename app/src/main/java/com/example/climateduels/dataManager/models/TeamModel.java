package com.example.climateduels.dataManager.models;

import com.example.climateduels.dataManager.DatabaseObject;

import java.util.ArrayList;

public class TeamModel extends DatabaseObject {
    String name;
    String code;

    ArrayList<PlayerModel> players;

    ArrayList<GoalCategoryModel<GoalModel>> goalCategories;

    public TeamModel(String name, String code, ArrayList<PlayerModel> admins, ArrayList<GoalCategoryModel<GoalModel>> goalCategories) {
        this.name = name;
        this.code = code;
        this.goalCategories = goalCategories;
    }

    // mock data constructor
    public TeamModel() {
        this.name = "Test name";
        this.code = "abcdef";
        this.players = new ArrayList<PlayerModel>();

        this.players.add(new PlayerModel());
        this.players.add(new PlayerModel());
        this.players.add(new PlayerModel());

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


    public ArrayList<GoalCategoryModel<GoalModel>> getGoalCategories() {
        return goalCategories;
    }

    // Special getters

    public ArrayList<PlayerModel> getPlayers() {
        return players;
    }

    @Override
    protected String getTableName() {
        return null;
    }

    @Override
    public void refreshData() {

    }

    @Override
    protected void saveData() {

    }

    @Override
    protected void initData() {

    }
}
