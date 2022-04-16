package com.example.todor.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CourseDao {

    @Query("SELECT * FROM course where termId=:termId")
    List<Course> getAllCourses(int termId);

    @Query("SELECT * FROM course")
    List<Course> getAllCourses();

    @Insert
    void insertNewCourse(Course course);

    @Delete
    void deleteCourse(Course course);

    @Update
    void updateCourse(Course course);
}
