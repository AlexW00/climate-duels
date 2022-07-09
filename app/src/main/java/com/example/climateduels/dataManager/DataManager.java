package com.example.climateduels.dataManager;

import android.os.AsyncTask;

import com.example.climateduels.dataManager.models.GoalCategoryModel;
import com.example.climateduels.dataManager.models.GoalModel;
import com.example.climateduels.dataManager.models.ModelCallback;
import com.example.climateduels.dataManager.models.PlayerModel;
import com.example.climateduels.dataManager.models.TeamModel;
import com.example.climateduels.database.Database;
import com.google.gson.Gson;

import java.sql.Connection;
import java.util.ArrayList;

public abstract class DataManager {

    private static Database database;

    // must be called once when app is started
    public static void init() {
        database = new Database();
    }

    private static PlayerModel cachedPlayerModel;
    private static boolean cachedPlayerModelIsValid = false;

    private static TeamModel cachedTeamModel;
    private static boolean cachedTeamModelIsValid = false;

    private static boolean isWaitingForData = false;

    public static Connection getConnection() {
        return database.connection;
    }

    public static void getPlayerCached(String teamCode, String playerName, ModelCallback<PlayerModel> callback) {
        if (cachedPlayerModelIsValid && cachedPlayerModel.getName().equals(teamCode)) callback.onComplete(cachedPlayerModel);
        else getPlayer(teamCode, playerName, callback);
    }

    public static void getPlayer(String teamCode, String playerName, ModelCallback<PlayerModel> callback) {
        isWaitingForData = true;
        AsyncTask<String, Void, PlayerModel> task = new AsyncTask<String, Void, PlayerModel>() {
            @Override
            protected PlayerModel doInBackground(String... params) {
                return PlayerModel.asyncCreatePlayerModel(params[0], params[1]);
            }

            @Override protected void onPostExecute(PlayerModel player) {
                super.onPostExecute(player);
                cachedPlayerModel = player;
                cachedPlayerModelIsValid = true;
                isWaitingForData = false;
                callback.onComplete(player);
            }
        };
        task.execute(playerName, teamCode);
    }


    public static void getTeamCached(String teamCode, ModelCallback<TeamModel> callback) {
        if (cachedTeamModelIsValid && cachedTeamModel.getCode().equals(teamCode)) callback.onComplete(cachedTeamModel);
        else getTeam(teamCode, callback);
    }

    public static void getTeam(String teamCode, ModelCallback<TeamModel> callback) {
        isWaitingForData = true;
        AsyncTask<String, Void, TeamModel> task = new AsyncTask<String, Void, TeamModel>() {
            @Override
            protected TeamModel doInBackground(String... params) {
                return TeamModel.asyncCreateTeamModel(params[0]);
            }

            @Override protected void onPostExecute(TeamModel team) {
                super.onPostExecute(team);
                cachedTeamModel = team;
                cachedTeamModelIsValid = true;
                isWaitingForData = false;
                callback.onComplete(team);
            }
        };
        task.execute(teamCode);
    }

    public static void checkIfPlayerNameExists(String playerName, String teamCode, ModelCallback<Boolean> callback) {
        AsyncTask<String, Void, Boolean> task = new AsyncTask<String, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(String... params) {
                return PlayerModel.asyncCheckIfPlayerExists(params[0], params[1]);
            }

            @Override protected void onPostExecute(Boolean exists) {
                super.onPostExecute(exists);
                callback.onComplete(exists);
            }
        };
        task.execute(playerName, teamCode);
    };

    public static void checkIfTeamExists(String teamCode, ModelCallback<Boolean> callback) {
        AsyncTask<String, Void, Boolean> task = new AsyncTask<String, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(String... params) {
                return TeamModel.asyncCheckIfTeamExists(params[0]);
            }

            @Override protected void onPostExecute(Boolean exists) {
                super.onPostExecute(exists);
                callback.onComplete(exists);
            }
        };
        task.execute(teamCode);
    };


    // some really cheap caching
    public static void invalidateCachedPlayerModel() {
        cachedPlayerModelIsValid = false;
    }

    public static void invalidateCachedTeamModel() {
        cachedTeamModelIsValid = false;
    }


    // avoid threading issues by waiting for data to be loaded
    public static boolean isWaitingForData() {
        return isWaitingForData;
    }
}