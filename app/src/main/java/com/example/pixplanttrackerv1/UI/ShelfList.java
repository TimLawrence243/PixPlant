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
 * ShelfList
 * Moved to from the main screen
 * Displays a list of the current Shelves, and has options to add a new shelf or return to the HomeScreen
 * @see ShelfAdd
 * @see HomeScreen
 */
public class ShelfList extends AppCompatActivity {

    /**
     * onCreate is run when the activity launches
     * onCreate for ShelfList sets up the RecyclerView with the list of current Shelves
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelf_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Set up RecyclerView to display all shelves
        RecyclerView shelfRecycler = findViewById(R.id.shelfRecycler);
        Repository shelfRepo = new Repository(getApplication());
        List<Shelf> shelves = shelfRepo.getAllShelves();
        final ShelfAdapter shelfAdapter = new ShelfAdapter(this);
        shelfRecycler.setAdapter(shelfAdapter);
        shelfRecycler.setLayoutManager(new LinearLayoutManager(this));
        shelfAdapter.setShelves(shelves);
    }

    /**
     * Add Shelf button clicked
     * Moves to ShelfAdd screen to add a new shelf
     * @see ShelfAdd
     * @param view
     */
    public void onClickAddShelf(View view){
        Intent intent = new Intent(ShelfList.this, ShelfAdd.class);
        startActivity(intent);
    }

    /**
     * Back button clicked
     * Returns to HomeScreen
     * @see HomeScreen
     * @param view
     */
    public void onClickBack(View view){
        Intent intent = new Intent(ShelfList.this, HomeScreen.class);
        startActivity(intent);
    }
}