package com.example.app_test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import com.example.app_test.databinding.ActivitySignupBinding;

public class SignupActivity extends AppCompatActivity {
    private ActivitySignupBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());
        setTitle(getString(R.string.signup_title));
//        this.binding.inputPasswordretypeArea.setFocusable(true);
//        this.binding.userAndPwArea.setChecked(true);


    }
}