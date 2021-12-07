package com.example.app_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.example.app_test.Utils.Scenario;
import com.example.app_test.Utils.ScenarioViewModel;
import com.example.app_test.databinding.ActivityAdventureCreateBinding;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.app_test.Utils.btnTxtOptions;
import com.google.android.material.textfield.TextInputLayout;

public class AdventureCreateActivity extends AppCompatActivity implements btnTxtOptions.onPopulateListener {
    private ActivityAdventureCreateBinding binding;
    private ArrayList<Scenario> scenarios;



    Scenario scenario_to_add;
    int num_btns = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityAdventureCreateBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        this.scenarios = new ArrayList<>();
        initNumBtnDropDown();


        this.binding.inputNumBtnsDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                num_btns = Integer.parseInt(parent.getItemAtPosition(position).toString());

                Fragment fragment = btnTxtOptions.newInstance(num_btns);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.btn_txt_fields, fragment);
                ft.commit();
            }
        });

        this.binding.addScenarioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               createScenarioFromInput();
                System.out.println("Breakpoint here. Check scenario list's contents.");
            }
        });

    }
    private void createScenarioFromInput(){
        this.scenario_to_add = new Scenario();

        if (this.binding.inputScenarioDesc.getText() != null){
           this.scenario_to_add.scene_desc_txt = binding.inputScenarioDesc.getText().toString();
        }

        int selectedTypeID = this.binding.inputScenarioTypeRadioGroup.getCheckedRadioButtonId();
        RadioButton selected_btn = findViewById(selectedTypeID);
        if (selected_btn.getText().toString().equals("END")) this.scenario_to_add.isEnding = true;

        this.scenario_to_add.btn_type = this.num_btns;



        // TODO: check for empty fields here. IF empty , set scenario_to_add.btntxt = ""
        scenarios.add(this.scenario_to_add);
    }



    private void initNumBtnDropDown() {
        String[] btn_options = getResources().getStringArray(R.array.button_num);
        ArrayAdapter<String> arrayAdapter
                = new ArrayAdapter<>(getApplicationContext(), R.layout.btn_options_dropdown_row, btn_options);
        this.binding.inputNumBtnsDropdown.setAdapter(arrayAdapter);
        this.binding.inputNumBtnsDropdown.setThreshold(1);
        //    R.layout.support_simple_spinner_dropdown_item --> default layout to pass in instantiating ArrayAdapter
    }

    @Override
    public void onFieldsFilled(ArrayList<TextInputLayout> btn_txts_layout) {

    }
}