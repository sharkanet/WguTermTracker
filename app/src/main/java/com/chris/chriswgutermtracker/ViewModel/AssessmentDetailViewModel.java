package com.chris.chriswgutermtracker.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.chris.chriswgutermtracker.database.Assessment;
import com.chris.chriswgutermtracker.database.WGUAppRepository;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AssessmentDetailViewModel extends AndroidViewModel {
    private MutableLiveData<Assessment> assessment = new MutableLiveData<>();
    private WGUAppRepository repo;
    private Executor executor = Executors.newSingleThreadExecutor();
    private int assessmentId;

    public AssessmentDetailViewModel(@NonNull Application application) {
        super(application);
        repo = WGUAppRepository.getInstance(application.getApplicationContext());
    }

    public MutableLiveData<Assessment> getAssessment (){
        return assessment;
    }

    public void  loadAssessment(int id){
        assessmentId = id;
        executor.execute(()->{
            Assessment assessmentEditing = repo.getAssessmentById(id);
            assessment.postValue(assessmentEditing);
        });

    }


    public void delete() {
        repo.deleteAssessment(assessment.getValue());
    }
    public void save(String title, Date assessmentDate, String info, String alertTitle, Date assessmentAlertDate, int courseIdFK){
        Assessment newAssessment = assessment.getValue();
        if(newAssessment == null){
            newAssessment = new Assessment(title.trim(), assessmentDate, info.trim(), alertTitle.trim(), assessmentAlertDate, courseIdFK);
            repo.insertAssessment(newAssessment);
        } else {
            newAssessment.setAssessmentTitle(title.trim());
            newAssessment.setAssessmentDate(assessmentDate);
            newAssessment.setAssessmentInfo(info.trim());
            newAssessment.setAssessmentAlertTitle(alertTitle.trim());
            newAssessment.setAssessmentAlertDate(assessmentAlertDate);
            repo.updateAssessment(newAssessment);

        }
    }
}
