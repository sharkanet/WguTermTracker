package com.chris.chriswgutermtracker.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chris.chriswgutermtracker.R;
import com.chris.chriswgutermtracker.database.Note;
import com.chris.chriswgutermtracker.databinding.NoteListItemBinding;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    private final List<Note> notes;
    private final Context context;
    private OnNoteClickListener listener;

    public NotesAdapter(List<Note> notes, Context context){
        this.context=context;
        this.notes = notes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.note_list_item, parent, false);
        return new NotesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Note note = notes.get(position);
        holder.textNoteId.setText(String.valueOf(note.getNoteId()));
        holder.textNoteText.setText(note.getNoteText());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
        TextView textNoteId;
        TextView textNoteText;
        NoteListItemBinding binding;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = NoteListItemBinding.bind(itemView);
            textNoteText = binding.textNoteText;
            textNoteId = binding.textNoteTitle;
            itemView.setOnClickListener(v->{
                int position = getAdapterPosition();
                if(listener!= null && position!= RecyclerView.NO_POSITION) {
                    listener.onNoteClick(notes.get(position));
                }
            });
        }
    }

    public  interface OnNoteClickListener{
        void onNoteClick(Note note);
    }
    public void setOnNoteClickListener(OnNoteClickListener listener){
        this.listener = listener;
    }
}
