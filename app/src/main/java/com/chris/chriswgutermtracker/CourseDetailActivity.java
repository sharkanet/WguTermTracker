package com.chris.chriswgutermtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chris.chriswgutermtracker.ViewModel.CourseDetailViewModel;
import com.chris.chriswgutermtracker.databinding.ActivityCourseDetailBinding;
import com.chris.chriswgutermtracker.utility.NotificationAlerts;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.view.View.GONE;
import static com.chris.chriswgutermtracker.utility.Constants.COURSE_ID_KEY;
import static com.chris.chriswgutermtracker.utility.Constants.NOTIFICATION_TEXT;
import static com.chris.chriswgutermtracker.utility.Constants.NOTIFICATION_TITLE;
import static com.chris.chriswgutermtracker.utility.Constants.TERM_ID_KEY;

public class CourseDetailActivity extends AppCompatActivity {
    public static final String NOTIFICATION = "notification_key";
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
        calendarStart = Calendar.getInstance();

        startListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendarStart.set(Calendar.YEAR,year);
                calendarStart.set(Calendar.MONTH, month);
                calendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                setStartField();
            }
        };
        endListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendarEnd.set(Calendar.YEAR,year);
                calendarEnd.set(Calendar.MONTH, month);
                calendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                setEndField();
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
                setStartField();
                setEndField();
                noteButton.setVisibility(GONE);
                assessmentButton.setVisibility(GONE);
               // System.out.println("new course");
            }
            viewModel.loadCourse(courseId);
        }
    }
    private void setStartField(){
        courseStart.setText(sdf.format(calendarStart.getTime()));
    }
    private void setEndField(){
        courseEnd.setText(sdf.format(calendarEnd.getTime()));
    }

    private void toNotes() {
        if(courseId != 0){
            Intent intent =new Intent( CourseDetailActivity.this, NotesActivity.class);
            intent.putExtra(COURSE_ID_KEY, courseId);
            startActivity(intent);
        }
    }

    private void toAssessments() {
        if(courseId != 0){
            Intent intent =new Intent( CourseDetailActivity.this, AssessmentsActivity.class);
            intent.putExtra(COURSE_ID_KEY, courseId);
            startActivity(intent);
        }
    }

    private void deleteCourse() {
        if (courseId != 0) {
            viewModel.delete();
            Toast toast = Toast.makeText(getApplicationContext(), "Course Deleted", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void saveCourse(){
        try{
            viewModel.save(courseTitle.getText().toString(), sdf.parse(courseStart.getText().toString()), sdf.parse(courseEnd.getText().toString()),
                    courseStatus.getText().toString(), mentorName.getText().toString(), mentorPhone.getText().toString(),mentorEmail.getText().toString(),
                    termIdFK);
            Toast toast = Toast.makeText(getApplicationContext(),"Term Saved", Toast.LENGTH_SHORT);
            toast.show();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.course_detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.alert_start:
                //implement
                setStartAlert();
                return true;
            case R.id.alert_end:
                //implement
                setEndAlert();
                return true;
            default:
               return super.onOptionsItemSelected(item);
        }
    }

    private void setStartAlert(){
        try{
          //  String alertDateString = courseTitle.getText().toString();
         //   String assessmentTimeString = assessmentDate.getText().toString().concat(" ").concat(assessmentTime.getText().toString());
            Date alertDate = sdf.parse(courseStart.getText().toString());
            String alertTitle = "Course Start Alert";
            String alertText = courseTitle.getText().toString().concat(" starts at ").concat(alertDate.toString());
            Intent intent = new Intent(CourseDetailActivity.this, NotificationAlerts.class);
            intent.putExtra(NOTIFICATION_TITLE, alertTitle);
            intent.putExtra(NOTIFICATION_TEXT, alertText);
        //    intent.putExtra(NOTIFICATION, notification);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(CourseDetailActivity.this, courseId*1000+1, intent, PendingIntent.FLAG_ONE_SHOT);
            AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, alertDate.getTime(), pendingIntent);
            Toast.makeText(CourseDetailActivity.this, "Alarm set for "+ alertDate + System.lineSeparator() + alertTitle + System.lineSeparator()+ alertText, Toast.LENGTH_LONG).show();
            //   System.out.println("Alarm set: " + alertTitle +"\n" +alertText);

        } catch (ParseException e){
            e.printStackTrace();
        }
    }

    private void setEndAlert(){
        try{
            //  String alertDateString = courseTitle.getText().toString();
            //   String assessmentTimeString = assessmentDate.getText().toString().concat(" ").concat(assessmentTime.getText().toString());
            Date alertDate = sdf.parse(courseEnd.getText().toString());
            String alertTitle = "Course End Alert";
            String alertText = courseTitle.getText().toString().concat(" ends at ").concat(alertDate.toString());
            Intent intent = new Intent(CourseDetailActivity.this, NotificationAlerts.class);
            intent.putExtra(NOTIFICATION_TITLE, alertTitle);
            intent.putExtra(NOTIFICATION_TEXT, alertText);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(CourseDetailActivity.this, courseId*1000+2, intent, PendingIntent.FLAG_ONE_SHOT);
            AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, alertDate.getTime(), pendingIntent);
            Toast.makeText(CourseDetailActivity.this, "Alarm set for "+ alertDate + System.lineSeparator() + alertTitle + System.lineSeparator()+ alertText, Toast.LENGTH_LONG).show();
            //   System.out.println("Alarm set: " + alertTitle +"\n" +alertText);

        } catch (ParseException e){
            e.printStackTrace();
        }
    }
}
