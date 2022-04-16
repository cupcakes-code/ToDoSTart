package com.example.todor.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Course {

    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo
    public String title;
    @ColumnInfo
    public String startDate;
    @ColumnInfo
    public String endDate;
    @ColumnInfo
    public String status;
    @ColumnInfo
    public String instructorName;
    @ColumnInfo
    public String instructorPhone;
    @ColumnInfo
    public String instructorEmail;
    @ColumnInfo
    public int termId;
    @ColumnInfo
    public String note;


}
