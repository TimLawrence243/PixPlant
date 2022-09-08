package com.example.pixplanttrackerv1.DAO;


import com.example.pixplanttrackerv1.Entity.Plant;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface PlantDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Plant plant);

    @Update
    void update(Plant plant);

    @Delete
    void delete(Plant plant);

    @Query("SELECT * FROM plants ORDER BY plantID ASC")
    List<Plant> getAllPlants();

    @Query("SELECT * FROM plants WHERE shelfID = :shelfID")
    List<Plant> getAllPlantsForShelf(int shelfID);

    @Query("SELECT COUNT(*) FROM plants WHERE shelfID = :shelfID")
    int checkPlantsOnShelf(int shelfID);

    @Query("DELETE FROM plants WHERE plantID = :plantID")
    void deletePlant(int plantID);

    @Query("UPDATE plants SET shelfID = :shelfID WHERE plantID = :plantID")
    void movePlant(int shelfID, int plantID);

}
