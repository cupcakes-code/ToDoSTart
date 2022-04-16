package com.example.todor.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Term {
     @PrimaryKey(autoGenerate = true)
    public int id;
     @ColumnInfo
    public String title;
     @ColumnInfo
    public String startDate;
     @ColumnInfo
    public String endDate;

}
