package com.example.pixplanttrackerv1.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pixplanttrackerv1.Database.Repository;
import com.example.pixplanttrackerv1.Entity.Shelf;
import com.example.pixplanttrackerv1.R;

/**
 * ShelfAdd
 * UI used to add a new Shelf to the database
 */
public class ShelfAdd extends AppCompatActivity {
    boolean isEdit;
    int shelfID;
    String shelfName;
    String shelfDesc;

    /**
     * onCreate method is run when the activity starts
     * The onCreate for ShelfAdd will set up the activity, but also checks if we're 'editing' the shelf rather than adding a new shelf.
     * If editing, it will pre-fill the text fields with the shelf we're editing.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelf_add);

        //ShelfAdd is used for both Adding new and editing a shelf, determined by boolean 'isEdit'
        isEdit = getIntent().getBooleanExtra("isEdit", false);
        shelfID = getIntent().getIntExtra("shelfID", 0);
        shelfName = getIntent().getStringExtra("shelfName");
        shelfDesc = getIntent().getStringExtra("shelfDesc");

        //If we're editing, not adding, pre-fill the text fields
        if(isEdit == true){
            TextView newShelf = findViewById(R.id.txtNewShelf);
            newShelf.setText("Edit shelf");

            EditText txtName = (EditText) findViewById(R.id.editShelfName);
            EditText txtDesc = (EditText) findViewById(R.id.editShelfDesc);
            txtName.setText(shelfName);
            txtDesc.setText(shelfDesc);
        }

    }

    /**
     * Called when the 'Save' button is clicked on the page
     * Checks if either of the fields was left blank.  If name is blank, give an error as it's required and do not proceed.
     * If Description is blank, which is optional, we'll change the description so it's not 'null'
     * If editing a shelf, we update the shelf we're editing rather than creating a new one.
     * Returns to ShelfList after creation/edit to display the new or edited shelf.
     * @param view
     */
    public void onClickSaveShelf(View view){
        //Get variables from fields
        //Shelf name
        final EditText txtName = (EditText) findViewById(R.id.editShelfName);
        String name = txtName.getText().toString();

        //Shelf Description
        final EditText txtDesc = (EditText) findViewById(R.id.editShelfDesc);
        String desc = txtDesc.getText().toString();
        //If description is left blank (Optional), change description so it's not 'null' for database
        if(desc.isEmpty()){
            desc = "No description provided for this shelf";
        }


        //Check if shelf name is blank
        //Display alert error if blank and do not continue
        if(name.isEmpty()){
            android.app.AlertDialog.Builder blankAlert = new android.app.AlertDialog.Builder(this);
            blankAlert.setTitle("No shelf name provided");
            blankAlert.setMessage("Shelf name is required");
            blankAlert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //Do nothing
                }
            });
            AlertDialog alertDialog = blankAlert.create();
            alertDialog.show();
        }
        //As long as name isn't left empty, we'll create the new shelf
        else {
            //Repository
            Repository repository = new Repository(getApplication());

            //If we're NOT editing a shelf, add new shelf to database
            if(isEdit == false) {
                //Create shelf and add to database
                Shelf shelf = new Shelf(0, name, desc);
                repository.insertShelf(shelf);

                //Return to the Shelves Screen
                Intent intent = new Intent(ShelfAdd.this, ShelfList.class);
                startActivity(intent);
            } else if(isEdit == true){
                //If EDITING the shelf, put in the shelfID and update rather than create new
                Shelf shelf = new Shelf(shelfID, name, desc);
                repository.updateShelf(shelf);

                Intent intent = new Intent(ShelfAdd.this, ShelfList.class);
                startActivity(intent);
            }
        }

    }

    /**
     * Cancel button clicked
     * Moves back to ShelfList, does not create a new shelf
     * @param view
     */
    public void onClickCancel(View view){
        Intent intent = new Intent(ShelfAdd.this, ShelfList.class);
        startActivity(intent);
    }
}