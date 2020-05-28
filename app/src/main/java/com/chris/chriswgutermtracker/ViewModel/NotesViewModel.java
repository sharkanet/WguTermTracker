package com.chris.chriswgutermtracker.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.chris.chriswgutermtracker.database.Note;
import com.chris.chriswgutermtracker.database.WGUAppRepository;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class NotesViewModel extends AndroidViewModel {
    private WGUAppRepository repo;
    private LiveData<List<Note>> notes;
  //  private Executor executor = Executors.newSingleThreadExecutor();

    public NotesViewModel(@NonNull Application application) {
        super(application);
        repo = WGUAppRepository.getInstance(application.getApplicationContext());
    }

    public void loadNotes(int courseId){
        repo.setCourseNotes(courseId);
        notes = repo.getCourseNotes();
    }
    public LiveData<List<Note>> getNotes(){
        return notes;
    }
}
