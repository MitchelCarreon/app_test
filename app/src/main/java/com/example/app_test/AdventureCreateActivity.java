package com.example.app_test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;


import com.example.app_test.databinding.ActivityAdventureCreateBinding;

public class AdventureCreateActivity extends AppCompatActivity {
    private ActivityAdventureCreateBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityAdventureCreateBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        initNumBtnDropDown();



    }

    private void initNumBtnDropDown(){
        String[] btn_options = getResources().getStringArray(R.array.button_num);
        ArrayAdapter<String> arrayAdapter
                = new ArrayAdapter<>(getApplicationContext(), R.layout.btn_options_dropdown_row , btn_options);
        this.binding.numBtnsDropdown.setAdapter(arrayAdapter);
        this.binding.numBtnsDropdown.setThreshold(1);
    }
    //    R.layout.support_simple_spinner_dropdown_item --> default layout to pass in instantiating ArrayAdapter
    private void initRadioBtns(){
        this.binding.scenarioTypeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

            }
        });
    }
    // radioButton.isChecked is the ATTIBUTE.
}