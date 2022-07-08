package com.example.climateduels.dataManager.models;

import java.util.ArrayList;

public class GoalCategoryModel <T extends GoalModel>  {

    protected String title;
    protected String description;
    protected ArrayList<T> goals;

    public GoalCategoryModel(String title, String description, ArrayList<T> goals) {
        this.title = title;
        this.description = description;
        this.goals = goals;
    }

    // mock data constructor
    public GoalCategoryModel() {
        this.title = "Test title";
        this.description = "Test description";
        this.goals = new ArrayList<T>();

        this.goals.add((T) new GoalModel("title1", 0));
        this.goals.add((T) new GoalModel("title2", 0));
        this.goals.add((T) new GoalModel("title3", 0));
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
}
