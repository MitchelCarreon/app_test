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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class AdventureSelectActivity extends AppCompatActivity {
    public static final String ADVENTURE_TITLE_KEY = "ADVENTURE TITLE";

    private ActivityAdventureSelectBinding binding;
    private RecyclerView recyclerView;
    private AdventureSelectAdapter adapter;
    // sample push
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityAdventureSelectBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());
        try {
            initAdventureSelectScreen(); // sample comment
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initAdventureSelectScreen() throws FileNotFoundException {
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
                        intent.putExtra(ADVENTURE_TITLE_KEY, adventures.get(position).getTitle());
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

    private List<Adventure_overview> getAvailableAdventures() throws FileNotFoundException {

        List<Adventure_overview> adventures = new ArrayList<Adventure_overview>();

        FileInputStream in_stream = null;
        File path = getApplicationContext().getFilesDir();
        List<String> file_names = Arrays.asList(path.list()); // TODO: CHECK HERE FOR ALL THE FILES SAAAAAAAVED!!!!

        for (int i = 0; i < file_names.size(); ++i){
            File readFrom = new File(path, file_names.get(i));
            in_stream = new FileInputStream(readFrom);
            Scanner reader = new Scanner(in_stream).useDelimiter(";|\\r\\n");

            String title = "";
            String adventure_description = "";
            while (title.equals("") || adventure_description.equals("")){
                String input_txt = reader.next();
                // TODO: uncomment the real one
                if (input_txt.matches("TITLE:.*")) {
                    title = input_txt.substring(input_txt.lastIndexOf(':') + 1).trim().replaceAll("^\"|\"$", "");
                }
                if (input_txt.matches("ADV_DESC:.*")){
                    adventure_description = input_txt.substring(input_txt.lastIndexOf(':') + 1).trim().replaceAll("^\"|\"$", "");
                }

            }

            adventures.add(new Adventure_overview(this,
                    title, adventure_description
                    , ScenarioInitActivity.class, R.drawable.ic_launcher_foreground));

        }


//        adventures.add(new Adventure_overview(this,
//                "Example title1 - TEST", "Example desc1 - TEST"
//        , ScenarioInitActivity.class,  R.drawable.ic_launcher_foreground));

//        adventures.add(new Adventure_overview(this,
//                "Example title2", "Example desc2", ScenarioInitActivity.class,  R.drawable.ic_launcher_background));

        return adventures;
    }
}