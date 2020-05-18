package com.chris.chriswgutermtracker.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.chris.chriswgutermtracker.R;
import com.chris.chriswgutermtracker.database.Term;
import com.chris.chriswgutermtracker.databinding.TermListItemBinding;

import java.util.List;

public class TermsAdapter extends RecyclerView.Adapter<TermsAdapter.ViewHolder> {
    private final List<Term> terms;
    private final Context context;
    private OnTermClickListener listener;

    public TermsAdapter(List<Term> terms, Context context){
        this.terms = terms;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.term_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TermsAdapter.ViewHolder holder, int position) {
        final Term term = terms.get(position);
        holder.textTermTitle.setText(term.getTermTitle());
        holder.textTermStart.setText(term.getTermStart().toString());
        holder.textTermEnd.setText(term.getTermEnd().toString());

    }

    @Override
    public int getItemCount() {
          return terms.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTermTitle;
        TextView textTermStart;
        TextView textTermEnd;

        TermListItemBinding binding;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = TermListItemBinding.bind(itemView);
            textTermEnd = binding.textTermEnd;
            textTermStart = binding.textTermStart;
            textTermTitle = binding.textTermTitle;
//            textTermTitle = itemView.findViewById(R.id.text_term_title);
//            textTermEnd = itemView.findViewById(R.id.text_term_end);
//            textTermStart = itemView.findViewById(R.id.text_term_start);
            itemView.setOnClickListener(v->{
                int position = getAdapterPosition();
                if(listener!= null && position!= RecyclerView.NO_POSITION) {
                    listener.onTermClick(terms.get(position));
                }
            });
        }
    }

    public interface OnTermClickListener{
        void onTermClick(Term term);
    }
    public void setOnTermClickListener(OnTermClickListener listener){
        this.listener = listener;
    }

}
