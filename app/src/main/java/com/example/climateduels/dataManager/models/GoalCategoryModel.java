package com.example.climateduels.dataManager.models;

import java.util.ArrayList;

public class GoalCategoryModel <T extends GoalModel>  {

    String title;
    String description;
    ArrayList<T> goals;

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

        this.goals.add((T) new GoalModel());
        this.goals.add((T) new GoalModel());
        this.goals.add((T) new GoalModel());
    }
}
