package com.example.climateduels.dataManager;

import com.example.climateduels.dataManager.models.GoalCategoryModel;
import com.example.climateduels.dataManager.models.GoalModel;

import java.util.ArrayList;
import java.util.List;

public class EmergencyData {
    private ArrayList<String> travelOptions, eatOption;
    private int travelCount, eatCount;
    private String selectedTravel, selectedEat;

    private static final EmergencyData instance = new EmergencyData();

    public static EmergencyData getInstance(){
        return instance;
    }

    private EmergencyData() {
        travelOptions = new ArrayList<>();
        travelOptions.add("Take the bike");
        travelOptions.add("Go with the bus");
        travelOptions.add("Use the train");

        eatOption = new ArrayList<>();
        eatOption.add("Eat in the cantina");
        eatOption.add("Eat a vegetarian/vegan meal");
        eatOption.add("Eat a regional meal");
    }

    public ArrayList<String> getTravelOptions() {
        return travelOptions;
    }

    public ArrayList<String> getEatOption() {
        return eatOption;
    }

    public int getTravelCount() {
        return travelCount;
    }

    public void setTravelCount(int travelCount) {
        this.travelCount = travelCount;
    }

    public int getEatCount() {
        return eatCount;
    }

    public void setEatCount(int eatCount) {
        this.eatCount = eatCount;
    }

    public String getSelectedTravel() {
        return selectedTravel;
    }

    public void setSelectedTravel(String selectedTravel) {
        this.selectedTravel = selectedTravel;
    }

    public String getSelectedEat() {
        return selectedEat;
    }

    public void setSelectedEat(String selectedEat) {
        this.selectedEat = selectedEat;
    }
}
