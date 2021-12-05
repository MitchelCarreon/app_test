package com.example.app_test.adventures.adventure_game_one;

import android.content.Context;
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


// POPULATES ALL!!!! VIEWS.
public class four_btns extends Fragment {
    private Scenario scenario;
    public static final String SCENARIO_KEY = "SCENARIO";

    private onPopulateListenerBTN4 listener;
    public interface onPopulateListenerBTN4 {
        public void onPopulateViewsBTN4(String text_desc);
        public void onButtonClickBTN4(int scenario_index);
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof onPopulateListenerBTN4){
            listener = (onPopulateListenerBTN4) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.scenario = getArguments().getParcelable(SCENARIO_KEY);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_four_btns, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MaterialButton btn_1 = view.findViewById(R.id.btn_1);
        MaterialButton btn_2 = view.findViewById(R.id.btn_2);
        MaterialButton btn_3 = view.findViewById(R.id.btn_3);
        MaterialButton btn_4 = view.findViewById(R.id.btn_4);

        btn_1.setText(this.scenario.btn_txts.get("btn1_txt"));
        btn_2.setText(this.scenario.btn_txts.get("btn2_txt"));
        btn_3.setText(this.scenario.btn_txts.get("btn3_txt"));
        btn_4.setText(this.scenario.btn_txts.get("btn4_txt"));

        listener.onPopulateViewsBTN4(this.scenario.scene_desc_txt);


        // viewmodel comes in here.
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonClickBTN4(scenario.btn_paths.get("btn1_dest"));
            }
        });
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
