package com.example.climateduels;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.climateduels.dataManager.DataManager;

public class StartActivity extends AppCompatActivity {

    private Button submitButton;
    private EditText
            teamCodeEdit,
            userNameEdit;
    public static final String
            TEAM_CODE_KEY = "TEAM_CODE",
            USER_NAME_KEY = "USER_NAME";
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataManager.init();

        System.out.println("**** StartActivity ****");

        //System.out.println(p.getTotalScore());
        //TeamModel t = DataManager.getTeam("ABCDE");
        //System.out.println(t.getName());
        // print the object stringified

        initPreferences();
        initUI();
    }

    private void initPreferences() {
        sharedPreferences = getApplicationContext().getSharedPreferences(
                getString(R.string.shared_pref_key), Context.MODE_PRIVATE);
        String teamCode = sharedPreferences.getString(getString(R.string.shared_pref_team_name), null),
                userName = sharedPreferences.getString(getString(R.string.shared_pref_user_name), null);
        if(teamCode!=null && userName != null){
            startCategoryChooserActivity(teamCode, userName);
        }
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


        if(userName.length()<=3){
            showUsernameFormatError();
            return;
        }
        if(teamCode.length()!=5){//TODO Datamanager
            showTeamCodeError();
            return;
        }

        DataManager.checkIfTeamExists(teamCode, teamDoesExist -> {
            if (teamDoesExist) {
                DataManager.checkIfPlayerNameExists(userName, teamCode, userNameDoesExist -> {
                    if (userNameDoesExist) showUsernameDoesExistError();
                    else {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(getString(R.string.shared_pref_user_name), userName);
                        editor.putString(getString(R.string.shared_pref_team_name), teamCode);
                        editor.apply();
                        startCategoryChooserActivity(teamCode, userName);
                    }
                });
            }
            else showTeamCodeError();
        });
    }

    private void showTeamCodeError() {
        Toast.makeText(StartActivity.this,
                "Please put in a valid team code.", Toast.LENGTH_SHORT).show();
    }

    private void showUsernameFormatError() {
        Toast.makeText(StartActivity.this,
                "Please choose a user name of length 3-20.", Toast.LENGTH_SHORT).show();
    }

    private void showUsernameDoesExistError() {
        Toast.makeText(StartActivity.this,
                "This user name is already taken.", Toast.LENGTH_SHORT).show();
    }

    private void startCategoryChooserActivity(String teamCode, String userName) {
        Intent intent = new Intent(StartActivity.this, CategoryChooserActivity.class);
        intent.putExtra(TEAM_CODE_KEY, teamCode);
        intent.putExtra(USER_NAME_KEY, userName);
        startActivity(intent);
        finish();
    }


}