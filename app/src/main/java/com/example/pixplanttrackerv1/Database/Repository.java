package com.example.pixplanttrackerv1.Database;

import android.app.Application;
import com.example.pixplanttrackerv1.DAO.PlantDAO;
import com.example.pixplanttrackerv1.DAO.ShelfDAO;
import com.example.pixplanttrackerv1.Entity.Plant;
import com.example.pixplanttrackerv1.Entity.Shelf;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private ShelfDAO mShelfDAO;
    private PlantDAO mPlantDAO;


    private List<Shelf> mAllShelves;
    private List<Plant> mAllPlants;

    private int plantsOnShelf;
    private String shelfName;

    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application){
        PixDatabaseBuilder db = PixDatabaseBuilder.getDatabase(application);
        mShelfDAO = db.shelfDAO();
        mPlantDAO = db.plantDAO();
    }

    /*
    Get alls for each db - Shelf, Plant
     */
    public List<Shelf> getAllShelves(){
        databaseExecutor.execute(()->{
            mAllShelves = mShelfDAO.getAllShelves();
        });

        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAllShelves;
    }

    public List<Plant> getAllPlants(){
        databaseExecutor.execute(()->{
            mAllPlants = mPlantDAO.getAllPlants();
        });

        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAllPlants;
    }

    public List<Plant> getAllPlantsforShelf(int shelfID){
        databaseExecutor.execute(()->{
            mAllPlants = mPlantDAO.getAllPlantsForShelf(shelfID);
        });

        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAllPlants;
    }




    public void insertShelf(Shelf shelf){
        databaseExecutor.execute(()->{
            mShelfDAO.insert(shelf);
        });
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void updateShelf(Shelf shelf){
        databaseExecutor.execute(()->{
            mShelfDAO.update(shelf);
        });
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void deleteShelf(int shelfID){
        databaseExecutor.execute(()->{
            mShelfDAO.deleteShelf(shelfID);
        });
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }


    //Plant
    public void insertPlant(Plant plant){
        databaseExecutor.execute(()->{
            mPlantDAO.insert(plant);
        });
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void updatePlant(Plant plant){
        databaseExecutor.execute(()->{
            mPlantDAO.update(plant);
        });
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void deletePlant(int plantID){
        databaseExecutor.execute(()->{
            mPlantDAO.deletePlant(plantID);
        });
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    //Other methods used
    public int checkPlantsOnShelf(int shelfID){
        databaseExecutor.execute(()->{
                plantsOnShelf = mPlantDAO.checkPlantsOnShelf(shelfID);
        });
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        return plantsOnShelf;
    }

    public void movePlant(int shelfID, int plantID) {
        databaseExecutor.execute(() -> {
            mPlantDAO.movePlant(shelfID, plantID);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getShelfNameFromID(int shelfID){
        databaseExecutor.execute(() ->{
            shelfName = mShelfDAO.getShelfNameFromID(shelfID);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        return shelfName;
    }




}
