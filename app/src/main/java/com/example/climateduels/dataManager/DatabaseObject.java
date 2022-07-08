package com.example.climateduels.dataManager;

public abstract class DatabaseObject {

    protected abstract String getTableName();

    public abstract void refreshData();

    protected abstract void saveData();

    protected abstract void initData();
}
