package com.example.app_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.example.app_test.Utils.Scenario;
import com.example.app_test.Utils.UserData;
import com.example.app_test.databinding.ActivityAdventureCreateBinding;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.app_test.Utils.btnTxtOptions;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
/*
* NOTE: The point of this Activity is to help the user determine the logic for his story and prevent logical errors.
* Logical errors like scenarios with no buttons/options are the most common.
*
* Logic RULES:
* - There can only be one beginning ("Begin" - scenario type)
* - There can be multiple endings ("End" - scenario type)
* - Other scenarios that don't have little to no constraints are ("Normal" - scenario type)
* - Logical constraints should be minimal. Otherwise, the user might end up with a linear story (etc. no going back to scenarios, etc.)
* */
public class AdventureCreateActivity extends AppCompatActivity implements btnTxtOptions.onFieldsShownListener {
    private ActivityAdventureCreateBinding binding;
    public static final int INVALID_CHOICE = -1;

    private ArrayList<Scenario> scenarios;
    private Scenario scenario_to_add;

    private int input_num_btns = INVALID_CHOICE;


    //FROM FRAGMENT
    private ArrayList<TextInputLayout> btn_txt_field_areas;

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
                input_num_btns = Integer.parseInt(parent.getItemAtPosition(position).toString());

                binding.btnTxtFields.setVisibility(View.VISIBLE);
                Fragment fragment = btnTxtOptions.newInstance(input_num_btns);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.btn_txt_fields, fragment);
                ft.commit();
            }
        });
        this.binding.addScenario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.fabExpandMenuButton.collapse();
                createScenarioFromInput();
                System.out.println("Breakpoint here. Check scenario list's contents.");
            }
        });
        this.binding.inputScenarioTypeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedButton = (RadioButton) group.findViewById(checkedId);

                if (checkedButton.getText().toString().equals("End")){
                    binding.numBtnsDropdownInputLayout.setEnabled(false);
                    binding.btnTxtFields.setVisibility(View.GONE);

                } else {
                    binding.numBtnsDropdownInputLayout.setEnabled(true);
                    binding.btnTxtFields.setEnabled(true);
                    binding.btnTxtFields.setVisibility(View.VISIBLE);
                }
            }
        });

    }


    private void createScenarioFromInput() {
        if (hasInvalidInput()) return;
        this.scenario_to_add = new Scenario();

        if (this.binding.inputScenarioDesc.getText() != null) {
            this.scenario_to_add.scene_desc_txt = binding.inputScenarioDesc.getText().toString();
        }

        int selectedTypeID = this.binding.inputScenarioTypeRadioGroup.getCheckedRadioButtonId();
        RadioButton selected_btn = findViewById(selectedTypeID);
        if (selected_btn.getText().toString().equals("End")){
            this.scenario_to_add.isEnding = true;
        }

        this.scenario_to_add.btn_type = this.input_num_btns;

        if (!selected_btn.getText().toString().equals("End")){
            for (int i = 0; i < this.btn_txt_field_areas.size(); ++i) {
                String btn_txt_key = String.format("btn%d_txt", i + 1);
                this.scenario_to_add.btn_txts.replace(btn_txt_key, this.btn_txt_field_areas.get(i).getEditText().getText().toString());
            }
        }

        scenarios.add(this.scenario_to_add);
        clearFields();
    }

    private void clearFields() {
        Toast.makeText(AdventureCreateActivity.this, "Scenario added", Toast.LENGTH_SHORT).show();
        this.binding.inputScenarioDesc.setText("");
        this.binding.inputSceneTypeNormal.setChecked(true);
        this.binding.inputNumBtnsDropdown.setText("");

        for (int i = 0; i < this.btn_txt_field_areas.size(); ++i){
            EditText btn_txt_field = this.btn_txt_field_areas.get(i).getEditText();
            if (btn_txt_field != null) btn_txt_field.setText("");
        }

        this.binding.inputScenarioDesc.requestFocus();
        this.binding.btnTxtFields.setVisibility(View.GONE);
        collapseVirtualKeyboard();

    }

    private void collapseVirtualKeyboard(){
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private Boolean hasInvalidInput() {
        Boolean hasInvalidInput = false;

        if (this.binding.inputScenarioDesc.getText().toString().isEmpty()) {
            this.binding.inputScenarioDescArea.setError("*Required");
            hasInvalidInput = true;
        } else this.binding.inputScenarioDescArea.setError(null);

        RadioButton selected_btn = findViewById(this.binding.inputScenarioTypeRadioGroup.getCheckedRadioButtonId());

        if (this.input_num_btns == INVALID_CHOICE) {
            if (!selected_btn.getText().toString().equals("End")){
                this.binding.numBtnsDropdownInputLayout.setError("*Required");
                hasInvalidInput = true;
            }

        } else this.binding.numBtnsDropdownInputLayout.setError(null);


        checkBtnTxtInput(hasInvalidInput);


        return hasInvalidInput;
    }

    private void checkBtnTxtInput(Boolean hasInvalidInput) {
        RadioButton selected_btn = findViewById(this.binding.inputScenarioTypeRadioGroup.getCheckedRadioButtonId());
        if (selected_btn.getText().toString().equals("End")){
            hasInvalidInput = false;
            return;
        }

        if (this.btn_txt_field_areas == null) {
            hasInvalidInput = true;
            return;
        }
        if (this.btn_txt_field_areas.get(0).getVisibility() == View.VISIBLE) {
            EditText btn1_edit_txt = this.btn_txt_field_areas.get(0).getEditText();
            if (btn1_edit_txt != null) {
                if (btn1_edit_txt.getText().toString().isEmpty()) {
                    this.btn_txt_field_areas.get(0).setError("*Required");
                    hasInvalidInput = true;
                } else this.btn_txt_field_areas.get(0).setError(null);
            }
        }

        if (this.btn_txt_field_areas.get(1).getVisibility() == View.VISIBLE) {
            EditText btn1_edit_txt = this.btn_txt_field_areas.get(1).getEditText();
            if (btn1_edit_txt != null) {
                if (btn1_edit_txt.getText().toString().isEmpty()) {
                    this.btn_txt_field_areas.get(1).setError("*Required");
                    hasInvalidInput = true;
                } else this.btn_txt_field_areas.get(1).setError(null);
            }
        }

        if (this.btn_txt_field_areas.get(2).getVisibility() == View.VISIBLE) {
            EditText btn1_edit_txt = this.btn_txt_field_areas.get(2).getEditText();
            if (btn1_edit_txt != null) {
                if (btn1_edit_txt.getText().toString().isEmpty()) {
                    this.btn_txt_field_areas.get(2).setError("*Required");
                    hasInvalidInput = true;
                } else this.btn_txt_field_areas.get(2).setError(null);
            }
        }

        if (this.btn_txt_field_areas.get(3).getVisibility() == View.VISIBLE) {
            EditText btn1_edit_txt = this.btn_txt_field_areas.get(3).getEditText();
            if (btn1_edit_txt != null) {
                if (btn1_edit_txt.getText().toString().isEmpty()) {
                    this.btn_txt_field_areas.get(3).setError("*Required");
                    hasInvalidInput = true;
                } else this.btn_txt_field_areas.get(3).setError(null);
            }
        }
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
    public void onFieldsShown(ArrayList<TextInputLayout> btn_txts_layout) {
        this.btn_txt_field_areas = btn_txts_layout;
    }
}