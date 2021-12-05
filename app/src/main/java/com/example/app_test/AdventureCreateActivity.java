package com.example.app_test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.app_test.databinding.ActivityAdventureCreateBinding;

public class AdventureCreateActivity extends AppCompatActivity {
    private ActivityAdventureCreateBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityAdventureCreateBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());
    }
}