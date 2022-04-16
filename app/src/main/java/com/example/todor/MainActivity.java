package com.example.todor;

import static com.example.todor.AlertWorker.CHANNEL_ID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.todor.adapters.TermsAdapter;
import com.example.todor.database.AppDatabase;
import com.example.todor.database.Term;
import com.example.todor.database.TermDao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    RecyclerView rv;
    AppDatabase appDatabase;
    List<Term> termList = new ArrayList<>();
    TermsAdapter termsAdapter;
    private static final String TAG = "PeriodicWorkTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Terms");

        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        appDatabase = AppDatabase.getDbInstance(this);



        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(AlertWorker.class, 20,
                TimeUnit.MINUTES)
                .addTag(TAG)
                .build();
        WorkManager.getInstance().enqueueUniquePeriodicWork(TAG ,ExistingPeriodicWorkPolicy.REPLACE, periodicWorkRequest);





    }

    @Override
    protected void onStart() {
        super.onStart();
        loadTerms();
    }

    private void loadTerms() {
        termList.clear();
        termList = appDatabase.termDao().getAllTerms();
        termsAdapter = new TermsAdapter(termList, getApplicationContext());
        rv.setAdapter(termsAdapter);
    }

    public void addNewTerm(View view) {
        startActivity(new Intent(getApplicationContext(), AddTermActivity.class));
    }


}