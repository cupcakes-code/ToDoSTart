package com.example.todor.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Assessment {

    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo
    public String title;
    @ColumnInfo
    public String endDate;
    //@ColumnInfo
    //public String status;
    @ColumnInfo
    public int courseId;

}
