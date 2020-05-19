package com.chris.chriswgutermtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.chris.chriswgutermtracker.ViewModel.CourseDetailViewModel;
import com.chris.chriswgutermtracker.databinding.ActivityCourseDetailBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static com.chris.chriswgutermtracker.utility.Constants.COURSE_ID_KEY;
import static com.chris.chriswgutermtracker.utility.Constants.TERM_ID_KEY;

public class CourseDetailActivity extends AppCompatActivity {
    private int termIdFK, courseId;
    private TextView courseStart, courseEnd;
    private EditText courseTitle, courseStatus, mentorName, mentorPhone, mentorEmail;
    private Button assessmentButton, noteButton;
    private FloatingActionButton fabSave, fabDelete;
    private Calendar calendarStart, calendarEnd;
    private DatePickerDialog.OnDateSetListener startListener, endListener;
    private String myFormat = "MM/dd/yyyy";
    private SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
    private ActivityCourseDetailBinding binding;
    private CourseDetailViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_course_detail);
        binding = ActivityCourseDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        courseEnd = binding.textCourseDetailEndField;
        courseStart = binding.textCourseDetailStartField;
        courseTitle = binding.textCourseDetailTitleField;
        courseStatus= binding.textCourseStatusField;
        mentorEmail = binding.textCourseMentorEmailField;
        mentorName = binding.textCourseMentorNameField;
        mentorPhone = binding.textCourseMentorPhoneField;
        assessmentButton = binding.buttonToAssessments;
        noteButton = binding.buttonToNotes;
        fabDelete = binding.fabCourseDelete;
        fabSave = binding.fabCourseSave;
        calendarEnd = Calendar.getInstance();
        calendarStart =Calendar.getInstance();

        startListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendarStart.set(Calendar.YEAR,year);
                calendarStart.set(Calendar.MONTH, month);
                calendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                courseStart.setText(sdf.format(calendarStart.getTime()));
            }
        };
        endListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendarEnd.set(Calendar.YEAR,year);
                calendarEnd.set(Calendar.MONTH, month);
                calendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                courseEnd.setText(sdf.format(calendarStart.getTime()));
            }
        };
        courseStart.setOnClickListener(v -> {
            new DatePickerDialog(CourseDetailActivity.this, startListener, calendarStart.get(Calendar.YEAR),
                    calendarStart.get(Calendar.MONTH), calendarStart.get(Calendar.DAY_OF_MONTH)).show();
        });
        courseEnd.setOnClickListener(v -> {
            new DatePickerDialog(CourseDetailActivity.this, endListener, calendarEnd.get(Calendar.YEAR),
                    calendarEnd.get(Calendar.MONTH), calendarEnd.get(Calendar.DAY_OF_MONTH)).show();
        });
        fabSave.setOnClickListener(v -> {
            saveCourse();
            finish();
        });
        fabDelete.setOnClickListener(v->{
            deleteCourse();
            finish();
        });
        assessmentButton.setOnClickListener(v -> {
            toAssessments();
        });
        noteButton.setOnClickListener(v -> {
            toNotes();
        });

        initViewModel();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(CourseDetailViewModel.class);
        viewModel.getCourse().observe(this, course -> {
            if(course!=null){
                courseTitle.setText(course.getCourseTitle());
                courseStart.setText(sdf.format(course.getCourseStart()));
                courseEnd.setText(sdf.format(course.getCourseEnd()));
                courseStatus.setText(course.getCourseStatus());
                mentorName.setText(course.getMentorName());
                mentorPhone.setText(course.getMentorPhone());
                mentorEmail.setText(course.getMentorEmail());
            }
        });
        Bundle extras = getIntent().getExtras();
        if(extras == null){
            System.out.println("should never occur");
        }else{
            termIdFK = extras.getInt(TERM_ID_KEY);
            courseId = extras.getInt(COURSE_ID_KEY,0);
            if(courseId == 0){
                System.out.println("new course");
            }
            viewModel.loadCourse(courseId);
        }
    }

    private void toNotes() {
    }

    private void toAssessments() {
    }

    private void deleteCourse() {
        //implement
    }

    private void saveCourse() {
        //implement
    }
}
