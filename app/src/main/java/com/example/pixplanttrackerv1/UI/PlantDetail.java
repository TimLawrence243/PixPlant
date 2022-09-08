package com.example.pixplanttrackerv1.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.pixplanttrackerv1.Database.Repository;
import com.example.pixplanttrackerv1.R;

import org.w3c.dom.Text;

import java.util.Date;

import static com.example.pixplanttrackerv1.Converter.DateConverter.toDate;

/**
 * PlantDetail
 * PlantDetail screen is where we transfer to from the PlantList screen when clicking a plant.
 * This screen shows all details of the plant - Name, description, creation date, how many days between waterings, next watering, and what shelf the plant is on.
 * All this info is displayed within the GUI
 * In addition, we have Edit, Move, and Delete options for the plant
 */
public class PlantDetail extends AppCompatActivity {
    TextView txtPlantName;
    TextView txtPlantDesc;
    TextView txtPlantDays;
    TextView txtPlantNextWater;
    TextView txtPlantCreate;
    TextView txtPlantShelf;

    int plantID;
    String plantName;
    String plantDesc;
    Long plantCreateLong;
    Date plantCreateDate;
    int plantDays;
    int plantShelfID;

    Repository repository;

    /**
     * onCreate is run when the activity launches
     * For PlantDetail, we get and fill all the info for the screen:
     * Name, description, days between watering, calculate the next watering, create date, and what shelf the plant is on.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_detail);

        //Set up repository
        repository = new Repository(getApplication());

        //Set up all the TextViews
        txtPlantName = findViewById(R.id.txtPlantName);
        txtPlantDesc = findViewById(R.id.txtPlantDesc);
        txtPlantDays = findViewById(R.id.txtPlantDays);
        txtPlantNextWater = findViewById(R.id.txtPlantNextWater);
        txtPlantCreate = findViewById(R.id.txtPlantCreate);
        txtPlantShelf = findViewById(R.id.txtPlantShelf);

        //Get all the variables from intent
        plantID = getIntent().getIntExtra("id", 0);
        plantName = getIntent().getStringExtra("name");
        plantDesc = getIntent().getStringExtra("detail");
        plantCreateLong = getIntent().getLongExtra("create", 0);
        plantCreateDate = toDate(plantCreateLong);
        plantDays = getIntent().getIntExtra("days", 0);
        plantShelfID = getIntent().getIntExtra("shelfID", 0);

        //Figure out the next watering day for the plant
        //** A better explanation for this can be found on the HomeScreen class **
        Long todayEpoch = ((System.currentTimeMillis() - 3600000) / 86400000);
        Long plantCreateEpoch = (plantCreateLong / 86400000);

        Long daysToNext = plantDays - ((todayEpoch - plantCreateEpoch) % plantDays);
        int daysToNextWater = daysToNext.intValue();
        String daysToNextWaterString;

        switch (daysToNextWater){
            case 0:
                daysToNextWaterString = "Today";
                break;
            case 1:
                daysToNextWaterString = "Tomorrow";
                break;
            default:
                daysToNextWaterString = "";
        }


        //Fill the textviews with the plant data
        txtPlantName.setText(plantName);
        txtPlantDesc.setText(plantDesc);
        txtPlantDays.setText("Days between watering: " + plantDays);

        //For the days to next water field
        //If it's today or tomorrow, output today or tomorrow.  Otherwise, just put out the number of days to next water
        if(daysToNextWaterString.isEmpty()){
            txtPlantNextWater.setText("Next watering: " + daysToNextWater + " days");
        } else {
            txtPlantNextWater.setText("Next watering: " + daysToNextWaterString);
        }

        txtPlantCreate.setText("Created on: " + plantCreateDate.toString());

        //On shelf field
        //Get the name of the shelf from the ID we've brought over
        String shelfName = repository.getShelfNameFromID(plantShelfID);
        txtPlantShelf.setText("On shelf: " + shelfName);
    }

    /**
     * Edit Plant button clicked
     * Move to PlantAdd class, bring over information for this plant
     * @see PlantAdd
     * @param view
     */
    public void onClickEditPlant(View view){
        Intent intent = new Intent(PlantDetail.this, PlantAdd.class);
        intent.putExtra("isEdit", true);
        intent.putExtra("plantID", plantID);
        intent.putExtra("plantName", plantName);
        intent.putExtra("plantDesc", plantDesc);
        intent.putExtra("plantCreate", plantCreateLong);
        intent.putExtra("plantDays", plantDays);
        intent.putExtra("plantShelfID", plantShelfID);
        startActivity(intent);
    }

    /**
     * Move Plant button clicked
     * Move to PlantMove class, bring over information for this plant
     * @see PlantMove
     * @param view
     */
    public void onClickMovePlant(View view){
        Intent intent = new Intent(PlantDetail.this, PlantMove.class);
        intent.putExtra("plantID", plantID);
        intent.putExtra("plantName", plantName);
        intent.putExtra("plantDesc", plantDesc);
        intent.putExtra("plantCreate", plantCreateLong);
        intent.putExtra("plantDays", plantDays);
        intent.putExtra("plantShelfID", plantShelfID);
        startActivity(intent);
    }

    /**
     * Back button clicked
     * Return to HomeScreen
     * @see HomeScreen
     * @param view
     */
    public void onClickBack(View view){
        Intent intent = new Intent(PlantDetail.this, HomeScreen.class);
        startActivity(intent);
    }

    /**
     * Delete Plant button clicked
     * Prompt with a Yes/Cancel dialog box to confirm plant deletion
     * On 'YES', delete the current plant from the database, return to ShelfList
     * @see ShelfList
     * @param view
     */
    public void onClickDeletePlant(View view){
        android.app.AlertDialog.Builder plantAlert = new android.app.AlertDialog.Builder(this);
        plantAlert.setTitle("Delete?");
        plantAlert.setMessage("Delete this plant?");
        plantAlert.setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Do nothing
            }
        });
        plantAlert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                repository.deletePlant(plantID);

                //Return back to ShelfList
                Intent intent = new Intent(PlantDetail.this, ShelfList.class);
                startActivity(intent);
            }
        });
        AlertDialog alertDialog = plantAlert.create();
        alertDialog.show();
    }
}