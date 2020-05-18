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
public interface NoteDao {
    //insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(Note note);

    //update
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateNote(Note note);

    //select by id
    @Query("SELECT * FROM notes WHERE noteId = :id")
    Note getNoteById(int id);

    //select all
    @Query("SELECT * FROM notes")
    LiveData<List<Note>> getAllNotes();

    //select all matching course id
    @Query("SELECT * FROM notes WHERE courseId_FK = :courseId")
    LiveData<List<Note>> getNotesWithFK(int courseId);

    //delete by id
    @Query("DELETE FROM notes WHERE noteId = :id")
    void deleteNoteById(int id);

    //delete
    @Delete
    void deleteNote(Note note);

    //delete all notes matching course id
    @Query("DELETE FROM notes WHERE courseId_FK = :courseId")
    void deleteNotesMatchingFK(int courseId);

    //delete all
    @Query("DELETE FROM notes")
    void deleteAllNotes();
}
