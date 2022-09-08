package com.example.pixplanttrackerv1.Database;

import android.content.Context;

import com.example.pixplanttrackerv1.Converter.DateConverter;

import com.example.pixplanttrackerv1.DAO.PlantDAO;
import com.example.pixplanttrackerv1.DAO.ShelfDAO;

import com.example.pixplanttrackerv1.Entity.Plant;
import com.example.pixplanttrackerv1.Entity.Shelf;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Shelf.class, Plant.class}, version = 6, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class PixDatabaseBuilder extends RoomDatabase {
    public abstract ShelfDAO shelfDAO();
    public abstract PlantDAO plantDAO();

    private static volatile PixDatabaseBuilder INSTANCE;


    static PixDatabaseBuilder getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (PixDatabaseBuilder.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), PixDatabaseBuilder.class, "mDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
