package com.example.app_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.app_test.adapter.AdventureSelectAdapter;
import com.example.app_test.adapter.Adventure_overview;
import com.example.app_test.adventures.*;
import com.example.app_test.databinding.ActivityAdventureSelectBinding;

import java.util.ArrayList;
import java.util.List;

public class AdventureSelectActivity extends AppCompatActivity {
    private ActivityAdventureSelectBinding binding;
    private RecyclerView recyclerView;
    private AdventureSelectAdapter adapter;

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
    }

    private List<Adventure_overview> getAvailableAdventures(){
        List<Adventure_overview> adventures = new ArrayList<Adventure_overview>();

        // ADD adventures here. Adventure generator

        adventures.add(new Adventure_overview(
                "Example title1", "Example desc1"
        , Sample_adventure.class));

        adventures.add(new Adventure_overview(
                "Example title2", "Example desc2", Sample_adventure.class));

        return adventures;
    }
}