package com.example.climateduels.challenge;

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



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_challenge, container, false);
        initUI(view);
        fillUI();
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

    }

    private void onAddTravelButtonClicked() {
        int
                travelMaxNum = 0, //TODO: get from DB
                travelCurrentNum = 0; //TODO: get from DB
        if(travelCurrentNum < travelMaxNum)
        {
            travelCurrentNum++; //TODO: Update DB
            addPoints(1/travelMaxNum * 50);
            travelCount.setText(travelCurrentNum + "/" + travelMaxNum);
            updateProgressBarTravel(getProgress(travelCurrentNum, travelMaxNum));
        }
    }

    private double getProgress(int current, int max) {
        double progress = current/(double) max;
        if(progress > 1.0) {
            progress = 1.0;
        }
        return progress;
    }

    private void onAddEatButtonClicked() {
        int
                eatMaxNum = 0, //TODO: get from DB
                eatCurrentNum = 0; //TODO: get from DB
        if(eatCurrentNum < eatMaxNum)
        {
            eatCurrentNum++; //TODO: Update DB
            addPoints(1/eatMaxNum * 50);
            eatCount.setText(eatCurrentNum + "/" + eatMaxNum);
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
        points += additionalPoints;
        //TODO: update DB
        weeklyPoints.setText((int) points + "/100 points");
    }

}