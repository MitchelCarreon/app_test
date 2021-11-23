package com.example.app_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.example.app_test.databinding.ActivityMainBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(this.binding.getRoot());


        this.binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AdventureSelectActivity.class);
                if (verifyCredentials()) startActivity(intent);
            }
        });
    }

    private Boolean verifyCredentials() {

        // if any one of the fields are empty.
        if (this.binding.usernameField.getEditText() == null
                || this.binding.passwordField.getEditText() == null) return false;

        // admin login
        if (this.binding.usernameField.getEditText().getText().toString().equals("admin") &&
                this.binding.passwordField.getEditText().getText().toString().equals("admin")) {
            return true;
        }

        // TODO: other login options here.
        return false;

    }
}