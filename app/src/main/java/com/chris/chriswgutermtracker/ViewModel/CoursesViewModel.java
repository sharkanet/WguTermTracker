package com.chris.chriswgutermtracker.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.chris.chriswgutermtracker.database.Course;
import com.chris.chriswgutermtracker.database.WGUAppRepository;

import java.util.List;

public class CoursesViewModel extends AndroidViewModel {
    private WGUAppRepository repo;
    private LiveData<List<Course>> courses;
 //   private int termIdFK;

    public CoursesViewModel(@NonNull Application application) {
        super(application);
        repo = WGUAppRepository.getInstance(application.getApplicationContext());
    }
    public void loadCourses(int termId){
        repo.setTermCourses(termId);
        courses = repo.getTermCourses();
    }
    public LiveData<List<Course>> getCourses (){
        return courses;
    }
//    public int getTermIdFK(){
////        return termIdFK;
////    }
}
