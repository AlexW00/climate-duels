package com.example.climateduels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.climateduels.challenge.ChallengeFragment;
import com.example.climateduels.rank.RankFragment;
import com.example.climateduels.team.TeamFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PerformanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance);


        ChallengeFragment cf = new ChallengeFragment();
        switchToFragment(cf);

        BottomNavigationView bottomView = findViewById(R.id.bottom_navigation);
        bottomView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.challenge_tab:
                        ChallengeFragment cf = new ChallengeFragment();
                        switchToFragment(cf);
                        return true;
                    case R.id.team_tab:
                        TeamFragment tf = new TeamFragment();
                        switchToFragment(tf);
                        return true;
                    case R.id.ranking_tab:
                        RankFragment rf = new RankFragment();
                        switchToFragment(rf);
                        return true;
                }
                return false;
            }
        });


        /**if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, ChallengeFragment.newInstance()).commitNow();
            //getSupportFragmentManager().beginTransaction().replace(R.id.container, TeamFragment.newInstance()).commitNow();
        }*/
    }

    private void switchToFragment(Fragment fr){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
        fragmentManager.beginTransaction()
                .replace(R.id.container_fragment_performance, fr)
                .addToBackStack(null)
                .commit(); // commit führt die Transaktion nicht sofort durch
        fragmentManager.executePendingTransactions(); // ausstehende Transaktionen sofort durchführen
    }
}