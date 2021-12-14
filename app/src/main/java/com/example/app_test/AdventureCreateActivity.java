package com.example.app_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.example.app_test.Utils.btnTxtOptions;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


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

        this.binding.finalizeAdventureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (scenarios.size() >= 2){
                    try {
                        writeToFile("temp");
                        Toast.makeText(AdventureCreateActivity.this, "Adventure created!", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else Toast.makeText
                        (AdventureCreateActivity.this, "Must at least contain 2 scenarios", Toast.LENGTH_SHORT).show();
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


            for (int i = 0; i < this.ref_drop_down_menus.size(); ++i){

                String btn_ref_key = String.format("btn%d_dest", i + 1);
                String input_btn_ref_desc = this.ref_drop_down_menus.get(i).getText().toString();

                int btn_ref_index = -1;
                for (int j = 0; j < this.scenarios.size(); ++j){
                    if (this.scenarios.get(j).scene_desc_txt.equals(input_btn_ref_desc)){
                        btn_ref_index = j;
                        break;
                    }
                }
                this.scenario_to_add.btn_paths.replace(btn_ref_key, btn_ref_index);

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

    private Boolean hasInvalidInput() {
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


        // checking for all btn txt field inputs and drop down menus for btn_references
        if (this.btn_txt_field_areas != null) {
            for (int i = 0; i < this.btn_txt_field_areas.size(); ++i) {
                if (this.input_cvs_fragment.get(i).getVisibility() == View.VISIBLE) {

                    EditText btn_edit_txt = this.btn_txt_field_areas.get(i).getEditText();
                    if (btn_edit_txt != null) {
                        if (btn_edit_txt.getText().toString().isEmpty()) {
                            this.btn_txt_field_areas.get(i).setError("*Required");
                            hasInvalidInput = true;
                        } else this.btn_txt_field_areas.get(i).setError(null);
                    }

                    TextInputLayout ref_layout = (TextInputLayout) this.ref_drop_down_menus.get(i).getParent().getParent();
                    if (this.ref_drop_down_menus.get(i).getText().toString().isEmpty()){
                        ref_layout.setError("*Required");
                        hasInvalidInput = true;
                    } else ref_layout.setError(null);
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

    }

    @SuppressLint("DefaultLocale")
    private void writeToFile(String file_name) throws IOException {
        file_name += ".txt";
        File path = getApplicationContext().getFilesDir();
        FileOutputStream writer = new FileOutputStream(new File(path, file_name));

        String formattedTxt = "";
        for (int i = 0 ; i < this.scenarios.size(); ++i){
            formattedTxt += String.format("<SCENARIO%d>\r\n", i);
            Scenario currScenario = this.scenarios.get(i);

            if (currScenario.isEnding) formattedTxt += String.format("end_txt: \"%s\";\r\n", currScenario.scene_desc_txt);
            else formattedTxt += String.format("desc_txt: \"%s\";\r\n", currScenario.scene_desc_txt);

            for (Map.Entry<String, String> entry : currScenario.btn_txts.entrySet()){ // BTN_TXTS
                if (!entry.getValue().equals("")){
                    formattedTxt += String.format("%s:\"%s\";\r\n", entry.getKey(), entry.getValue());
                }
            }

            for (Map.Entry<String, Integer> entry : currScenario.btn_paths.entrySet()){ // BTN_PATHS
                if (!entry.getValue().equals(-1)){
                    formattedTxt += String.format("%s:%d;\r\n", entry.getKey(), entry.getValue());
                }
            }

            formattedTxt += String.format("</SCENARIO%d>\r\n", i);
            writer.write(formattedTxt.getBytes());
            formattedTxt = "";
        }
        writer.close();
    }
}


