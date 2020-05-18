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
public interface AssessmentDao {
    //insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAssessment(Assessment assessment);

    //update
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateAssessment(Assessment assessment);

    //select by id
    @Query("SELECT * FROM assessments WHERE assessmentId = :id")
    Assessment getAssessmentById(int id);

    //select all
    @Query("SELECT * FROM assessments")
    LiveData<List<Assessment>> getAllAssessments();

    //select all matching course id
    @Query("SELECT * FROM assessments WHERE courseId_FK = :courseId")
    LiveData<List<Assessment>> getAssessmentsWithFK(int courseId);

    //delete by id
    @Query("DELETE FROM assessments WHERE assessmentId = :id")
    void deleteAssessmentById(int id);

    //delete
    @Delete
    void deleteAssessment(Assessment assessment);

    //delete all assessments matching course id
    @Query("DELETE FROM assessments WHERE courseId_FK = :courseId")
    void deleteAssessmentsMatchingFK(int courseId);

    //delete all
    @Query("DELETE FROM assessments")
    void deleteAllAssessments();
}
