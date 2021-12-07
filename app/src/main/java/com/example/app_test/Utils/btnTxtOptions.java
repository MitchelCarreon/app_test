package com.example.app_test.Utils;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app_test.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;


public class btnTxtOptions extends Fragment {
    public static final String NUM_BTNS_KEY = "NUM_BTNS";

    public int num_btns_to_display;
    private TextInputLayout btn1_field, btn2_field, btn3_field, btn4_field;

    private onPopulateListener listener;

    // TODO: Fix fragment for btn_txt input (AdventureCreateActivity).
    public btnTxtOptions() {
        // Required empty public constructor
    }

    public interface onPopulateListener{
        public void onFieldsFilled(ArrayList<TextInputLayout> btn_txts_layout);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof onPopulateListener){
            this.listener = (onPopulateListener) context;
        }
    }

    public static btnTxtOptions newInstance(int num_btns) {

        btnTxtOptions fragment = new btnTxtOptions();

        Bundle args = new Bundle();
        args.putInt(NUM_BTNS_KEY, num_btns);
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.num_btns_to_display = getArguments().getInt(NUM_BTNS_KEY);

        return inflater.inflate(R.layout.fragment_btn_txt_options, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.btn1_field = view.findViewById(R.id.input_btn1_area);
        this.btn2_field = view.findViewById(R.id.input_btn2_area);
        this.btn3_field = view.findViewById(R.id.input_btn3_area);
        this.btn4_field = view.findViewById(R.id.input_btn4_area);

        setFieldsVisibility();
    }

    private void setFieldsVisibility(){
        if (this.num_btns_to_display >= 1){
            this.btn1_field.setVisibility(View.VISIBLE);
            if (this.num_btns_to_display >= 2){
                this.btn2_field.setVisibility(View.VISIBLE);
                if (this.num_btns_to_display >= 3){
                    this.btn3_field.setVisibility(View.VISIBLE);
                    if (this.num_btns_to_display >= 4){
                        this.btn4_field.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }
}