package com.example.climateduels.dataManager.models;

import android.os.AsyncTask;

import com.example.climateduels.dataManager.DataManager;
import com.example.climateduels.dataManager.DatabaseCallback;

import java.sql.PreparedStatement;

public class UserGoalModel extends GoalModel {
    int user_goal_id;
    int currentCount;
    int targetCount;

    public UserGoalModel(int user_goal_id, int goal_id, String title, int targetCount, int currentCount) {
        super(goal_id, title);
        this.user_goal_id = user_goal_id;
        this.currentCount = currentCount;
        this.targetCount = targetCount;
    }

    // static async sql methods
    private static boolean saveSelfSql(int userGoalId, int currentCount) {
        String sql = "UPDATE player_goal_count SET current_count = ? WHERE player_goal_id = ?";
        try {
            PreparedStatement statement = DataManager.getConnection().prepareStatement(sql);
            statement.setInt(1, currentCount);
            statement.setInt(2, userGoalId);

            int r = statement.executeUpdate();
            return r > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    // Getters

    public int getCurrentCount() {
        return currentCount;
    }

    public int getPercentageComplete() {
        return (int) (((double) currentCount / (double) targetCount) * 100);
    }

    public int getTargetCount() {
        return targetCount;
    }

    // Special getters

    public String getTotalCountViewString() {
        return Integer.toString(currentCount) + "/" + Integer.toString(targetCount);
    }

    // Setters
    public boolean incrementCurrentCount() {
        if (currentCount < targetCount) {
            currentCount++;
            saveData(null);
            return true;
        }
        saveData(null);
        return false;
    }


    // Overrides
    @Override
    public void refreshData(DatabaseCallback callback) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    protected void saveData(DatabaseCallback callback) {
        AsyncTask<Integer, Void, Boolean> task = new AsyncTask<Integer, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Integer... params) {
                return saveSelfSql(params[0], params[1]);
                //return true; // TODO: Change back
            }

            @Override protected void onPostExecute(Boolean wasSuccessful) {
                super.onPostExecute(wasSuccessful);
                if(callback!=null) callback.onComplete(wasSuccessful);
            }
        };
        task.execute(user_goal_id, currentCount);
    }
}