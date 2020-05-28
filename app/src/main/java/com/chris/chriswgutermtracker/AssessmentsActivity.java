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
import com.chris.chriswgutermtracker.UI.CoursesAdapter;
import com.chris.chriswgutermtracker.ViewModel.AssessmentsViewModel;
import com.chris.chriswgutermtracker.ViewModel.CoursesViewModel;
import com.chris.chriswgutermtracker.database.Assessment;
import com.chris.chriswgutermtracker.database.Course;
import com.chris.chriswgutermtracker.databinding.ActivityAssessmentsBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static com.chris.chriswgutermtracker.utility.Constants.ASSESSMENT_ID_KEY;
import static com.chris.chriswgutermtracker.utility.Constants.COURSE_ID_KEY;
import static com.chris.chriswgutermtracker.utility.Constants.TERM_ID_KEY;

public class AssessmentsActivity extends AppCompatActivity {
    private RecyclerView rvAssessments;
    private List<Assessment> assessments = new ArrayList<>();
    private AssessmentsAdapter assessmentsAdapter;
    private AssessmentsViewModel assessmentsViewModel;
    private  int courseIdFK;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAssessmentsBinding binding = ActivityAssessmentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        rvAssessments = binding.recyclerViewAssessments;

        //  Toolbar toolbar = binding.toolbar;
        // setSupportActionBar(toolbar);
        initRecyclerView();
        initViewModel();

        FloatingActionButton fab = binding.fabAssessmentAdd;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AssessmentsActivity.this, AssessmentDetailActivity.class);
                intent.putExtra(COURSE_ID_KEY, courseIdFK);
                startActivity(intent);
            }
        });


    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvAssessments.setLayoutManager(layoutManager);
        rvAssessments.setHasFixedSize(true);
        assessmentsAdapter = new AssessmentsAdapter(assessments, this);
        rvAssessments.setAdapter(assessmentsAdapter);
    }

    private void initViewModel() {
        //grab term id
        final Observer<List<Assessment>> assessmentObserver = newAssessment->{
            assessments.clear();
            assessments.addAll(newAssessment);
            if(assessmentsAdapter == null){
                assessmentsAdapter = new AssessmentsAdapter(assessments, AssessmentsActivity.this);
                rvAssessments.setAdapter(assessmentsAdapter);
            } else{
                assessmentsAdapter.notifyDataSetChanged();
            }
        };
        assessmentsViewModel = new ViewModelProvider(this).get(AssessmentsViewModel.class);

        Bundle extras = getIntent().getExtras();
        if(extras == null){
            System.out.println("something went wrong - should never be called - AssessmentActivityInitViewModel()");
        }else{
            courseIdFK = extras.getInt(COURSE_ID_KEY);
            assessmentsViewModel.loadAssessments(courseIdFK);
        }
        assessmentsViewModel.getAssessments().observe(this, assessmentObserver );

        assessmentsAdapter.setOnAssessmentClickListener(assessment -> {
            Intent intent = new Intent(AssessmentsActivity.this, AssessmentDetailActivity.class);
            intent.putExtra(ASSESSMENT_ID_KEY, assessment.getAssessmentId());
            intent.putExtra(TERM_ID_KEY, assessment.getCourseId_FK());
            startActivity(intent);
        });
    }
}
