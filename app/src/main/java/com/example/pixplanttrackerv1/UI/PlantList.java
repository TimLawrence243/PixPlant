package com.example.pixplanttrackerv1.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.pixplanttrackerv1.Database.Repository;
import com.example.pixplanttrackerv1.Entity.Plant;
import com.example.pixplanttrackerv1.R;

import java.util.List;

/**
 * PlantList
 * Displays all the plants for the selected shelf in a RecyclerView
 * Also has buttons to add a new plant, edit, or delete the current shelf
 */
public class PlantList extends AppCompatActivity {
    TextView editShelfName;
    int shelfID;
    String shelfName;
    String shelfDesc;

    Repository repository;

    /**
     * onCreate is run when the activity starts
     * onCreate for PlantList will set up the activity to display the shelf name and fill the RecyclerView with plants on that shelf.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_list);
        editShelfName = findViewById(R.id.PlantListTitle);
        //Set up the repository on this page
        repository = new Repository(getApplication());

        //Get the shelf name that we brought over, add it to the title TextView
        shelfName = getIntent().getStringExtra("name");
        editShelfName.setText("Plants on " + shelfName);
        //Also get the shelf ID and description
        shelfID = getIntent().getIntExtra("id", 0);
        shelfDesc = getIntent().getStringExtra("desc");

        //If we came to PlantList from PlantMove, we update the plant that was moved in that activity
        if(PlantMove.movePlantActive == true){
            int plantID = PlantMove.movePlantID;
            repository.movePlant(shelfID, plantID);
            PlantMove.movePlantActive = false;
        }

        //Recycler view setup
        RecyclerView recyclerView = findViewById(R.id.plantRecycler);

        //Get the list of all plants for the shelf we selected
        List<Plant> plants = repository.getAllPlantsforShelf(shelfID);
        final PlantAdapter plantAdapter = new PlantAdapter(this);
        recyclerView.setAdapter(plantAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        plantAdapter.setPlants(plants);
    }

    /**
     * Add Plant button is clicked
     * Takes the ShelfID and shelfName of current shelf, moves to PlantAdd class to add a new plant to the current shelf.
     * @param view
     */
    public void onClickAddPlant(View view){
        Intent intent = new Intent(PlantList.this, PlantAdd.class);
        intent.putExtra("shelfID", shelfID);
        intent.putExtra("shelfName", shelfName);
        startActivity(intent);
    }

    public void onClickBack(View view){
        Intent intent = new Intent(PlantList.this, ShelfList.class);
        startActivity(intent);
    }

    /**
     * Edit Shelf button is clicked
     * Takes the shelfID, shelfName, and shelfDescription for the current shelf and moves to ShelfAdd class to edit it.
     * @param view
     */
    public void onClickEditShelf(View view){
        Intent intent = new Intent(PlantList.this, ShelfAdd.class);
        intent.putExtra("isEdit", true);
        intent.putExtra("shelfID", shelfID);
        intent.putExtra("shelfName", shelfName);
        intent.putExtra("shelfDesc", shelfDesc);
        startActivity(intent);
    }

    /**
     * Delete shelf button is clicked
     * First, checks if there are any plants on the shelf.
     * If there are plants assigned, does NOT delete the shelf.  Prompts user to delete or move plants on the shelf first.
     * If no plants assigned, deletes the shelf from the database.
     * @param view
     */
    public void onClickDeleteShelf(View view){
        //Check if there are any plants currently on the shelf before deleting
        int plantsOnShelf = repository.checkPlantsOnShelf(shelfID);
        //If we have greater than 0 plants attached to the shelf, create alertDialog warning of plants on shelf
        if(plantsOnShelf != 0){
            AlertDialog.Builder shelfAlert = new AlertDialog.Builder(this);
            shelfAlert.setTitle("Plants on shelf");
            shelfAlert.setMessage("Shelf cannot be deleted - Plants are on shelf.  Move or delete plants on shelf first");
            shelfAlert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //Do nothing
                }
            });
            AlertDialog alertDialog = shelfAlert.create();
            alertDialog.show();
        } else {
            //Delete the current shelf
            repository.deleteShelf(shelfID);

            //Return to Shelf List screen
            Intent intent = new Intent(PlantList.this, ShelfList.class);
            startActivity(intent);
        }
    }
}