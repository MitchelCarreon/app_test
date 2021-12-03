package com.example.app_test.adventures.adventure_game_one;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app_test.R;
import com.example.app_test.Utils.Scenario;
import com.example.app_test.adventures.ScenarioInitActivity;
import com.example.app_test.databinding.FragmentFourBtnsBinding;

import java.util.ArrayList;


public class four_btns extends Fragment {
    private Scenario scenario;
    private FragmentFourBtnsBinding binding;

    public static final String SCENARIO_KEY = "SCENARIO";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.binding = FragmentFourBtnsBinding.inflate(getLayoutInflater());
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_four_btns, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.scenario = getArguments().getParcelable(SCENARIO_KEY);

        this.binding.btn1.setText(this.scenario.btn_txts.get("btn1_txt"));
        this.binding.btn2.setText(this.scenario.btn_txts.get("btn2_txt"));
        this.binding.btn3.setText(this.scenario.btn_txts.get("btn3_txt"));
        this.binding.btn4.setText(this.scenario.btn_txts.get("btn4_txt"));

    }

    // USED IN AdventureGameActivity to pass a scenario.
    public static four_btns newInstance(Scenario scenario){
        four_btns fragment = new four_btns();

        Bundle args = new Bundle();
        args.putParcelable(SCENARIO_KEY, scenario);
        fragment.setArguments(args);

        return fragment;
    }
}
