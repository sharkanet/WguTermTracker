package com.chris.chriswgutermtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chris.chriswgutermtracker.ViewModel.TermDetailViewModel;
import com.chris.chriswgutermtracker.databinding.ActivityTermDetailBinding;
import com.chris.chriswgutermtracker.databinding.ActivityTermsBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static com.chris.chriswgutermtracker.utility.Constants.TERM_ID_KEY;

public class TermDetailActivity extends AppCompatActivity {
    private EditText termTitleField;
    private TextView termStartField;
    private TextView termEndField;
    private TermDetailViewModel viewModel;
    private boolean newTermBool;
    private Calendar calendarStart;
    private Calendar calendarEnd;
    private DatePickerDialog.OnDateSetListener dateStartListener;
    private DatePickerDialog.OnDateSetListener dateEndListener;
    private String myFormat = "MM/dd/yyyy";
    private SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

    private Button buttonToCourses;
    private FloatingActionButton fabSaveTerm;
    private FloatingActionButton fabDeleteTerm;


 //   private int yearStart, monthStart, dayStart, yearEnd, monthEnd, dayEnd;

    private ActivityTermDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_term_detail);
        binding = ActivityTermDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        termEndField = binding.textTermDetailEndField;
        termStartField = binding.textTermDetailStartField;
        termTitleField = binding.textTermDetailTitleField;
        calendarStart = Calendar.getInstance();
        calendarEnd = Calendar.getInstance();
        buttonToCourses = binding.buttonToCourses;
        fabSaveTerm = binding.fabTermSave;
        fabDeleteTerm = binding.fabTermDelete;

        dateStartListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendarStart.set(Calendar.YEAR,year);
                calendarStart.set(Calendar.MONTH, month);
                calendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                setTermStartField();
             }
        };
        dateEndListener = (view, year, month, dayOfMonth) -> {
            calendarEnd.set(Calendar.YEAR,year);
            calendarEnd.set(Calendar.MONTH, month);
            calendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setTermEndField();
        };

        termStartField.setOnClickListener(v->{
            new DatePickerDialog(TermDetailActivity.this, dateStartListener, calendarStart.get(Calendar.YEAR),
                    calendarStart.get(Calendar.MONTH), calendarStart.get(Calendar.DAY_OF_MONTH)).show();
        });
        termEndField.setOnClickListener(v->{
            new DatePickerDialog(TermDetailActivity.this, dateEndListener, calendarEnd.get(Calendar.YEAR),
                    calendarEnd.get(Calendar.MONTH), calendarEnd.get(Calendar.DAY_OF_MONTH)).show();
        });
        fabSaveTerm.setOnClickListener(v -> {
            saveTerm();
        });
        fabDeleteTerm.setOnClickListener(v -> {
            deleteTerm(v);

        });


        initViewModel();
        //setFields();
    }



    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(TermDetailViewModel.class);
        viewModel.getTerm().observe(this, term ->{
            if(term!= null){
                termTitleField.setText(term.getTermTitle());
                termStartField.setText(sdf.format(term.getTermStart()));
                termEndField.setText(sdf.format(term.getTermEnd()));
            }
        });

        Bundle extras = getIntent().getExtras();
        if(extras == null){
            setTitle("Add Term");
            newTermBool = true;
            setTermEndField();
            setTermStartField();
        } else{
            int termId = extras.getInt(TERM_ID_KEY);
            viewModel.loadTerm(termId);
        }

    }

//    private void setFields(){
//        if(!newTermBool){
//
//        }else {
////            year = calendar.get(Calendar.YEAR);
////////            month = calendar.get(Calendar.MONTH)+1;
////////            day = calendar.get(Calendar.DAY_OF_MONTH);
////////            showStart(year, month, day);
////////            showEnd(year,month,day);
//        //    termTitleField.setText("many hamsters");
//
//        }
//    }

//    private void showStart(int year, int month, int day){
//        termStartField.setText(new StringBuilder().append(day).append("/").append(month).append("/").append(year));
//    }
//    private void showEnd(int year, int month, int day){
//      termEndField.setText(new StringBuilder().append(day).append("/").append(month).append("/").append(year));
//    }
    private void setTermStartField(){
        termStartField.setText(sdf.format(calendarStart.getTime()));
    }
    private void setTermEndField(){
        termEndField.setText(sdf.format(calendarEnd.getTime()))
        ;
    }
    private void saveTerm() {
        try{
             viewModel.save(termTitleField.getText().toString(), sdf.parse(termStartField.getText().toString()), sdf.parse(termEndField.getText().toString()));
            Toast toast = Toast.makeText(getApplicationContext(),"Term Saved", Toast.LENGTH_SHORT);
            toast.show();
             finish();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    private void deleteTerm(View v) {
        if(!newTermBool){
            if(noCourses()){
             viewModel.delete();
                Toast toast = Toast.makeText(getApplicationContext(),"Term Deleted", Toast.LENGTH_SHORT);
                toast.show();
                finish();
            } else{
                Toast toast = Toast.makeText(getApplicationContext(),"Term Has Courses", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
    private boolean noCourses(){
        return viewModel.noCourses();
    }
}
