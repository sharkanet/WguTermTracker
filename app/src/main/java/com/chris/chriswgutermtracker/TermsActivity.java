package com.chris.chriswgutermtracker;

import android.content.Intent;
import android.os.Bundle;

import com.chris.chriswgutermtracker.UI.TermsAdapter;
import com.chris.chriswgutermtracker.ViewModel.TermsViewModel;
import com.chris.chriswgutermtracker.database.Term;
import com.chris.chriswgutermtracker.databinding.ActivityTermsBinding;
import com.chris.chriswgutermtracker.databinding.TermListItemBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import static com.chris.chriswgutermtracker.utility.Constants.TERM_ID_KEY;

public class TermsActivity extends AppCompatActivity {

    private RecyclerView rcTerms;
    private List<Term> terms = new ArrayList<>();
    private TermsViewModel termsViewModel;
    private TermsAdapter termsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.chris.chriswgutermtracker.databinding.ActivityTermsBinding binding = ActivityTermsBinding.inflate(getLayoutInflater());
    //  setContentView(R.layout.activity_terms);
        setContentView(binding.getRoot());
        rcTerms = binding.recyclerViewTerms;
        //Toolbar toolbar = findViewById(R.id.toolbar);
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        initRecyclerView();
        initViewModel();

       // FloatingActionButton fab = findViewById(R.id.fab_term_add);
        FloatingActionButton fab = binding.fabTermAdd;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermsActivity.this, TermDetailActivity.class);
//                Snackbar.make(view, "The Add term button", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                startActivity(intent);
            }
        });
    }

    private void initViewModel() {
        final Observer<List<Term>> termObserver = newTerms -> {
            terms.clear();
            terms.addAll(newTerms);
            if(termsAdapter == null) {
                termsAdapter = new TermsAdapter(terms, TermsActivity.this);
                rcTerms.setAdapter(termsAdapter);
                System.out.println("should never be called");
            } else {
                termsAdapter.notifyDataSetChanged();
            }
        };
        //termsViewModel = ViewModelProviders.of(this).get(TermsViewModel.class);
        termsViewModel = new ViewModelProvider(this).get(TermsViewModel.class);
        termsViewModel.getTerms().observe(this, termObserver);
        termsAdapter.setOnTermClickListener(term -> {
            Intent intent = new Intent(TermsActivity.this, TermDetailActivity.class);
           // System.out.println(term + " clicked" + System.getProperty("line.separator") + "(IMPLEMENT SOMETHING)");
            intent.putExtra(TERM_ID_KEY, term.getTermId());
            startActivity(intent);
        });
    }


    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rcTerms.setLayoutManager(layoutManager);
        rcTerms.setHasFixedSize(true);
        termsAdapter = new TermsAdapter(terms, this);
        rcTerms.setAdapter(termsAdapter);
    }

}
