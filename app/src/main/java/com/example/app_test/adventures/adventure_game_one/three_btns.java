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
import com.google.android.material.button.MaterialButton;

public class three_btns extends Fragment {
    private Scenario scenario;
    public static final String SCENARIO_KEY = "SCENARIO";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_three_btns, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MaterialButton btn_1 = view.findViewById(R.id.btn_1);
        MaterialButton btn_2 = view.findViewById(R.id.btn_2);
        MaterialButton btn_3 = view.findViewById(R.id.btn_3);

        btn_1.setText(this.scenario.btn_txts.get("btn1_txt"));
        btn_2.setText(this.scenario.btn_txts.get("btn2_txt"));
        btn_3.setText(this.scenario.btn_txts.get("btn3_txt"));
    }

    public static three_btns newInstance(Scenario scenario) {

        three_btns fragment = new three_btns();
        Bundle args = new Bundle();
        args.putParcelable(SCENARIO_KEY, scenario);
        fragment.setArguments(args);


        return fragment;
    }
}