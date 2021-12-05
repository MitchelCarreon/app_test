package com.example.app_test.adventures.adventure_game_one;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.example.app_test.R;
import com.example.app_test.Utils.Scenario;
import com.example.app_test.Utils.ScenarioViewModel;
import com.example.app_test.adventures.ScenarioInitActivity;
import com.example.app_test.databinding.ActivityAdventureGameBinding;

import java.util.ArrayList;

// GAME LOGIC HERE
public class AdventureGameActivity extends AppCompatActivity implements four_btns.onPopulateListenerBTN4 {
    private ActivityAdventureGameBinding binding;
    private ArrayList<Scenario> scenarios;
    private ScenarioViewModel next_scenario_vModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityAdventureGameBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        // make scenarios reference point to intent's scenarios from PARCELABLE EXTRA IN INTENT
        this.scenarios = getIntent()
                .getParcelableArrayListExtra(ScenarioInitActivity.SCENARIOS_KEY);

        System.out.println("Breakpoint to contents of scenarios list here.");
        // INITIAL BUTTON LAYOUT FOR FRAGMENT
        four_btns fragment = four_btns.newInstance(scenarios.get(0));
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.game_btns_area, fragment);
        ft.commit();

        this.next_scenario_vModel = new ViewModelProvider(this).get(ScenarioViewModel.class);
        this.next_scenario_vModel.getScenarioData().observe(this, new Observer<Scenario>() {
            @Override
            public void onChanged(Scenario scenario) {

                // GAME LOGIC.
                four_btns fragment = four_btns.newInstance(
                        next_scenario_vModel.getScenarioData().getValue());
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.game_btns_area, fragment);
                ft.commit();
            }
        });


        // TEST BUTTON - FRAGMENT
        this.binding.testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                four_btns fragment = four_btns.newInstance(scenarios.get(1));
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.game_btns_area, fragment);
                ft.commit();
            }
        });

    }


    // PASSING DATA FROM FRAGMENT TO ACTIVITY
    @Override
    public void onPopulateViewsBTN4(String text_desc) {
        this.binding.scenarioDesc.setText(text_desc);
    }
    @Override
    public void onButtonClickBTN4(int scenario_index) {
        this.next_scenario_vModel.setCurrScenarioData(this.scenarios.get(scenario_index));
    }

}