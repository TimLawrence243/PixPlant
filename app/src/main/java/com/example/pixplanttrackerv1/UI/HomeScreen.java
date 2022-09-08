package com.example.pixplanttrackerv1.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.pixplanttrackerv1.Converter.DateConverter;
import com.example.pixplanttrackerv1.Database.Repository;
import com.example.pixplanttrackerv1.Entity.Plant;
import com.example.pixplanttrackerv1.R;

import java.util.ArrayList;
import java.util.List;

/**
 * HomeScreen
 * The Homescreen is the main screen of the app.  It shows which plants need to be watered today
 * It also has buttons to see which plants need to be watered tomorrow and yesterday
 * The homescreen has a button to move to ShelfList
 * @see ShelfList
 * The homescreen has a button to move to the Settings screen
 * @see Settings
 * Clicking one of the plants in need of watering will take the user to the PlantDetail screen for that plant
 * @see PlantDetail
 */
public class HomeScreen extends AppCompatActivity {
    List<Plant> plantsToWater = new ArrayList<>();
    public static int numPlantsToWater;
    List<Plant> plantsToWaterYesterday = new ArrayList<>();
    List<Plant> plantsToWaterTomorrow = new ArrayList<>();
    RecyclerView recyclerView;
    Button btnYesterday;
    Button btnTomorrow;

    /**
     * onCreate is run when the activity launches.
     * For the onCreate of HomeScreen, we do a few things:
     * Set up three lists, calculating which plants need to be watered yesterday, today, and tomorrow
     * Set up the RecyclerView (which defaults to plants needing watering today)
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.todayRecycler);
        btnYesterday = findViewById(R.id.btnYesterday);
        btnTomorrow = findViewById(R.id.btnTomorrow);
        Repository repository = new Repository(getApplication());

        /*
        This is where we determine what plants need to be watered today
        We get a list of ALL plants, then create a blank list of plantsToWater
        Run a for each loop - For each plant in allPlants:
        We get the Long date from when it was created.
        That date is divided by 86,400,000.  That's how many milliseconds are in a day.
        Then we take the same Long, but for today, also divided by 86,400,000, and subtract the create day from today
        This gives us how many days it's been since the plant was created.
        Then we check the modulus (%) of daysToWater.
        i.e., if it's been 5 days since we created the plant, and the daysToWater is 5, modulus will be zero
        if it's been 10 days since we created the plant, and the daysToWater is 3, then modulus will be 1
        If modulus for that plant is zero, we add it to the plantsToWater list, which is then displayed in the RecyclerView
         */
        List<Plant> allPlants = repository.getAllPlants();

        Long createDate;
        Long createDayEpoch;
        int daysToWater;

        //NOTE TO SELF:
        //The '- 3600000 part of this and the section using the same formula on PlantDetail are to account for adjusting to MDT (Mountain DAYLIGHT time)
        //Very basic, would need more work to appropriately adjust for time zones!
        Long todayEpoch = ((System.currentTimeMillis() - 3600000) / 86400000);

        for(Plant p : allPlants){
            createDate = p.getPlantCreateLong();
            createDayEpoch = (createDate / 86400000);
            daysToWater = p.getPlantDaysToWater();
            //EDGE CASE - When a plant needs to be watered every day (daysToWater = 1), add to all three lists
            if(daysToWater == 1){
                plantsToWater.add(p);
                plantsToWaterTomorrow.add(p);
                plantsToWaterYesterday.add(p);
            } else {

                //If it needs to be watered today, add to plantsToWater
                if (DateConverter.needsWaterToday(todayEpoch, createDayEpoch, daysToWater) == true) {
                    plantsToWater.add(p);
                }
                //If this equation == 1, it needs to be watered tomorrow
                if (DateConverter.needsWaterTomorrow(todayEpoch, createDayEpoch, daysToWater) == true) {
                    plantsToWaterTomorrow.add(p);
                }
                //If this equation == daysToWater - 1, then it needed to be watered yesterday
                if (DateConverter.needsWaterYesterday(todayEpoch, createDayEpoch, daysToWater) == true) {
                    plantsToWaterYesterday.add(p);
                }
            }

        }
        //Set public int of number of plants to water today, accessible for other parts of the program (notifications)
        numPlantsToWater = plantsToWater.size();

        final PlantAdapter plantAdapter = new PlantAdapter(this);
        recyclerView.setAdapter(plantAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        plantAdapter.setPlants(plantsToWater);



    }

        public boolean onCreateOptionsMenu(Menu menu){
            //Inflate menu.  Add items to action bar if present
            getMenuInflater().inflate(R.menu.menu_termlist, menu);
            return true;
        }

        public boolean onOptionsItemSelected(MenuItem item){
            switch(item.getItemId()) {
                case android.R.id.home:
                    this.finish();
                    return true;
            }
                return super.onOptionsItemSelected(item);
            }


    /**
     * Moves to ShelfList screen
     * @see ShelfList
     * @param view
     */
    public void onClickShelves(View view){
            //Move to ShelfList
            Intent intent = new Intent(HomeScreen.this, ShelfList.class);
            startActivity(intent);
        }

    /**
     * Yesterday button clicked
     * Changes RecyclerView to show plants needing watering yesterday
     * Changes 'Yesterday', 'today', and 'tomorrow' button labeling as needed
     * @param view
     */
    public void onClickYesterday(View view){
        //If the 'Yesterday' button says 'Yesterday', change the text to 'Today' and display plants to water yesterday in RecyclerView
            if (btnYesterday.getText().equals("Yesterday")) {
                btnYesterday.setText("Today");
                btnTomorrow.setText("Tomorrow");

                final PlantAdapter plantAdapter = new PlantAdapter(this);
                recyclerView.setAdapter(plantAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                plantAdapter.setPlants(plantsToWaterYesterday);
            }
        //If the 'Yesterday' button says 'Today', change the text to 'Yesterday' and display plants to water today in RecyclerView
            else if (btnYesterday.getText().equals("Today")) {
                btnYesterday.setText("Yesterday");

                final PlantAdapter plantAdapter = new PlantAdapter(this);
                recyclerView.setAdapter(plantAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                plantAdapter.setPlants(plantsToWater);
            }
        }

    /**
     * Tomorrow button clicked
     * Changes RecyclerView to show plants needing watering tomorrow
     * Changes 'Yesterday', 'today', and 'tomorrow' button labeling as needed
     * @param view
     */
        public void onClickTomorrow(View view){
            //If the 'Tomorrow' button says 'Tomorrow', change the text to 'Today' and display plants to water Tomorrow in RecyclerView
            if (btnTomorrow.getText().equals("Tomorrow")) {
                btnTomorrow.setText("Today");
                btnYesterday.setText("Yesterday");

                final PlantAdapter plantAdapter = new PlantAdapter(this);
                recyclerView.setAdapter(plantAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                plantAdapter.setPlants(plantsToWaterTomorrow);
            }
            //If the 'Tomorrow' button says 'Today', change the text to 'Tomorrow' and display plants to water today in RecyclerView
            else if (btnTomorrow.getText().equals("Today")) {
                btnTomorrow.setText("Tomorrow");

                final PlantAdapter plantAdapter = new PlantAdapter(this);
                recyclerView.setAdapter(plantAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                plantAdapter.setPlants(plantsToWater);
            }
        }


    /**
     * Moves to 'Settings' screen
     * @see Settings
     * @param view
     */
    public void onClickSettings(View view){
            Intent intent = new Intent(HomeScreen.this, Settings.class);
            startActivity(intent);
        }

}