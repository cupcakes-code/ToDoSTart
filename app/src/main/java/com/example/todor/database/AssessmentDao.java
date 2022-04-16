package com.example.todor.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AssessmentDao {

    @Query("SELECT * FROM assessment where courseId=:courseId")
    List<Assessment> getAllAssessments(int courseId);

    @Query("SELECT * FROM assessment")
    List<Assessment> getAllAssessments();

    @Insert
    void insertNewAssessment(Assessment assessment);

    @Delete
    void deleteAssessment(Assessment assessment);

    @Update
    void updateAssessment(Assessment assessment);
}
