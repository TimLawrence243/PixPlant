package com.example.pixplanttrackerv1.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pixplanttrackerv1.Converter.DateConverter;
import com.example.pixplanttrackerv1.Database.Repository;
import com.example.pixplanttrackerv1.Entity.Plant;
import com.example.pixplanttrackerv1.R;

import java.util.Date;

/**
 * PlantAdd
 * Activity used to Add a new plant to a shelf
 */
public class PlantAdd extends AppCompatActivity {
    int shelfID;

    TextView addTitle;
    EditText txtName;
    EditText txtDesc;
    EditText txtDays;

    boolean isEdit;
    int editPlantID;
    String editPlantName;
    String editPlantDesc;
    Long editPlantCreate;
    int editPlantDays;
    int editPlantShelfID;

    /**
     * onCreate method is run when the activity starts
     * The onCreate for PlantAdd will set up the activity, but also checks if we're 'editing' the plant rather than adding a new plant.
     * If editing, it will pre-fill the text fields with the plant we're editing.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_add);

        //Set up title TextView and EditTexts
        addTitle = findViewById(R.id.plantAddTitle);
        txtName = (EditText) findViewById(R.id.editPlantName);
        txtDesc = (EditText) findViewById(R.id.editPlantDesc);
        txtDays = (EditText) findViewById(R.id.editPlantDays);

        //PlantAdd is used for both Adding new and editing a plant, determined by boolean 'isEdit'
        isEdit = getIntent().getBooleanExtra("isEdit", false);
        editPlantID = getIntent().getIntExtra("plantID", 0);
        editPlantName = getIntent().getStringExtra("plantName");
        editPlantDesc = getIntent().getStringExtra("plantDesc");
        editPlantCreate = getIntent().getLongExtra("plantCreate", 0);
        editPlantDays = getIntent().getIntExtra("plantDays", 0);
        editPlantShelfID = getIntent().getIntExtra("plantShelfID", 0);

        if(isEdit == true){
            //If we're EDITING a plant:
            //Set title and edittext fields to be filled with data from PlantDetail
            addTitle.setText("Editing " + editPlantName);
            txtName.setText(editPlantName);
            txtDesc.setText(editPlantDesc);
            txtDays.setText("" + editPlantDays);
        } else {
            //We're not editing, creating new plant
            //Get the shelf ID we're adding a plant to
            shelfID = getIntent().getIntExtra("shelfID", 0);

            //Update the 'title' to reflect the shelf name
            String shelfName = getIntent().getStringExtra("shelfName");

            addTitle.setText("New plant for " + shelfName);
        }
    }

    /**
     * Called when the 'Save' button is clicked on the page
     * Checks if any of the fields were left blank.  If name is blank, give an error as it's required and do not proceed.
     * If Description is blank, which is optional, we'll change the description so it's not 'null'
     * If 'days' is not an appropriate number (whole number > 0), give an error and do not proceed
     * If editing a plant, we update the plant we're editing rather than creating a new one.
     * Returns to ShelfList after creation/edit.
     * @param view
     */
    public void onClickSavePlant(View view){
        //Get variables from fields
        String name = txtName.getText().toString();
        String desc = txtDesc.getText().toString();
        //Days to water (Get as String, parse to Int)
        String daysString = txtDays.getText().toString();
        int days = Integer.parseInt(daysString);

        //If description is left blank (Optional), change description so it's not 'null' for database
        if(desc.isEmpty()){
            desc = "No description provided for this plant";
        }

        //Check if name is blank
        //Display alert error if blank and do not continue
        if(name.isEmpty()){
            android.app.AlertDialog.Builder blankAlert = new android.app.AlertDialog.Builder(this);
            blankAlert.setTitle("No plant name provided");
            blankAlert.setMessage("Plant name is required");
            blankAlert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //Do nothing
                }
            });
            AlertDialog alertDialog = blankAlert.create();
            alertDialog.show();
        }
        //Check if days is equal to 0 or negative
        //Display alert error if so and do not continue
        else if(days <= 0){
            android.app.AlertDialog.Builder blankAlert = new android.app.AlertDialog.Builder(this);
            blankAlert.setTitle("Plant Days to water less than one");
            blankAlert.setMessage("Please provide the number of days between waterings.  Must be at least 1");
            blankAlert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //Do nothing
                }
            });
            AlertDialog alertDialog = blankAlert.create();
            alertDialog.show();
        }
        //Continue with creation
        else{
            //Repository
            Repository repository = new Repository(getApplication());

            //If we're EDITING a plant:
            if(isEdit == true){
                //Updates the plant with new name, description, and Days to Water from fields
                //Keeps same plantID, create date, and shelfID
                Plant plant = new Plant(editPlantID, name, desc, editPlantCreate, days, editPlantShelfID);
                repository.updatePlant(plant);
            } else {
            //If we're CREATING NEW plant
                //Date of creation (today)
                //Creates a date, parses and stores as a Long for database storage
                Date now = new java.util.Date();
                Long nowLong = DateConverter.fromDate(now);

                //Create Plant and add to database
                Plant plant = new Plant(0, name, desc, nowLong, days, shelfID);
                repository.insertPlant(plant);
            }

            //Return to shelves screen
            Intent intent = new Intent(PlantAdd.this, ShelfList.class);
            startActivity(intent);
        }
    }
}