package com.example.pixplanttrackerv1.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.pixplanttrackerv1.Database.Repository;
import com.example.pixplanttrackerv1.Entity.Shelf;
import com.example.pixplanttrackerv1.R;

/**
 * MainActivity
 * The first screen accessed when launching the program
 * This is the 'login' screen and could be changed to have an actual log in field
 * Currently just an 'Enter' button to run the program
 */
public class MainActivity extends AppCompatActivity {
    public static int numAlert;

    /**
     * onCreate is run when the activity launches
     * Pulls GUI for the 'activity_main' file
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Moves to HomeScreen
     * @see HomeScreen
     * @param view
     */
    public void goToHomeScreen(View view){
        //Move to list of terms
        Intent intent = new Intent(MainActivity.this, HomeScreen.class);
        startActivity(intent);

        Repository repository = new Repository(getApplication());
        //Creates a placeholder first shelf (Can be commented out)
        Shelf testShelf = new Shelf(1, "Top Shelf", "On the top");
        repository.insertShelf(testShelf);

    }


}