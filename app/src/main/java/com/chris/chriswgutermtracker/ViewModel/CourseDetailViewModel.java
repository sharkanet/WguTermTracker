package com.chris.chriswgutermtracker.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.chris.chriswgutermtracker.database.Course;
import com.chris.chriswgutermtracker.database.WGUAppRepository;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CourseDetailViewModel extends AndroidViewModel {
    private MutableLiveData<Course> course = new MutableLiveData<>();
    private WGUAppRepository repo;
    private Executor executor = Executors.newSingleThreadExecutor();

    public CourseDetailViewModel(@NonNull Application application) {
        super(application);
        repo = WGUAppRepository.getInstance(application.getApplicationContext());
    }

    public MutableLiveData<Course> getCourse() {
        return course;
    }
    public void loadCourse(int id){
        executor.execute(()->{
            Course courseEditing = repo.getCourseById(id);
            course.postValue(courseEditing);
        });

    }
    public void save(String title, Date start, Date end, String status, String mentorName, String mentorPhone, String mentorEmail, int termIdFK ){
        Course newCourse = course.getValue();
        if(newCourse == null){
            newCourse = new Course(title.trim(), start,end, status.trim(), mentorName.trim(), mentorPhone.trim(), mentorEmail.trim(),termIdFK);

            repo.insertCourse(newCourse);
        } else {
            newCourse.setCourseTitle(title.trim());
            newCourse.setCourseStart(start);
            newCourse.setCourseEnd(end);
            newCourse.setCourseStatus(status.trim());
            newCourse.setMentorName(mentorName.trim());
            newCourse.setMentorEmail(mentorEmail.trim());
            newCourse.setMentorPhone(mentorPhone.trim());
            repo.updateCourse(newCourse);
        }
    }
}
