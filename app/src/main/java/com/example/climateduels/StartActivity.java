package com.example.climateduels;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.climateduels.dataManager.DataManager;
import com.example.climateduels.dataManager.models.PlayerModel;
import com.example.climateduels.dataManager.models.TeamModel;
import com.example.climateduels.database.Database;
import com.google.gson.Gson;

public class StartActivity extends AppCompatActivity {

    private Button submitButton;
    private EditText
            teamCodeEdit,
            userNameEdit;
    public static final String
            TEAM_CODE_KEY = "TEAM_CODE",
            USER_NAME_KEY = "USER_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataManager.init();

        System.out.println("**** StartActivity ****");
        DataManager.getPlayer("ABCDE", "Alex", model -> {
            System.out.println("GOT RESULT");
            if (model != null) System.out.println("PlayerModel: " + new Gson().toJson(model));
            else System.out.println("PlayerModel: null");
        });

        DataManager.getTeam("ABCDE", teamModel -> {
            System.out.println("GOT TEAM RESULT");
            if (teamModel != null) System.out.println("TeamModel: " + new Gson().toJson(teamModel));
            else System.out.println("TeamModel is null");

        });
        //System.out.println(p.getTotalScore());
        //TeamModel t = DataManager.getTeam("ABCDE");
        //System.out.println(t.getName());
        // print the object stringified

        initUI();
    }

    private void initUI(){
        setContentView(R.layout.activity_start);
        submitButton = findViewById(R.id.start_button_submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmitButtonClicked();
            }
        });
        teamCodeEdit = findViewById(R.id.edit_text_team_name);
        userNameEdit = findViewById(R.id.edit_text_user_name);
    }

    private void onSubmitButtonClicked(){
        String
                teamCode = teamCodeEdit.getText().toString(),
                userName = userNameEdit.getText().toString();
        /**
        if(DataManager.containsTeam(teamCode)) {

        }
        else {

        }
         */
        startCategoryChooserActivity(teamCode, userName);
    }

    private void startCategoryChooserActivity(String teamCode, String userName) {
        Intent intent = new Intent(StartActivity.this, CategoryChooserActivity.class);
        intent.putExtra(TEAM_CODE_KEY, teamCode);
        intent.putExtra(USER_NAME_KEY, userName);
        startActivity(intent);
    }


}