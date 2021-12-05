package com.example.app_test.adventures;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.app_test.AdventureSelectActivity;
import com.example.app_test.Utils.Scenario;
import com.example.app_test.adventures.adventure_game_one.AdventureGameActivity;
import com.example.app_test.databinding.ActivityScenarioInitBinding;


// IO STUFF
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class ScenarioInitActivity extends AppCompatActivity {
    private ActivityScenarioInitBinding binding;
    public static final String SCENARIOS_KEY = "SCENARIOS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityScenarioInitBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());


        // INTENT
        Intent intent = new Intent(this, AdventureGameActivity.class);

        //GETTING INPUT FROM TXT FILE
        InputStream in_stream = null;
        try {
            in_stream =
                    getApplicationContext().getAssets().open("adventureGameOne/adventure1.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scanner reader = new Scanner(in_stream).useDelimiter(";|\\r\\n");
        String input_txt = "";

        Scenario scene = null;
        ArrayList<Scenario> scenarios_dynamic = new ArrayList<>();

        // READING INPUT FROM TXT
        while (reader.hasNext()) {
            input_txt = reader.next();

            if (input_txt.matches("<SCENARIO[0-9]*>")) {
                scene = new Scenario();
                continue;
            }

            if (input_txt.equals("")) {
                reader.nextLine();
                continue;
            }

            if (scene != null && input_txt.matches("btn[0-4]_txt:.*")) {
                scene.btn_txts
                        .replace(input_txt.substring(0, input_txt.indexOf(':'))
                                , input_txt.substring(input_txt.indexOf(':') + 1)
                                        .trim().replaceAll("^\"|\"$", ""));
            } else if (input_txt.matches("desc_txt:.*")) {
                scene.scene_desc_txt
                        = input_txt.substring(input_txt.indexOf(":") + 1)
                        .trim().replaceAll("^\"|\"$", "");
            }
            else if (input_txt.matches("btn[0-4]_dest:.*")){
                scene.btn_paths
                        .replace(input_txt.substring(0, input_txt.indexOf(':'))
                                , Integer
                                        .parseInt(input_txt.substring(input_txt.indexOf(':') + 1).
                                                trim().replaceAll("^\"|\"$", "")));
            }

            if (input_txt.matches("</SCENARIO[0-9]*>")) {
                determineBtnType(scene);
                scenarios_dynamic.add(scene);
            }
        }

        ArrayList<Integer> scenario_num_references =
       setScenarioNumReferences(scenarios_dynamic);

        // PASS PARCELABLE TO NEXT ACTIVITY
        intent.putParcelableArrayListExtra(SCENARIOS_KEY, scenarios_dynamic);

        startActivity(intent);
        finish();
    }


    // TODO: FIX hasExceededReferences()
    private Boolean hasExceededReferences(ArrayList<Scenario> scenarios_dynamic, ArrayList<Integer> scenario_num_references){
        for (int i = 0; i < scenarios_dynamic.size(); ++i){
            if (scenarios_dynamic.get(i).num_references_to >= Collections.frequency(scenario_num_references, i)){
                return true;
            }
        }
        return false;
    }
    private ArrayList<Integer> setScenarioNumReferences(ArrayList<Scenario> scenarios_dynamic){

        ArrayList<Integer> scenario_references = new ArrayList<Integer>();
        for (int i =0; i < scenarios_dynamic.size(); ++i){
            Scenario curr_scenario = scenarios_dynamic.get(i);

            for (Integer value : curr_scenario.btn_paths.values()){
                scenario_references.add(value);
            }
        }

        for (int i = 0; i < scenarios_dynamic.size(); ++i){
            int count = Collections
                    .frequency(scenario_references, i);
            scenarios_dynamic.get(i).num_references_to = count;
        }

        return scenario_references;
    }
    private void determineBtnType(Scenario scene){
        int btnType = 4;
        for (String value : scene.btn_txts.values()){
            if (value.equals("") || value.equals("0")) btnType--;
        }
        scene.btn_type = btnType;
    }
}