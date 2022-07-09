package com.example.climateduels.team;

import static com.example.climateduels.R.id.recycler_menu;
import static com.example.climateduels.R.id.team;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.climateduels.R;
import com.example.climateduels.dataManager.DataManager;
import com.example.climateduels.dataManager.models.PlayerModel;

import java.util.ArrayList;

public class TeamFragment extends Fragment {

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_team, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(
                getString(R.string.shared_pref_key), Context.MODE_PRIVATE);
        String teamCode = sharedPreferences.getString(getString(R.string.shared_pref_team_name), null);

        DataManager.getTeamCached(teamCode, teamModel -> {
            recyclerView = view.findViewById(recycler_menu);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


            ArrayList<String> teamMembers = new ArrayList<>();
            int index = 0;
            ArrayList<PlayerModel> sortedPlayers = teamModel.getPlayersSorted();
            for (PlayerModel playerModel : sortedPlayers) {
                String content = "";
                if (index == 0) {
                    content = "\uD83D\uDC51 "; // crown
                } else if (index == 1) {
                    // silver medal
                    content = "\uD83E\uDD48 ";
                } else if (index == 2) {
                    // bronze medal
                    content = "\uD83E\uDD49 ";
                }
                else if (index == sortedPlayers.size() - 1) {
                    content = "\uD83D\uDCA9 "; // pooooooooopooooo
                } else {
                    content = Integer.toString(index + 1) + ". ";
                }
                content += playerModel.getName();
                content += " " + playerModel.getTotalScore();
                teamMembers.add(content);
                index++;
            }
            recyclerView.setAdapter(new CustomAdapter(teamMembers.toArray(new String[teamMembers.size()])));


    });
    }

}