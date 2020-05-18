package com.chris.chriswgutermtracker.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TermDao {
    //Insert a term
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTerm(Term term);

    //Update a term
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTerm(Term term);

    //Retrieve a term(by id)
    @Query("SELECT * FROM terms WHERE termId = :id")
    Term getTermById(int id);



    //Retrieve all terms
    @Query("SELECT * FROM terms ORDER BY termStart ASC")
    LiveData<List<Term>> getAllTerms();

    //Delete a term(by id) - check there are no courses
    @Query("DELETE FROM terms WHERE termId = :id")
    void deleteTermById(int id);

    //Delete a term - check there are no courses
    @Delete
    void deleteTerm(Term term);

    //Delete all terms - check there are no courses
    @Query("DELETE FROM terms")
    void deleteAllTerms();
}
