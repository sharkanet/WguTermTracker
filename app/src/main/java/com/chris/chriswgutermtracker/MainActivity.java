package com.chris.chriswgutermtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.TextView;

import com.chris.chriswgutermtracker.ViewModel.MainActivityViewModel;
import com.chris.chriswgutermtracker.database.Term;
import com.chris.chriswgutermtracker.database.WGUAppRepository;
import com.chris.chriswgutermtracker.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.chris.chriswgutermtracker.utility.NotificationAlerts.createNotificationChannel;

public class MainActivity extends AppCompatActivity {
    private Button btnToTerms;
    private Button btnResetToSample;
    private ActivityMainBinding binding;
    private WGUAppRepository repo;
    private MainActivityViewModel viewModel;
    private TextView termsRemaining, termsCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createNotificationChannel(this);
        repo = WGUAppRepository.getInstance(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        btnToTerms = binding.btnToTerms;
        btnResetToSample = binding.btnPopulate;
        termsCompleted = binding.termsCompletedField;
        termsRemaining = binding.termsRemainingField;
//        btnToTerms = findViewById(R.id.btn_to_terms);
//        btnPopulate = findViewById(R.id.btn_populate);

        btnToTerms.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, TermsActivity.class);
            startActivity(intent);
        });
        btnResetToSample.setOnClickListener(v-> {
            repo.clearDBAndAddSampleData();
        });

       // setContentView(R.layout.activity_main);
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        final Observer<List<Term>> termsObserver = new Observer<List<Term>>() {
            @Override
            public void onChanged(List<Term> terms) {
                System.out.println(terms.size());
                List<Term> completedTerms = new ArrayList<>();
                List<Term> remainingTerms = new ArrayList<>();
                terms.forEach(term -> {
                    if (term.getTermEnd().compareTo(new Date()) > 0) {
                        remainingTerms.add(term);
                    } else {
                        completedTerms.add(term);
                    }
                });
                termsCompleted.setText(String.valueOf(completedTerms.size()));
                termsRemaining.setText(String.valueOf(remainingTerms.size()));

            }
        };
        viewModel.getTerms().observe(this, termsObserver);
    }
}
