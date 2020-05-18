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
public interface CourseDao {
    //Insert course
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCourse(Course course);

    //Update course
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateCourse (Course course);

    //Get course by id
    @Query("SELECT * FROM courses WHERE courseId = :id")
    Course getCourseById(int id);

    @Query("SELECT COUNT(courseId) FROM courses WHERE termId_FK = :termId")
    Integer getCourseCountWithFK(int termId);

    //get all courses
    @Query("SELECT * FROM courses ORDER BY courseStart ASC")
    LiveData<List<Course>> getAllCourses();

    //select all matching term id
    @Query("SELECT * FROM courses WHERE termId_FK = :termId")
    LiveData<List<Course>> getCoursesWithFK(int termId);

    //delete course by id
    @Query("DELETE FROM courses WHERE courseId = :id")
    void deleteCourseById(int id);

    //delete a course
    @Delete
    void deleteCourse(Course course);

    //delete all courses matching  term id
    @Query("DELETE FROM courses WHERE termId_FK = :termId")
    void deleteCoursesMatchingFK(int termId);

    //delete all courses
    @Query("DELETE FROM courses")
    void deleteAllCourses();
}
