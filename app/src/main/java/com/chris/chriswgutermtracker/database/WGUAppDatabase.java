package com.chris.chriswgutermtracker.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Note.class, Assessment.class, Course.class, Term.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class WGUAppDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "WGUAppDatabase.db";
    public static volatile WGUAppDatabase instance;
    private static final Object LOCK = new Object();
    public abstract TermDao TermDao();
    public abstract CourseDao CourseDao();
    public abstract AssessmentDao AssessmentDao();
    public abstract NoteDao NoteDao();

    public static WGUAppDatabase getInstance(Context context){
        if(instance == null){
            synchronized (LOCK){
                if(instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(), WGUAppDatabase.class, DATABASE_NAME).build();
                }
            }
        }
        return instance;
    }

}
