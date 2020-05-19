package com.chris.chriswgutermtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chris.chriswgutermtracker.UI.CoursesAdapter;
import com.chris.chriswgutermtracker.UI.TermsAdapter;
import com.chris.chriswgutermtracker.ViewModel.CoursesViewModel;
import com.chris.chriswgutermtracker.database.Course;
import com.chris.chriswgutermtracker.databinding.ActivityCoursesBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static com.chris.chriswgutermtracker.utility.Constants.COURSE_ID_KEY;
import static com.chris.chriswgutermtracker.utility.Constants.TERM_ID_FK_KEY;
import static com.chris.chriswgutermtracker.utility.Constants.TERM_ID_KEY;

public class CoursesActivity extends AppCompatActivity {

    private RecyclerView rcCourses;
    private List<Course> courses = new ArrayList<>();
    private CoursesViewModel coursesViewModel;
    private CoursesAdapter coursesAdapter;
    private int termIdFK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   setContentView(R.layout.activity_courses);
        ActivityCoursesBinding binding = ActivityCoursesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        rcCourses = binding.recyclerViewCourses;

      //  Toolbar toolbar = binding.toolbar;
       // setSupportActionBar(toolbar);
        initRecyclerView();
        initViewModel();

        FloatingActionButton fab = binding.fabCourseAdd;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CoursesActivity.this, CourseDetailActivity.class);
                intent.putExtra(TERM_ID_KEY, termIdFK);
                startActivity(intent);
            }
        });
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rcCourses.setLayoutManager(layoutManager);
        rcCourses.setHasFixedSize(true);
        coursesAdapter = new CoursesAdapter(courses, this);
        rcCourses.setAdapter(coursesAdapter);
    }

    private void initViewModel() {
        //grab term id
        final Observer<List<Course>> courseObserver = newCourse->{
            courses.clear();
            courses.addAll(newCourse);
            if(coursesAdapter == null){
                coursesAdapter = new CoursesAdapter(courses, CoursesActivity.this);
                rcCourses.setAdapter(coursesAdapter);
            } else{
                coursesAdapter.notifyDataSetChanged();
            }
        };
        coursesViewModel = new ViewModelProvider(this).get(CoursesViewModel.class);

        Bundle extras = getIntent().getExtras();
        if(extras == null){
            System.out.println("something went wrong - should never be called - courseActivityInitViewModel()");
        }else{
            termIdFK = extras.getInt(TERM_ID_KEY);
            coursesViewModel.loadCourses(termIdFK);
        }
        coursesViewModel.getCourses().observe(this, courseObserver );

        coursesAdapter.setOnCourseClickListener(course -> {
            Intent intent = new Intent(CoursesActivity.this, CourseDetailActivity.class);
            intent.putExtra(COURSE_ID_KEY, course.getCourseId());
            intent.putExtra(TERM_ID_KEY, course.getTermId_FK());
            startActivity(intent);
        });
    }
}
