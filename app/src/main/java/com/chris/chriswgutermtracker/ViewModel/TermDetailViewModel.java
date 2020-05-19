package com.chris.chriswgutermtracker.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.chris.chriswgutermtracker.database.Term;
import com.chris.chriswgutermtracker.database.WGUAppRepository;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TermDetailViewModel extends AndroidViewModel {
    private MutableLiveData<Term> term = new MutableLiveData<>();
    private MutableLiveData<Integer> courses = new MutableLiveData<>();
    private WGUAppRepository repo;
    private Executor executor = Executors.newSingleThreadExecutor();
    private int termId;


    public TermDetailViewModel(@NonNull Application application) {
        super(application);
        repo = WGUAppRepository.getInstance(application.getApplicationContext());
    }

    public MutableLiveData<Term> getTerm(){
        return term;
    }
    public void loadTerm(int id){
        termId = id;
        executor.execute(()->{
//            repo.setTermById(id);
//            Term termEditing = repo.getSelectedTerm();
            Term termEditing = repo.getTermById(id);
            term.postValue(termEditing);
        });
        executor.execute(()->{
            //   repo.setLastSelectedTermCourseCount(id);
            Integer coursesInTerm = repo.getCourseCountWithFK(id);
            courses.postValue(coursesInTerm);
        });
    }
    public void save(String title, Date start, Date end){
        Term newTerm = term.getValue();
     //   System.out.println(newTerm);
        if(newTerm == null){
            newTerm = new Term(title.trim(),start,end);
            repo.insertTerm(newTerm);
        } else {
            newTerm.setTermTitle(title.trim());
            newTerm.setTermEnd(end);
            newTerm.setTermStart(start);
            repo.updateTerm(newTerm);
        }
        System.out.println(newTerm);

    }

    public boolean delete() {
        repo.deleteTerm(term.getValue());
        return true;

    }
    public void refreshCount(){
        executor.execute(()->{
            //   repo.setLastSelectedTermCourseCount(id);
            Integer coursesInTerm = repo.getCourseCountWithFK(termId);
            courses.postValue(coursesInTerm);
        });
    }
    public boolean noCourses(int id){

        Integer count = courses.getValue();
       if(count == 0)
          return true;
        else
          return false;
    }
}
