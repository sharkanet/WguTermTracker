package com.chris.chriswgutermtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Button;

import com.chris.chriswgutermtracker.database.WGUAppRepository;
import com.chris.chriswgutermtracker.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private Button btnToTerms;
    private Button btnResetToSample;
    private ActivityMainBinding binding;
    private WGUAppRepository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        repo = WGUAppRepository.getInstance(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        btnToTerms = binding.btnToTerms;
        btnResetToSample = binding.btnPopulate;
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

    }
}
