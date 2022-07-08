package com.example.climateduels.dataManager;

import com.example.climateduels.dataManager.models.ModelCallback;

public abstract class DatabaseObject {

    public abstract void refreshData(Void callback);

    protected abstract void saveData(Void callback);

}
