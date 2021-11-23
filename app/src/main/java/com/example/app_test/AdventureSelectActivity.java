package com.example.app_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.app_test.adapter.AdventureSelectAdapter;
import com.example.app_test.adapter.Adventure_overview;
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
        initAdventureSelectScreen();
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
                    public void onItemClick(int position) {

                    }
                }
        );
    }

    private List<Adventure_overview> getAvailableAdventures(){
        List<Adventure_overview> adventures = new ArrayList<Adventure_overview>();

        // ADD adventures here.
        adventures.add(new Adventure_overview("Example title", "Example desc"));

        return adventures;
    }
}