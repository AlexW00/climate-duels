package com.example.climateduels.dataManager.models;

import com.example.climateduels.dataManager.DataManager;
import com.example.climateduels.dataManager.DatabaseObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class GoalCategoryModel <T extends GoalModel> extends DatabaseObject {

    protected String title;
    protected String description;
    protected ArrayList<T> goals;

    protected int id;

    private GoalCategoryModel(int id, String title, String description, ArrayList<T> goals) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.goals = goals;
    }

    public void pushGoal (T goal) {
        goals.add(goal);
    }


    public static ArrayList<GoalCategoryModel<GoalModel>> asyncCreateGoalCategoryModel (String teamCode) {

        String sql = "SELECT goal_categories.id as goal_category_id, goal_categories.title as goal_category_title, goal_categories.description as goal_category_description, goals.id as goal_id, goals.title as goal_title, goals.target_count as goal_target_count FROM goals JOIN goal_categories ON goals.goal_category_id=goal_categories.id WHERE goal_categories.team_code = ?";
        try {
            PreparedStatement statement = DataManager.getConnection().prepareStatement(sql);
            statement.setString(1, teamCode);

            ResultSet rs = statement.executeQuery();
            // Loop over rs
            ArrayList<GoalCategoryModel<GoalModel>> goalCategories = new ArrayList<>();

            while (rs.next()) {
                int goal_id = rs.getInt("goal_id");
                String goal_title = rs.getString("goal_title");
                int goal_target_count = rs.getInt("goal_target_count");
                GoalModel goalModel = new GoalModel(goal_id, goal_title, goal_target_count);


                int goal_category_id = rs.getInt("goal_category_id");
                String goal_category_title = rs.getString("goal_category_title");
                String goal_category_description = rs.getString("goal_category_description");
                int index = -1;
                for (int i = 0; i < goalCategories.size(); i++) {
                    if (goalCategories.get(i).id == goal_category_id) {
                        index = i;
                        break;
                    }
                }
                if (index != -1) {
                    goalCategories.get(index).pushGoal(goalModel);
                } else {
                    ArrayList<GoalModel> goals = new ArrayList<>();
                    goals.add(goalModel);
                    goalCategories.add(new GoalCategoryModel(goal_category_id, goal_category_title, goal_category_description, goals));
                }
            }
            return  goalCategories;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<GoalCategoryModel<UserGoalModel>> asyncCreateUserGoalCategoryModel (String playerName, String teamCode) {

        String sql = "SELECT goal_categories.id as goal_category_id, goal_categories.title as goal_category_title, goal_categories.description as goal_category_description, goals.id as goal_id, goals.title as goal_title, goals.target_count as goal_target_count, player_goals.id as player_goals_id, player_goal_count.current_count as goal_current_count FROM player_goals JOIN goals ON player_goals.goal_id=goals.id JOIN player_goal_count ON player_goals.id=player_goal_count.player_goal_id JOIN goal_categories ON goals.goal_category_id=goal_categories.id WHERE player_goals.player_name=? AND player_goals.team_code=?";

        try {
            PreparedStatement statement = DataManager.getConnection().prepareStatement(sql);

            statement.setString(1, playerName);
            statement.setString(2, teamCode);
            System.out.println(statement.toString());
            ResultSet rs = statement.executeQuery();
            // Loop over rs
            ArrayList<GoalCategoryModel<UserGoalModel>> goalCategories = new ArrayList<>();
            while (rs.next()) {
                System.out.println("rs.next()");
                int user_goal_id = rs.getInt("player_goals_id");
                int goal_id = rs.getInt("goal_id");
                String title = rs.getString("goal_title");
                int targetCount = rs.getInt("goal_target_count");
                int currentCount = rs.getInt("goal_current_count");

                UserGoalModel userGoalModel = new UserGoalModel(user_goal_id, goal_id, title, targetCount, currentCount);

                int goal_category_id = rs.getInt("goal_category_id");
                String goal_category_title = rs.getString("goal_category_title");
                String goal_category_description = rs.getString("goal_category_description");
                int index = -1;
                for (int i = 0; i < goalCategories.size(); i++) {
                    if (goalCategories.get(i).id == goal_category_id) {
                        index = i;
                        break;
                    }
                }
                if (index != -1) {
                    goalCategories.get(index).pushGoal(userGoalModel);
                } else {
                    ArrayList<UserGoalModel> goals = new ArrayList<>();
                    goals.add(userGoalModel);
                    goalCategories.add(new GoalCategoryModel<UserGoalModel>(goal_category_id, goal_category_title, goal_category_description, goals));
                }
            }
            System.out.println("Returning: " + goalCategories.size());
            return  goalCategories;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    // Getters

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<T> getGoals() {
        return goals;
    }

    @Override
    public void refreshData(Void callback) {
        throw new UnsupportedOperationException("Not implemented");

    }

    @Override
    protected void saveData(Void callback) {
        throw new UnsupportedOperationException("Not implemented");

    }
}
