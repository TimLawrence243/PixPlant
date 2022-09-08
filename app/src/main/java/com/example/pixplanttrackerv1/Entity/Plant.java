package com.example.pixplanttrackerv1.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "plants")
public class Plant {
    @PrimaryKey(autoGenerate = true)
    private int plantID;
    private String plantName;
    private String plantDetail;

    private Long plantCreateLong;
    private int plantDaysToWater;
    private int shelfID;




    public Plant(int plantID, String plantName, String plantDetail, Long plantCreateLong, int plantDaysToWater, int shelfID){
        this.plantID = plantID;
        this.plantName = plantName;
        this.plantDetail = plantDetail;
        this.plantCreateLong = plantCreateLong;
        this.plantDaysToWater = plantDaysToWater;
        this.shelfID = shelfID;
    }


    public int getPlantID() {
        return plantID;
    }

    public void setPlantID(int plantID) {
        this.plantID = plantID;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getPlantDetail() {
        return plantDetail;
    }

    public void setPlantDetail(String plantDetail) {
        this.plantDetail = plantDetail;
    }

    public Long getPlantCreateLong() {
        return plantCreateLong;
    }

    public void setPlantCreateLong(Long plantCreateLong) {
        this.plantCreateLong = plantCreateLong;
    }

    public int getPlantDaysToWater() {
        return plantDaysToWater;
    }

    public void setPlantDaysToWater(int plantDaysToWater) {
        this.plantDaysToWater = plantDaysToWater;
    }

    public int getShelfID() {
        return shelfID;
    }

    public void setShelfID(int shelfID) {
        this.shelfID = shelfID;
    }

    @Override
    public String toString() {
        return "Plant{" +
                "plantID=" + plantID +
                ", plantName='" + plantName + '\'' +
                ", plantDetail='" + plantDetail + '\'' +
                ", plantCreateLong=" + plantCreateLong +
                ", plantDaysToWater=" + plantDaysToWater +
                '}';
    }


}
