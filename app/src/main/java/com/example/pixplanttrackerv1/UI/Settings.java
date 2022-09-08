package com.example.pixplanttrackerv1.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.pixplanttrackerv1.R;

import java.sql.Time;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Settings
 * Settings page is mainly for adjusting when notifications are sent each day
 * Once set, one notification will send each day at the specified time
 * Settings also has an 'About' section for other details about the app.
 */
public class Settings extends AppCompatActivity {
    EditText hour;
    EditText minute;

    /**
     * onCreate is run when the activity launches
     * onCreate for settings sets up the two EditText fields on the screen
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        hour = (EditText) findViewById(R.id.settingsHour);
        minute = (EditText) findViewById(R.id.settingsMinutes);
    }

    /**
     * Save button clicked
     * When 'Save' is clicked, we set up the notifications.
     * Takes the hour and minute EditText fields from the screen and puts them into a Calendar object
     * This Calendar object is used to create a daily repeating notification at a specified time.
     * Returns to HomeScreen after clicking the Save button
     * @param view
     */
    public void onClickSaveSettings(View view){
        //Get the hour and minute entered on screen
        String StrHour = hour.getText().toString();
        String StrMinute = minute.getText().toString();
        int intHour = Integer.parseInt(StrHour);
        int intMinute = Integer.parseInt(StrMinute);

        //Set up a Calendar variable with the hour and minute
        Calendar updateTime = Calendar.getInstance();
        updateTime.setTimeZone(TimeZone.getTimeZone("MDT"));
        updateTime.set(Calendar.HOUR_OF_DAY, intHour);
        updateTime.set(Calendar.MINUTE, intMinute);

        int numPlants = HomeScreen.numPlantsToWater;

        Intent intent = new Intent(Settings.this, MyReceiver.class);
        //What the notification reads
        intent.putExtra("key", "Check PixPlant! You have " + numPlants + " plant to water today.");
        PendingIntent sender = PendingIntent.getBroadcast(Settings.this, MainActivity.numAlert++, intent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //Set up REPEATING notification - Every day at the time listed, converted to a Long using getTimeInMillis()
        //AlarmManager.INTERVAL_DAY is what's used to set a notification every day
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, updateTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, sender);

        Intent backToHome = new Intent(Settings.this, HomeScreen.class);
        startActivity(backToHome);
    }

    /**
     * Back to Home button clicked
     * Returns to HomeScreen without making changes to notifications
     * @param view
     */
    public void onClickBack(View view){
        Intent intent = new Intent(Settings.this, HomeScreen.class);
        startActivity(intent);
    }
}