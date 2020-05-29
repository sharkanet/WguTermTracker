package com.chris.chriswgutermtracker.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.chris.chriswgutermtracker.database.Term;
import com.chris.chriswgutermtracker.database.WGUAppRepository;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    private LiveData<List<Term>> terms;
    private WGUAppRepository repo;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        repo = WGUAppRepository.getInstance(application.getApplicationContext());
        terms = repo.getMTerms();
    }

    public LiveData<List<Term>> getTerms() {
        return terms;
    }
}
