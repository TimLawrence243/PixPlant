package com.example.pixplanttrackerv1.DAO;


import com.example.pixplanttrackerv1.Entity.Shelf;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ShelfDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Shelf shelf);

    @Update
    void update(Shelf shelf);

    @Delete
    void delete(Shelf shelf);

    @Query("SELECT * FROM shelves ORDER BY shelfID ASC")
    List<Shelf> getAllShelves();

    @Query("DELETE FROM shelves WHERE shelfID = :shelfID")
    void deleteShelf(int shelfID);

    @Query("SELECT shelfName FROM shelves WHERE shelfID = :shelfID")
    String getShelfNameFromID(int shelfID);

}
