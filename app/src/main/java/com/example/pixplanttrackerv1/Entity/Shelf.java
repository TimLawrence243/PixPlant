package com.example.pixplanttrackerv1.Entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "shelves")
public class Shelf {
    @PrimaryKey(autoGenerate = true)
    private int shelfID;
    private String shelfName;
    private String shelfDesc;



    public Shelf(int shelfID, String shelfName, String shelfDesc){
        this.shelfID = shelfID;
        this.shelfName = shelfName;
        this.shelfDesc = shelfDesc;
    }


    public int getShelfID() {
        return shelfID;
    }

    public void setShelfID(int shelfID) {
        this.shelfID = shelfID;
    }

    public String getShelfName() {
        return shelfName;
    }

    public void setShelfName(String shelfName) {
        this.shelfName = shelfName;
    }

    public String getShelfDesc() {
        return shelfDesc;
    }

    public void setShelfDesc(String shelfDesc) {
        this.shelfDesc = shelfDesc;
    }

    @Override
    public String toString() {
        return "Shelf{" +
                "shelfID=" + shelfID +
                ", shelfName='" + shelfName + '\'' +
                ", shelfDesc='" + shelfDesc + '\'' +
                '}';
    }


}
