package com.example.climateduels.dataManager;

public abstract class DatabaseObject {

    public abstract void refreshData(DatabaseCallback callback);

    protected abstract void saveData(DatabaseCallback callback);

}
