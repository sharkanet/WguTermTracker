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


    public TermDetailViewModel(@NonNull Application application) {
        super(application);
        repo = WGUAppRepository.getInstance(application.getApplicationContext());
    }

    public MutableLiveData<Term> getTerm(){
        return term;
    }
    public void loadTerm(int id){
        executor.execute(()->{
//            repo.setTermById(id);
//            Term termEditing = repo.getSelectedTerm();
            Term termEditing = repo.getTermById(id);
            Integer coursesInTerm = repo.getCourseCountWithFK(id);
            term.postValue(termEditing);
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

    public void delete() {
        repo.deleteTerm(term.getValue());
    }
    public boolean noCourses(){
        Integer count = courses.getValue();
       if(count.intValue() == 0)
          return true;
        else
          return false;
    }
}
