package com.chris.chriswgutermtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chris.chriswgutermtracker.UI.AssessmentsAdapter;
import com.chris.chriswgutermtracker.UI.NotesAdapter;
import com.chris.chriswgutermtracker.ViewModel.AssessmentsViewModel;
import com.chris.chriswgutermtracker.ViewModel.NotesViewModel;
import com.chris.chriswgutermtracker.database.Assessment;
import com.chris.chriswgutermtracker.database.Note;
import com.chris.chriswgutermtracker.databinding.ActivityNotesBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static com.chris.chriswgutermtracker.utility.Constants.COURSE_ID_KEY;
import static com.chris.chriswgutermtracker.utility.Constants.NOTE_ID_KEY;
import static com.chris.chriswgutermtracker.utility.Constants.TERM_ID_KEY;

public class NotesActivity extends AppCompatActivity {
    private RecyclerView rcNotes;
    private List<Note> notes = new ArrayList<>();
    private NotesAdapter notesAdapter;
    private NotesViewModel viewModel;
    private int courseIdFK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityNotesBinding binding = ActivityNotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        rcNotes = binding.recyclerViewNote;
        initRecyclerView();
        initViewModel();

        FloatingActionButton fab = binding.fabNotesAdd;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotesActivity.this, NoteDetailActivity.class);
                intent.putExtra(COURSE_ID_KEY, courseIdFK);
                startActivity(intent);
            }
        });
    }

    private void initViewModel() {
        final Observer<List<Note>> noteObserver = newNote->{
            notes.clear();
            notes.addAll(newNote);
            if(notesAdapter == null){
                notesAdapter = new NotesAdapter(notes, NotesActivity.this);
                rcNotes.setAdapter(notesAdapter);
            } else{
                notesAdapter.notifyDataSetChanged();
            }
        };
        viewModel = new ViewModelProvider(this).get(NotesViewModel.class);

        Bundle extras = getIntent().getExtras();
        if(extras == null){
            System.out.println("something went wrong - should never be called - NoteActivityInitViewModel()");
        }else{
            courseIdFK = extras.getInt(COURSE_ID_KEY);
            viewModel.loadNotes(courseIdFK);
        }
        viewModel.getNotes().observe(this, noteObserver );

        notesAdapter.setOnNoteClickListener(note -> {
            Intent intent = new Intent(NotesActivity.this, NoteDetailActivity.class);
            intent.putExtra(NOTE_ID_KEY, note.getNoteId());
            intent.putExtra(TERM_ID_KEY, note.getCourseId_FK());
            startActivity(intent);
        });
    }



    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rcNotes.setLayoutManager(layoutManager);
        rcNotes.setHasFixedSize(true);
        notesAdapter = new NotesAdapter(notes, this);
        rcNotes.setAdapter(notesAdapter);

    }
}
