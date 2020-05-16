package com.chris.chriswgutermtracker.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "notes",
    foreignKeys = @ForeignKey(entity = Course.class,
            parentColumns = "courseId",
            childColumns = "courseId_FK",
            onDelete = CASCADE
    )
)
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int noteId;

    private String noteText;
    private int courseId_FK;

    public Note(int noteId, String noteText, int courseId_FK) {
        this.noteId = noteId;
        this.noteText = noteText;
        this.courseId_FK = courseId_FK;
    }

    public Note(String noteText, int courseId_FK) {
        this.noteText = noteText;
        this.courseId_FK = courseId_FK;
    }

    public Note() {
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public int getCourseId_FK() {
        return courseId_FK;
    }

    public void setCourseId_FK(int courseId_FK) {
        this.courseId_FK = courseId_FK;
    }

    @Override
    public String toString() {
        return "Note{" +
                "noteId=" + noteId +
                ", noteText='" + noteText + '\'' +
                ", courseId_FK=" + courseId_FK +
                '}';
    }
}
