package com.example.climateduels;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.climateduels.dataManager.DataManager;
import com.example.climateduels.dataManager.models.GoalCategoryModel;
import com.example.climateduels.dataManager.models.GoalModel;
import com.example.climateduels.dataManager.models.TeamModel;

import java.util.ArrayList;

public class CategoryChooserActivity extends AppCompatActivity {

    private Button submitButton;
    private EditText
            numTravelEdit,
            numEatEdit;
    private RadioGroup radioGroupTravel, radioGroupEat;
    private String teamCode, userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        teamCode = bundle.getString(StartActivity.TEAM_CODE_KEY);
        userName = bundle.getString(StartActivity.USER_NAME_KEY);
        initUI();
    }

    private void initUI() {
        setContentView(R.layout.activity_category_chooser);
        radioGroupTravel = findViewById(R.id.category_radio_group_travel);
        radioGroupEat = findViewById(R.id.category_radio_group_eat);
        fillCategories();
        submitButton = findViewById(R.id.category_button_submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmitButtonClicked();
            }
        });
        numTravelEdit = findViewById(R.id.category_travel_edit_num);
        numEatEdit = findViewById(R.id.category_eat_edit_num);
    }

    private void fillCategories() {
        TextView
                travelTitle = findViewById(R.id.text_travel),
                travelDescription = findViewById(R.id.text_travel_description),
                eatTitle = findViewById(R.id.text_eat),
                eatDescription = findViewById(R.id.text_eat_description);


        TeamModel teamModel = DataManager.getTeam(teamCode);
        ArrayList<GoalCategoryModel<GoalModel>> goalCategoryModels = teamModel.getGoalCategories();
        GoalCategoryModel<GoalModel> travelModel = goalCategoryModels.get(0);
        GoalCategoryModel<GoalModel> eatModel = goalCategoryModels.get(1);

        travelTitle.setText(travelModel.getTitle());
        travelDescription.setText(travelModel.getDescription());
        eatTitle.setText(eatModel.getTitle());
        eatDescription.setText(eatModel.getDescription());

        for (GoalModel goal : travelModel.getGoals()) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(goal.getTitle());
            radioGroupTravel.addView(radioButton);
        }
        for (GoalModel goal : eatModel.getGoals()) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(goal.getTitle());
            radioGroupEat.addView(radioButton);
        }
    }

    private void onSubmitButtonClicked() {
        int numTravel = Integer.parseInt(numTravelEdit.getText().toString()),
                numEat = Integer.parseInt(numEatEdit.getText().toString());

        int travelRadioId = radioGroupTravel.getCheckedRadioButtonId(),
                eatRadioId = radioGroupEat.getCheckedRadioButtonId();
        if(travelRadioId == -1 || eatRadioId == -1){
            showErrorToastGoal();
            return;
        }
        if(numTravel == 0 || numTravel >= 50 || numEat == 0 || numEat >= 50){
            showErrorToastCounter();
            return;
        }
        RadioButton selectedTravel = findViewById(travelRadioId),
                selectedEat = findViewById(eatRadioId);
        String selectedTravelText = selectedTravel.getText().toString(),
                selectedEatText = selectedEat.getText().toString();

        //TODO SAVE TO DATAMANAGER
        Toast.makeText(CategoryChooserActivity.this, selectedTravelText+" "+selectedEatText, Toast.LENGTH_SHORT).show();
        Toast.makeText(CategoryChooserActivity.this, numTravel+" "+numEat, Toast.LENGTH_SHORT).show();

    }

    private void showErrorToastGoal() {
        Toast.makeText(CategoryChooserActivity.this,
                "Please choose two goals", Toast.LENGTH_SHORT).show();

    }

    private void showErrorToastCounter() {
        Toast.makeText(CategoryChooserActivity.this,
                "Please use a time per week amount between 0 to 49", Toast.LENGTH_SHORT).show();

    }
}