package com.chris.chriswgutermtracker.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.chris.chriswgutermtracker.database.Assessment;
import com.chris.chriswgutermtracker.database.WGUAppRepository;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AssessmentsViewModel extends AndroidViewModel {
    private WGUAppRepository repo;
    private LiveData<List<Assessment>> assessments;
  //  private Executor executor = Executors.newSingleThreadExecutor();

    public AssessmentsViewModel(@NonNull Application application) {
        super(application);
        repo = WGUAppRepository.getInstance(application.getApplicationContext());
    }
    public void loadAssessments(int courseId){
        repo.setCourseAssessments(courseId);
        assessments=repo.getCourseAssessments();
    }
    public LiveData<List<Assessment>> getAssessments(){
        return assessments;
    }
}
