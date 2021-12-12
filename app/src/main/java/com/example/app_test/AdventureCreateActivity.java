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
import android.widget.AutoCompleteTextView;
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
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
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
    private ArrayList<AutoCompleteTextView> ref_drop_down_menus;
    private ArrayList<MaterialCardView> input_cvs_fragment;


    // TEMP
    private ArrayList<Integer> scene_ref_indices;

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
                Fragment fragment = btnTxtOptions.newInstance(input_num_btns, scenarios);
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

                if (checkedButton.getText().toString().equals("End")) {
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
        if (selected_btn.getText().toString().equals("End")) {
            this.scenario_to_add.isEnding = true;
        }

        this.scenario_to_add.btn_type = this.input_num_btns;

        if (!selected_btn.getText().toString().equals("End")) {
            for (int i = 0; i < this.btn_txt_field_areas.size(); ++i) {
                String btn_txt_key = String.format("btn%d_txt", i + 1);
                this.scenario_to_add.btn_txts.replace(btn_txt_key, this.btn_txt_field_areas.get(i).getEditText().getText().toString());
            }


            for (int i = 0; i < this.scene_ref_indices.size(); ++i){
                String btn_dest_key = String.format("btn%d_dest", i + 1);
                this.scenario_to_add.btn_paths.replace(btn_dest_key, this.scene_ref_indices.get(i));
            }

        }

        // ADDING THE SCENARIO
        scenarios.add(this.scenario_to_add);
        clearFields();
    }

    private void clearFields() {
        Toast.makeText(AdventureCreateActivity.this, "Scenario added", Toast.LENGTH_SHORT).show();
        this.binding.inputScenarioDesc.setText("");
        this.binding.inputSceneTypeNormal.setChecked(true);
        this.binding.inputNumBtnsDropdown.setText("");

        if (this.btn_txt_field_areas != null) {
            for (int i = 0; i < this.btn_txt_field_areas.size(); ++i) {
                EditText btn_txt_field = this.btn_txt_field_areas.get(i).getEditText();
                if (btn_txt_field != null) btn_txt_field.setText("");
            }
        }

        this.binding.inputScenarioDesc.requestFocus();
        this.binding.btnTxtFields.setVisibility(View.GONE);
        collapseVirtualKeyboard();

    }

    private void collapseVirtualKeyboard() {
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private Boolean hasInvalidInput() { // T_T
        Boolean hasInvalidInput = false;

        if (this.binding.inputScenarioDesc.getText().toString().isEmpty()) {
            this.binding.inputScenarioDescArea.setError("*Required");
            hasInvalidInput = true;
        } else this.binding.inputScenarioDescArea.setError(null);

        RadioButton selected_btn = findViewById(this.binding.inputScenarioTypeRadioGroup.getCheckedRadioButtonId());

        if (this.input_num_btns == INVALID_CHOICE) {
            if (!selected_btn.getText().toString().equals("End")) {
                this.binding.numBtnsDropdownInputLayout.setError("*Required");
                hasInvalidInput = true;
            }

        } else this.binding.numBtnsDropdownInputLayout.setError(null);


        // checking for btn txt field inputs
        if (this.btn_txt_field_areas != null) {
            for (int i = 0; i < this.btn_txt_field_areas.size(); ++i) {
                if (this.input_cvs_fragment.get(i).getVisibility() == View.VISIBLE) {
                    EditText btn1_edit_txt = this.btn_txt_field_areas.get(i).getEditText();
                    if (btn1_edit_txt != null) {
                        if (btn1_edit_txt.getText().toString().isEmpty()) {
                            this.btn_txt_field_areas.get(i).setError("*Required");
                            hasInvalidInput = true;
                        } else this.btn_txt_field_areas.get(i).setError(null);
                    }
                }
            }
        } else hasInvalidInput = true;

        if (selected_btn.getText().toString().equals("End") && !this.binding.inputScenarioDesc.getText().toString().isEmpty()) {
            hasInvalidInput = false;
        }

        return hasInvalidInput;
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
    public void onFieldsShown(ArrayList<TextInputLayout> btn_txts_layout,
                              ArrayList<AutoCompleteTextView> ref_drop_down_menus,
                              ArrayList<MaterialCardView> input_cvs) {
        this.btn_txt_field_areas = btn_txts_layout;
        this.ref_drop_down_menus = ref_drop_down_menus;
        this.input_cvs_fragment = input_cvs;



        for (int i = 0; i < input_num_btns; ++i) {
            ref_drop_down_menus.get(i).setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String scenario_title = parent.getItemAtPosition(position).toString();
                    scene_ref_indices = new ArrayList<>(input_num_btns);

                    for (int j = 0; j < scenarios.size(); ++j) {
                        if (scenarios.get(j).scene_desc_txt.equals(scenario_title)) {
                            scene_ref_indices.add(j);
                        }
                    }
                }
            });
        }
    }


}