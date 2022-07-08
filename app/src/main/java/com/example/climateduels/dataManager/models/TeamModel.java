package com.example.climateduels.dataManager.models;

import com.example.climateduels.dataManager.DataManager;
import com.example.climateduels.dataManager.DatabaseObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class TeamModel extends DatabaseObject {
    String name;
    String code;

    ArrayList<PlayerModel> players;

    ArrayList<GoalCategoryModel<GoalModel>> goalCategories;

    private TeamModel(String name, String code, ArrayList<PlayerModel> players, ArrayList<GoalCategoryModel<GoalModel>> goalCategories) {
        this.name = name;
        this.code = code;
        this.players = players;
        this.goalCategories = goalCategories;
    }

    public static TeamModel asyncCreateTeamModel(String code) {
        String name = asyncGetTeamName(code);
        ArrayList<PlayerModel> players = PlayerModel.asyncCreatePlayerModel(code);
        ArrayList<GoalCategoryModel<GoalModel>> goalCategories = GoalCategoryModel.asyncCreateGoalCategoryModel(code);
        return new TeamModel(name, code, players, goalCategories);
    }

    private static String asyncGetTeamName(String code) {
        String sql = "SELECT name FROM teams WHERE code=?";

        try {
            PreparedStatement statement = DataManager.getConnection().prepareStatement(sql);
            statement.setString(1, code);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) return rs.getString("name");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
    public void refreshData(Void callback) {

    }

    @Override
    protected void saveData(Void callback) {

    }

//    @Override
//    protected void initData() {
//        String sql = "SELECT * FROM " + getTableName() + " WHERE code = ?";
//        try {
//            PreparedStatement statement = DataManager.getConnection().prepareStatement(sql);
//            statement.setString(1, code);
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) this.name = resultSet.getString("name");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
