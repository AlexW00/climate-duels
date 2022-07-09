package com.example.climateduels.challenge;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.climateduels.R;
import com.example.climateduels.dataManager.DataManager;
import com.example.climateduels.dataManager.models.PlayerModel;
import com.example.climateduels.dataManager.models.UserGoalModel;
import com.example.climateduels.dataManager.models.WeeklyChallengeModel;
import com.google.gson.Gson;

public class ChallengeFragment extends Fragment {

    private double points;

    private ProgressBar
            progressBarTravel,
            progressBarEat;
    private Button
            addTravelButton,
            addEatButton;
    private TextView
            weeklyPoints,
            travelTitle,
            travelGoal,
            eatTitle,
            eatGoal,
            travelCount,
            eatCount;
    private PlayerModel playerModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_challenge, container, false);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(
                getString(R.string.shared_pref_key), Context.MODE_PRIVATE);
        String teamCode = sharedPreferences.getString(getString(R.string.shared_pref_team_name), null),
                userName = sharedPreferences.getString(getString(R.string.shared_pref_user_name), null);

        DataManager.getPlayerCached(teamCode, userName, playerModel -> {
            this.playerModel = playerModel;
            initUI(view);
            fillUI();
        });

        return view;
    }

    private void initUI(View view) {
        progressBarTravel = view.findViewById(R.id.fr_challenge_progressBar_travel);
        progressBarEat = view.findViewById(R.id.fr_challenge_progressBar_eat);
        addTravelButton = view.findViewById(R.id.fr_challenge_button_travel);
        addTravelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddTravelButtonClicked();
            }
        });
        addEatButton = view.findViewById(R.id.fr_challenge_button_eat);
        addEatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddEatButtonClicked();
            }
        });
        weeklyPoints = view.findViewById(R.id.fr_challenge_score);
        travelTitle = view.findViewById(R.id.fr_challenge_travel_title);
        travelGoal = view.findViewById(R.id.fr_challenge_travel_goal);
        eatTitle = view.findViewById(R.id.fr_challenge_eat_title);
        eatGoal = view.findViewById(R.id.fr_challenge_eat_goal);
        travelCount = view.findViewById(R.id.fr_challenge_travel_count_text);
        eatCount = view.findViewById(R.id.fr_challenge_eat_count_text);
    }

    private void fillUI() {
        UserGoalModel travelGoal = playerModel.getWeeklyChallenge().getGoalCategories().get(0).getGoals().get(0);
        UserGoalModel eatGoal = playerModel.getWeeklyChallenge().getGoalCategories().get(1).getGoals().get(0);
        String travelTitle = travelGoal.getTitle(),
                eatTitle = eatGoal.getTitle();

        this.travelTitle.setText(travelTitle);
        this.eatTitle.setText(eatTitle);

        int
                travelMaxNum = travelGoal.getTargetCount(),
                travelCurrentNum = travelGoal.getCurrentCount(),
                eatMaxNum = eatGoal.getTargetCount(),
                eatCurrentNum = eatGoal.getCurrentCount();
        this.travelGoal.setText(Integer.toString(travelMaxNum));
        this.eatGoal.setText(Integer.toString(eatMaxNum));
        travelCount.setText(Integer.toString(travelCurrentNum) + "/" + Integer.toString(travelMaxNum));
        eatCount.setText(Integer.toString(eatCurrentNum) + "/" + Integer.toString(eatMaxNum));

        this.weeklyPoints.setText(playerModel.getWeeklyChallenge().getTotalScoreViewString());
    }

    private void onAddTravelButtonClicked() {
        UserGoalModel travelGoal = playerModel.getWeeklyChallenge().getGoalCategories().get(0).getGoals().get(0);
        int travelMaxNum = travelGoal.getTargetCount(),
                travelCurrentNum = travelGoal.getCurrentCount();
        if(travelGoal.incrementCurrentCount())
        {
            travelCurrentNum = travelGoal.getCurrentCount();
            addPoints(calculateAddPointsPerStep(2, travelMaxNum));
            travelCount.setText(travelGoal.getTotalCountViewString());
            updateProgressBarTravel(getProgress(travelCurrentNum, travelMaxNum));
        }
    }

    private static int calculateAddPointsPerStep (int numberOfGoals, int goalMaxNum) {
        double base = (1.0 / goalMaxNum) * (100.0 / numberOfGoals);
        return (int) base;
    }

    private double getProgress(int current, int max) {
        double progress = current/(double) max;
        if(progress > 1.0) {
            progress = 1.0;
        }
        return progress;
    }

    private void onAddEatButtonClicked() {
        UserGoalModel eatGoal = playerModel.getWeeklyChallenge().getGoalCategories().get(1).getGoals().get(0);
        int eatMaxNum = eatGoal.getTargetCount(),
                eatCurrentNum = eatGoal.getCurrentCount();
        if(eatGoal.incrementCurrentCount())
        {
            eatCurrentNum = eatGoal.getCurrentCount();
            addPoints(calculateAddPointsPerStep(2, eatMaxNum));
            eatCount.setText(eatGoal.getTotalCountViewString());
            updateProgressBarEat(getProgress(eatCurrentNum, eatMaxNum));
        }
    }

    private void updateProgressBarTravel(double value) {
        progressBarTravel.setProgress((int) value * 100, true);
    }

    private void updateProgressBarEat(double value) {
        progressBarEat.setProgress((int) value * 100, true);
    }

    private void addPoints(double additionalPoints) {
        System.out.println("addPoints: " + additionalPoints);
        playerModel.addToScore((int) additionalPoints); // idk if this is safe
        weeklyPoints.setText(playerModel.getWeeklyChallenge().getTotalScoreViewString());
    }

}