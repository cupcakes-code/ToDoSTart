package com.example.todor.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TermDao {

    @Query("SELECT * FROM term")
    List<Term> getAllTerms();

    @Insert
    void insertNewTerm(Term term);

    @Delete
    void deleteTerm(Term term);
}
