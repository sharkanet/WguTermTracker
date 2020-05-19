package com.chris.chriswgutermtracker.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chris.chriswgutermtracker.R;
import com.chris.chriswgutermtracker.database.Course;
import com.chris.chriswgutermtracker.databinding.CourseListItemBinding;

import java.util.List;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.ViewHolder> {
    private final List<Course> courses;
    private final Context context;
    private OnCourseClickListener listener;


    public CoursesAdapter(List<Course> courses, Context context){
        this.context=context;
        this.courses = courses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.course_list_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Course course = courses.get(position);
        holder.textCourseTitle.setText(course.getCourseTitle());
        holder.textCourseStart.setText(course.getCourseStart().toString());
        holder.textCourseEnd.setText(course.getCourseEnd().toString());
        holder.textCourseStatus.setText(course.getCourseStatus());

    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
        TextView textCourseTitle;
        TextView textCourseStart;
        TextView textCourseEnd;
        TextView textCourseStatus;
        CourseListItemBinding binding;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CourseListItemBinding.bind(itemView);
            textCourseEnd = binding.textCourseEnd;
            textCourseStart = binding.textCourseStart;
            textCourseStatus = binding.textCourseStatus;
            textCourseTitle= binding.textCourseTitle;
            itemView.setOnClickListener(v->{
                int position = getAdapterPosition();
                if(listener!= null && position!= RecyclerView.NO_POSITION) {
                    listener.onCourseClick(courses.get(position));
                }
            });
        }
    }

    public  interface OnCourseClickListener{
        void onCourseClick(Course course);
    }
    public void setOnCourseClickListener(OnCourseClickListener listener){
        this.listener = listener;
    }
}
