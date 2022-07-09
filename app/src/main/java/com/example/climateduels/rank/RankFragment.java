package com.example.climateduels.rank;

import static com.example.climateduels.R.id.recycler_menu;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.climateduels.R;
import com.example.climateduels.dataManager.DataManager;
import com.example.climateduels.dataManager.models.TeamModel;
import com.example.climateduels.team.CustomAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RankFragment extends Fragment {

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rank, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(
                getString(R.string.shared_pref_key), Context.MODE_PRIVATE);
        String teamCode = sharedPreferences.getString(getString(R.string.shared_pref_team_name), null);
        // Stinky hard coded match making
        String enemyTeamCode = (teamCode.equals("ABCDE") ? "EDCBA" : "ABCDE");
        recyclerView = view.findViewById(recycler_menu);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        DataManager.getTeamCached(teamCode, ownTeamModel -> {
            DataManager.getTeam(enemyTeamCode, enemyTeam -> {
                String[] teamStrings = new String[2];
                if (ownTeamModel.getTotalScore() > enemyTeam.getTotalScore()) {
                    teamStrings = new String[]{
                            getTeamInfoString(ownTeamModel, true, true),
                            getTeamInfoString(enemyTeam, false, false)
                    };
                } else {
                    teamStrings = new String[]{
                            getTeamInfoString(enemyTeam, true, false),
                            getTeamInfoString(ownTeamModel, false ,true)
                    };
                }

                recyclerView.setAdapter(new CustomAdapter(teamStrings));
            });
        });
    }

    static String getTeamInfoString(TeamModel teamModel, boolean isLeading, boolean isSelf) {
        String infoString = isLeading ? "\uD83D\uDC51 " : "\uD83C\uDFC3 "; // crown if leader, runner otherwise
        infoString += teamModel.getName() + " ";
        infoString += teamModel.getTotalScore() + " ";
        if (isSelf) {
            infoString += " ⬅️ (your team)"; // indicator for your team
        }
        return infoString;
    }
}