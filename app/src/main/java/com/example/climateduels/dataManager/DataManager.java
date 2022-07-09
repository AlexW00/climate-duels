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
    private static TeamModel cachedTeamModel;

    public static Connection getConnection() {
        return database.connection;
    }

    public static void getPlayer (String teamCode, String playerName, ModelCallback<PlayerModel> callback) {
        if (cachedPlayerModel != null) callback.onComplete(cachedPlayerModel);
        AsyncTask<String, Void, PlayerModel> task = new AsyncTask<String, Void, PlayerModel>() {
            @Override
            protected PlayerModel doInBackground(String... params) {
                return PlayerModel.asyncCreatePlayerModel(params[0], params[1]);
            }

            @Override protected void onPostExecute(PlayerModel player) {
                super.onPostExecute(player);
                cachedPlayerModel = player;
                callback.onComplete(player);
            }
        };
        task.execute(playerName, teamCode);
    }

    public static void getTeam(String teamCode, ModelCallback<TeamModel> callback) {
        if (cachedTeamModel != null) callback.onComplete(cachedTeamModel);
        AsyncTask<String, Void, TeamModel> task = new AsyncTask<String, Void, TeamModel>() {
            @Override
            protected TeamModel doInBackground(String... params) {
                return TeamModel.asyncCreateTeamModel(params[0]);
            }

            @Override protected void onPostExecute(TeamModel team) {
                super.onPostExecute(team);
                cachedTeamModel = team;
                System.out.println("***** Team");
                System.out.println("team: " + team.getPlayers().size());
                callback.onComplete(team);
            }
        };
        task.execute(teamCode);
    }

}