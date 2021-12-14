package com.example.app_test.Utils;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.example.app_test.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;


public class btnTxtOptions extends Fragment {
    public static final String NUM_BTNS_KEY = "NUM_BTNS";
    public static final String SCENARIOS_KEY = "SCENARIOS";
    public int num_btns_to_display; // FROM ACTIVITY. newInstance()
    private ArrayList<Scenario> scenarios; // FROM ACTIVITY . newInstance()


    private TextInputLayout btn1_field, btn2_field, btn3_field, btn4_field; // PASSED TO ACTIVITY. to get input.
    private MaterialCardView btn1_cv, btn2_cv, btn3_cv, btn4_cv; // PASSED TO ACTIVITY. to determine visibility.
    private TextInputEditText btn1_ref, btn2_ref, btn3_ref, btn4_ref; // PASSED TO ACTIVTY. to get input.

    private onFieldsShownListener listener;

    public btnTxtOptions() {
        // Required empty public constructor
    }

    public interface onFieldsShownListener {
        public void onFieldsShown(ArrayList<TextInputLayout> btn_txts_layout,
                                  ArrayList<TextInputEditText> btn_ref_fields,
                                    ArrayList<MaterialCardView> input_cvs);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof onFieldsShownListener){
            this.listener = (onFieldsShownListener) context;
        }
    }

    public static btnTxtOptions newInstance(int num_btns, ArrayList<Scenario> scenarios) {

        btnTxtOptions fragment = new btnTxtOptions();

        Bundle args = new Bundle();
        args.putInt(NUM_BTNS_KEY, num_btns);
        args.putParcelableArrayList(SCENARIOS_KEY, scenarios);
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.num_btns_to_display = getArguments().getInt(NUM_BTNS_KEY);
        this.scenarios = getArguments().getParcelableArrayList(SCENARIOS_KEY); // TO INIT DROP DOWN MENU WITH RECOMMENDATIONS.

        return inflater.inflate(R.layout.fragment_btn_txt_options, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initComponents(view);
        setFieldsVisibility();

        ArrayList<TextInputLayout> valid_fields = new ArrayList<>(
                Arrays.asList(this.btn1_field, this.btn2_field,
                        this.btn3_field, this.btn4_field)
        );
        ArrayList<TextInputEditText> valid_ref_fields = new ArrayList<>(
                Arrays.asList(this.btn1_ref, this.btn2_ref, this.btn3_ref, this.btn4_ref)
        );
        ArrayList<MaterialCardView> valid_cvs = new ArrayList<>(
                Arrays.asList(this.btn1_cv, this.btn2_cv, this.btn3_cv, this.btn4_cv)
        );
        listener.onFieldsShown(valid_fields, valid_ref_fields, valid_cvs);

    }

    private void initComponents(View view){
        this.btn1_field = view.findViewById(R.id.input_btn1_area);
        this.btn2_field = view.findViewById(R.id.input_btn2_area);
        this.btn3_field = view.findViewById(R.id.input_btn3_area);
        this.btn4_field = view.findViewById(R.id.input_btn4_area);

        this.btn1_cv = view.findViewById(R.id.btn1_cv);
        this.btn2_cv = view.findViewById(R.id.btn2_cv);
        this.btn3_cv = view.findViewById(R.id.btn3_cv);
        this.btn4_cv = view.findViewById(R.id.btn4_cv);

        this.btn1_ref = view.findViewById(R.id.input_btn1_ref_field);
        this.btn2_ref = view.findViewById(R.id.input_btn2_ref_field);
        this.btn3_ref = view.findViewById(R.id.input_btn3_ref_field);
        this.btn4_ref = view.findViewById(R.id.input_btn4_ref_field);
    }
    private void setFieldsVisibility(){
        if (this.num_btns_to_display == -1) return;

        if (this.num_btns_to_display >= 1){
            this.btn1_cv.setVisibility(View.VISIBLE);
//            initNumBtnDropDown(btn1_ref);
            if (this.num_btns_to_display >= 2){
                this.btn2_cv.setVisibility(View.VISIBLE);
//                initNumBtnDropDown(btn2_ref);
                if (this.num_btns_to_display >= 3){
                    this.btn3_cv.setVisibility(View.VISIBLE);
//                    initNumBtnDropDown(btn3_ref);
                    if (this.num_btns_to_display >= 4){
                        this.btn4_cv.setVisibility(View.VISIBLE);
//                        initNumBtnDropDown(btn4_ref);
                    }
                }
            }
        }
    }


    // TODO: init dropdown menu with scenario "recommendations" -->
//    private void initNumBtnDropDown(AutoCompleteTextView drop_down_menu) {
//
//        ArrayList<String> scenario_descs = new ArrayList<>();
//        for (Scenario scenario : this.scenarios) {
//            scenario_descs.add(scenario.toString());
//        }
//
//        ArrayAdapter<String> arrayAdapter
//                = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.btn_options_dropdown_row, scenario_descs);
//        drop_down_menu.setAdapter(arrayAdapter);
//        drop_down_menu.setThreshold(1);
//
//    }
}