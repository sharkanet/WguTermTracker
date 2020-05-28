package com.chris.chriswgutermtracker.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.chris.chriswgutermtracker.database.Note;
import com.chris.chriswgutermtracker.database.WGUAppRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class NoteDetailViewModel extends AndroidViewModel {
    private MutableLiveData<Note> note = new MutableLiveData<>();
    private WGUAppRepository repo;
    private Executor executor = Executors.newSingleThreadExecutor();
    private int noteId;


    public NoteDetailViewModel(@NonNull Application application) {
        super(application);
        repo = WGUAppRepository.getInstance(application.getApplicationContext());
    }

    public MutableLiveData<Note> getNote() {
        return note;
    }

    public void loadNote(int id){
        noteId = id;
        executor.execute(()->{
            Note noteEditing = repo.getNoteById(id);
            note.postValue(noteEditing);
        });
    }

    public void delete(){
        repo.deleteNote(note.getValue());
    }

    public void save(String noteText, int courseIdFK){
        Note newNote =note.getValue();
        if(newNote == null){
            newNote = new Note(noteText.trim(),courseIdFK);
            repo.insertNote(newNote);
        } else{
            newNote.setNoteText(noteText.trim());
            repo.updateNote(newNote);
        }
    }


}
