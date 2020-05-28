package com.chris.chriswgutermtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chris.chriswgutermtracker.ViewModel.NoteDetailViewModel;
import com.chris.chriswgutermtracker.databinding.ActivityNoteDetailBinding;
import com.chris.chriswgutermtracker.databinding.ActivityNotesBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;

import static com.chris.chriswgutermtracker.utility.Constants.COURSE_ID_KEY;
import static com.chris.chriswgutermtracker.utility.Constants.NOTE_ID_KEY;

public class NoteDetailActivity extends AppCompatActivity {
    private int noteId, courseIdFK;
    private EditText noteTextField;
    private TextView noteIdField;
    private ActivityNoteDetailBinding binding;
    private FloatingActionButton fabDelete, fabSave;
    private NoteDetailViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoteDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        noteTextField = binding.textNoteTextField;
        noteIdField = binding.textNoteDetailTitleField;
        fabDelete = binding.fabNoteDelete;
        fabSave = binding.fabNoteSave;

        fabDelete.setOnClickListener(v -> {
            deleteNote();
            finish();

        });

        fabSave.setOnClickListener(v -> {
            saveNote();
            finish();
        });
        initViewModel();

    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(NoteDetailViewModel.class);
        viewModel.getNote().observe(this, note -> {
            if(note!=null){
                noteTextField.setText(note.getNoteText());
                noteIdField.setText(String.valueOf(note.getNoteId()));
            }
        });
        Bundle extras = getIntent().getExtras();
        if(extras == null){
            System.out.println("should never occur");
        } else {
            noteId = extras.getInt(NOTE_ID_KEY, 0);
            courseIdFK = extras.getInt(COURSE_ID_KEY);
            if(noteId == 0){
                //new note
                noteIdField.setText("New Note");
            }
        }
        viewModel.loadNote(noteId);
    }

    private void saveNote() {

            viewModel.save(noteTextField.getText().toString(),
                    courseIdFK);
            Toast toast = Toast.makeText(getApplicationContext(),"Note Saved", Toast.LENGTH_SHORT);
            toast.show();

    }

    private void deleteNote() {
        if(noteId !=0){
            viewModel.delete();
            Toast toast = Toast.makeText(getApplicationContext(), "Note Deleted", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.note_detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.note_share:
                // implement
                //to do
                return true;
            default:
            return super.onOptionsItemSelected(item);
        }
    }
}
