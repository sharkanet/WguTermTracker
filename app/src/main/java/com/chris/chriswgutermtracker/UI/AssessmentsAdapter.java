package com.chris.chriswgutermtracker.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chris.chriswgutermtracker.R;
import com.chris.chriswgutermtracker.database.Assessment;
import com.chris.chriswgutermtracker.databinding.AssessmentListItemBinding;

import java.util.List;

public class AssessmentsAdapter extends RecyclerView.Adapter<AssessmentsAdapter.ViewHolder> {
    private final List<Assessment> assessments;
    private final Context context;
    private OnAssessmentClickListener listener;

    public AssessmentsAdapter(List<Assessment> assessments, Context context){
        this.context=context;
        this.assessments = assessments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.assessment_list_item, parent, false);
        return new AssessmentsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Assessment assessment = assessments.get(position);
        holder.textAssessmentTitle.setText(assessment.getAssessmentTitle());
        holder.textAssessmentStart.setText(assessment.getAssessmentDate().toString());
    }

    @Override
    public int getItemCount() {
        return assessments.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
        TextView textAssessmentTitle;
        TextView textAssessmentStart;
    //    TextView textAssessmentEnd;

        AssessmentListItemBinding binding;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = AssessmentListItemBinding.bind(itemView);
          //  textAssessmentEnd = binding.textAssessmentEnd;
            textAssessmentStart = binding.textAssessmentStart;
            textAssessmentTitle= binding.textAssessmentTitle;
            itemView.setOnClickListener(v->{
                int position = getAdapterPosition();
                if(listener!= null && position!= RecyclerView.NO_POSITION) {
                    listener.onAssessmentClick(assessments.get(position));
                }
            });
        }
    }

    public  interface OnAssessmentClickListener{
        void onAssessmentClick(Assessment assessment);
    }
    public void setOnAssessmentClickListener(OnAssessmentClickListener listener){
        this.listener = listener;
    }
}
