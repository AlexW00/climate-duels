package com.example.climateduels;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.climateduels.team.TeamFragment;

public class PerformanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, TeamFragment.newInstance()).commitNow();
            //getSupportFragmentManager().beginTransaction().replace(R.id.container, TeamFragment.newInstance()).commitNow();
        }
    }
}