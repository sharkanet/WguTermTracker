package com.chris.chriswgutermtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.chris.chriswgutermtracker.ViewModel.AssessmentDetailViewModel;
import com.chris.chriswgutermtracker.databinding.ActivityAssessmentDetailBinding;
import com.chris.chriswgutermtracker.utility.NotificationAlerts;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.chris.chriswgutermtracker.utility.Constants.ASSESSMENT_ID_KEY;
import static com.chris.chriswgutermtracker.utility.Constants.COURSE_ID_KEY;
import static com.chris.chriswgutermtracker.utility.Constants.NOTIFICATION_TEXT;
import static com.chris.chriswgutermtracker.utility.Constants.NOTIFICATION_TITLE;

public class AssessmentDetailActivity extends AppCompatActivity {
    private int assessmentId, courseIdFK;
    private EditText assessmentTitle, assessmentInfo;
    private TextView assessmentDate, assessmentTime, assessmentAlertDate, assessmentAlertTime;
    private Calendar calendarAssessment, calendarAlert;
    private DatePickerDialog.OnDateSetListener dateAssessmentListener, dateAlertListener;
    private TimePickerDialog.OnTimeSetListener timeAssessmentListener, timeAlertListener;
    private String dateFormat = "MM/dd/yyyy";
    private SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
    private String timeFormat = "hh:mm a";
    private SimpleDateFormat stf = new SimpleDateFormat(timeFormat, Locale.US);
    private String dateTimeFormat = "MM/dd/yyyy hh:mm a";
    private SimpleDateFormat dtf = new SimpleDateFormat(dateTimeFormat, Locale.US);
    private Button buttonAlert;
    private FloatingActionButton fabSave, fabDelete;
  //  private boolean newAssessmentBool;
    private ActivityAssessmentDetailBinding binding;
    private AssessmentDetailViewModel viewModel;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAssessmentDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        assessmentTime = binding.textAssessmentDetailStartTimeField;
        assessmentTitle = binding.textAssessmentDetailTitleField;
        assessmentInfo =binding.textAssessmentStatusField;
        assessmentDate = binding.textAssessmentDetailStartField;
        assessmentAlertDate=binding.textAssessmentDetailEndField;
        assessmentAlertTime=binding.textAssessmentDetailEndTimeField;
        fabSave=binding.fabAssessmentSave;
        fabDelete=binding.fabAssessmentDelete;
        buttonAlert=binding.buttonAssessmentAlert;
        calendarAssessment = Calendar.getInstance();
        calendarAlert = Calendar.getInstance();

        dateAssessmentListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendarAssessment.set(Calendar.YEAR,year);
                calendarAssessment.set(Calendar.MONTH, month);
                calendarAssessment.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                setAssessmentDate();
            }
        };
        dateAlertListener = (view, year, month, dayOfMonth) -> {
            calendarAlert.set(Calendar.YEAR,year);
            calendarAlert.set(Calendar.MONTH, month);
            calendarAlert.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setAlertDate();
        };

        timeAssessmentListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendarAssessment.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendarAssessment.set(Calendar.MINUTE, minute);
                setAssessmentTime();
            }
        };
        timeAlertListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendarAlert.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendarAlert.set(Calendar.MINUTE, minute);
                setAlertTime();
            }
        };

        assessmentDate.setOnClickListener(v -> {
            new DatePickerDialog(AssessmentDetailActivity.this, dateAssessmentListener, calendarAssessment.get(Calendar.YEAR),
                    calendarAssessment.get(Calendar.MONTH), calendarAssessment.get(Calendar.DAY_OF_MONTH)).show();
        });
        assessmentTime.setOnClickListener(v -> {
            new TimePickerDialog(AssessmentDetailActivity.this, timeAssessmentListener, calendarAssessment.get(Calendar.HOUR_OF_DAY),
                    calendarAssessment.get(Calendar.MINUTE), false).show();
        });
        assessmentAlertDate.setOnClickListener(v -> {
            new DatePickerDialog(AssessmentDetailActivity.this, dateAlertListener, calendarAlert.get(Calendar.YEAR),
                    calendarAlert.get(Calendar.MONTH), calendarAlert.get(Calendar.DAY_OF_MONTH)).show();
        });
        assessmentAlertTime.setOnClickListener(v -> {
            new TimePickerDialog(AssessmentDetailActivity.this, timeAlertListener, calendarAlert.get(Calendar.HOUR_OF_DAY),
                    calendarAlert.get(Calendar.MINUTE), false).show();
        });

        fabDelete.setOnClickListener(v -> {
            deleteAssessment();
            finish();

        });

        fabSave.setOnClickListener(v -> {
            saveAssessment();
            finish();
        });

        buttonAlert.setOnClickListener(v -> {
//implement alert
            setAssessmentAlert();
        });

        initViewModel();





    }



    private void setAssessmentDate(){
            assessmentDate.setText(sdf.format(calendarAssessment.getTime()));
    }
    private void setAlertDate(){
        assessmentAlertDate.setText(sdf.format(calendarAlert.getTime()));
    }
    private void setAssessmentTime(){
        assessmentTime.setText(stf.format(calendarAssessment.getTime()));
    }
    private  void setAlertTime(){
        assessmentAlertTime.setText(stf.format(calendarAlert.getTime()));
    }

    private void initViewModel(){
        viewModel = new ViewModelProvider(this).get(AssessmentDetailViewModel.class);
        viewModel.getAssessment().observe(this, assessment -> {
            if(assessment!=null) {
                assessmentTitle.setText(assessment.getAssessmentTitle());
                assessmentDate.setText(sdf.format(assessment.getAssessmentDate()));
                assessmentTime.setText(stf.format(assessment.getAssessmentDate()));
                assessmentAlertDate.setText(sdf.format(assessment.getAssessmentAlertDate()));
                assessmentAlertTime.setText(stf.format(assessment.getAssessmentAlertDate()));
                assessmentInfo.setText(assessment.getAssessmentInfo());
            }
        });
        Bundle extras = getIntent().getExtras();
        if(extras == null){
            System.out.println("should never occur");
        } else {
            assessmentId = extras.getInt(ASSESSMENT_ID_KEY, 0);
            courseIdFK = extras.getInt(COURSE_ID_KEY);
            if(assessmentId == 0){
                //code for new assessment
                setAlertDate();
                setAlertTime();
                setAssessmentDate();
                setAssessmentTime();
            }
            viewModel.loadAssessment(assessmentId);
        }

    }
    private void saveAssessment(){
        try{
            viewModel.save(assessmentTitle.getText().toString(),
                    dtf.parse(assessmentDate.getText().toString() +" " + assessmentTime.getText().toString()),
                    assessmentInfo.getText().toString(),
                    assessmentTitle.getText().toString() +" Alert",
                    dtf.parse(assessmentAlertDate.getText().toString().concat(" ").concat(assessmentAlertTime.getText().toString())),
                    courseIdFK);
            Toast toast = Toast.makeText(getApplicationContext(),"Assessment Saved", Toast.LENGTH_SHORT);
            toast.show();
        } catch (ParseException e){
            e.printStackTrace();
        }

    }
    private void deleteAssessment(){
        if(assessmentId!=0){
            viewModel.delete();
            Toast toast = Toast.makeText(getApplicationContext(), "Assessment Deleted", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    private void setAssessmentAlert() {
        try{
            String alertTimeString = assessmentAlertDate.getText().toString().concat(" ").concat(assessmentAlertTime.getText().toString());
            String assessmentTimeString = assessmentDate.getText().toString().concat(" ").concat(assessmentTime.getText().toString());
            Date alertTimeDate = dtf.parse(alertTimeString);
            String alertTitle = "Assessment Alert";
            String alertText = assessmentTitle.getText().toString().concat(" at ").concat(assessmentTimeString);
            Intent intent = new Intent(AssessmentDetailActivity.this, NotificationAlerts.class);
            intent.putExtra(NOTIFICATION_TITLE, alertTitle);
            intent.putExtra(NOTIFICATION_TEXT, alertText);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(AssessmentDetailActivity.this, assessmentId*1000+3, intent, PendingIntent.FLAG_ONE_SHOT);
            AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, alertTimeDate.getTime(), pendingIntent);
            Toast.makeText(AssessmentDetailActivity.this, "Alarm set for "+ alertTimeString + System.lineSeparator() + alertTitle + System.lineSeparator()+ alertText, Toast.LENGTH_LONG).show();
       //   System.out.println("Alarm set: " + alertTitle +"\n" +alertText);

        } catch (ParseException e){
            e.printStackTrace();
        }
    }

}
