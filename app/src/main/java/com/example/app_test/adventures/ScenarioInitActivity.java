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
            else if (input_txt.matches("end_txt:.*")){
                scene.scene_desc_txt = input_txt.substring(input_txt.indexOf(":") + 1)
                        .trim().replaceAll("^\"|\"$", "");
                scene.isEnding = true;
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


        if (!hasExceededReferences(scenarios_dynamic, scenario_num_references)){
            startActivity(intent);
            finish();
        }
        else {
            Intent backToAdvSelect = new Intent(this, AdventureSelectActivity.class);
            startActivity(backToAdvSelect);
        }

    }


    // TODO: FIX hasExceededReferences()??
    private Boolean hasExceededReferences(ArrayList<Scenario> scenarios_dynamic, ArrayList<Integer> scenario_num_references){

        // special case for beginning scenario
        if (scenarios_dynamic.get(0).num_references_to >= scenarios_dynamic.get(0).btn_type){
            return true;
        }
        for (int i = 1; i < scenarios_dynamic.size(); ++i){

            // # of references_to_scenario CANNOT BE greater than # of buttons_in_scenario
            if (!scenarios_dynamic.get(i).isEnding && (scenarios_dynamic.get(i).num_references_to > scenarios_dynamic.get(i).btn_type) ){
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