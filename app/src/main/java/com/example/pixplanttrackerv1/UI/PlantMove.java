package com.example.pixplanttrackerv1.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.pixplanttrackerv1.Database.Repository;
import com.example.pixplanttrackerv1.Entity.Shelf;
import com.example.pixplanttrackerv1.R;

import java.util.List;

/**
 * PlantMove
 * UI to move a plant from one shelf to another
 */
public class PlantMove extends AppCompatActivity {
    public static int movePlantID;
    public static boolean movePlantActive;

    int plantID;
    String plantName;
    String plantDesc;
    Long plantCreate;
    int plantDays;
    int plantShelfID;

    /**
     * onCreate is run when the activity launches
     * onCreate for PlantMove will populate a RecyclerView with the available Shelves
     * Clicking a shelf moves the plant we've selected to that shelf
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_move);

        movePlantID = getIntent().getIntExtra("plantID", 0);
        movePlantActive = true;

        plantID = getIntent().getIntExtra("plantID", 0);
        plantName = getIntent().getStringExtra("plantName");
        plantDesc = getIntent().getStringExtra("plantDesc");
        plantCreate = getIntent().getLongExtra("plantCreate", 0);
        plantDays = getIntent().getIntExtra("plantDays", 0);
        plantShelfID = getIntent().getIntExtra("plantShelfID", 0);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Set up RecyclerView to display all shelves
        RecyclerView shelfRecycler = findViewById(R.id.moveRecycler);
        Repository shelfRepo = new Repository(getApplication());
        List<Shelf> shelves = shelfRepo.getAllShelves();
        final ShelfAdapter shelfAdapter = new ShelfAdapter(this);
        shelfRecycler.setAdapter(shelfAdapter);
        shelfRecycler.setLayoutManager(new LinearLayoutManager(this));
        shelfAdapter.setShelves(shelves);


    }

    /**
     * Cancel button
     * Moves back to PlantDetail, does not move the plant to a new shelf
     * @param view
     */
    public void onClickCancel(View view){
        movePlantActive = false;
        Intent intent = new Intent(PlantMove.this, PlantDetail.class);
        intent.putExtra("id", plantID);
        intent.putExtra("name", plantName);
        intent.putExtra("detail", plantDesc);
        intent.putExtra("create", plantCreate);
        intent.putExtra("days", plantDays);
        intent.putExtra("shelfID", plantShelfID);

        startActivity(intent);
    }


}