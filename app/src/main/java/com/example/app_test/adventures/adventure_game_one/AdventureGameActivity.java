package com.example.app_test.adventures.adventure_game_one;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.example.app_test.R;
import com.example.app_test.databinding.ActivityAdventureGameBinding;

public class AdventureGameActivity extends AppCompatActivity {
    private ActivityAdventureGameBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityAdventureGameBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());
    }
}