package com.example.app_test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.app_test.Utils.User;
import com.example.app_test.Utils.UserData;
import com.example.app_test.adapter.AdventureSelectAdapter;
import com.example.app_test.adapter.Adventure_overview;
import com.example.app_test.adventures.*;
import com.example.app_test.adventures.adventure_game_one.AdventureGameActivity;
import com.example.app_test.databinding.ActivityAdventureSelectBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdventureSelectActivity extends AppCompatActivity {
    private ActivityAdventureSelectBinding binding;
    private RecyclerView recyclerView;
    private AdventureSelectAdapter adapter;
    // sample push
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityAdventureSelectBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());
        initAdventureSelectScreen(); // sample comment
    }

    private void initAdventureSelectScreen(){
        List<Adventure_overview> adventures;
        adventures = getAvailableAdventures();

        this.adapter = new AdventureSelectAdapter(adventures, this);
        this.recyclerView = findViewById(R.id.recyclerView_adventures);
        this.recyclerView.setAdapter(this.adapter);

        RecyclerView.LayoutManager layoutMgr = new LinearLayoutManager(this);
        this.recyclerView.setLayoutManager(layoutMgr);

        this.adapter.setOnItemClickListener(
                new AdventureSelectAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) throws ClassNotFoundException {
                        Intent intent = new Intent(getBaseContext(),
                                adventures.get(position).getActivity_class_name());
                        startActivity(intent);
                    }
                }
        );

        // TODO: just experimenting with menu bar.
        binding.bottomMenuBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Menu bar clicked", Toast.LENGTH_SHORT).show();
            }
        });

        this.binding.btnAdvCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toAdvCreate = new Intent(getBaseContext(), AdventureCreateActivity.class);
                startActivity(toAdvCreate);

            }
        });
    }

    private List<Adventure_overview> getAvailableAdventures(){
        List<Adventure_overview> adventures = new ArrayList<Adventure_overview>();

        // ADD adventures here. Adventure generator

        adventures.add(new Adventure_overview(this,
                "Example title1", "Example desc1"
        , ScenarioInitActivity.class,  R.drawable.ic_launcher_foreground));

//        adventures.add(new Adventure_overview(this,
//                "Example title2", "Example desc2", ScenarioInitActivity.class,  R.drawable.ic_launcher_background));
        adventures.add(new Adventure_overview(this,
                "Example title2", "Example desc2", AdventureGameActivity.class,  R.drawable.ic_launcher_background));

        return adventures;
    }
}