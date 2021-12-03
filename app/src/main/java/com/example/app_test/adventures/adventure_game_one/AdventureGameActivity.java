package com.example.app_test.adventures.adventure_game_one;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.example.app_test.R;
import com.example.app_test.Utils.Scenario;
import com.example.app_test.adventures.ScenarioInitActivity;
import com.example.app_test.databinding.ActivityAdventureGameBinding;

import java.util.ArrayList;

public class AdventureGameActivity extends AppCompatActivity {
    private ActivityAdventureGameBinding binding;
    private ArrayList<Scenario> scenarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityAdventureGameBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        // make scenarios reference point to intent's scenarios
        this.scenarios = getIntent()
                .getParcelableArrayListExtra(ScenarioInitActivity.SCENARIOS_KEY);

        System.out.println("HEre");
        // IF (btnType == 4)
//        four_btns fragment = four_btns.newInstance(scenarios.get(0));
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.replace(R.id.game_btns_area, fragment);
//        ft.commit();
//        this.binding.testBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                ft.replace(R.id.game_btns_area, new three_btns());
//                ft.commit();
//            }
//        });



    }


}