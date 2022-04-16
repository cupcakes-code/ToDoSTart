package com.example.todor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.TextView;

import com.example.todor.adapters.AssessmentsAdapter;
import com.example.todor.database.AppDatabase;
import com.example.todor.database.Assessment;

import java.util.ArrayList;
import java.util.List;

public class AssessmentDetailsActivity extends AppCompatActivity {

    TextView tTv,sdTv,edTv;
    //
    AppDatabase appDatabase;
    List<Assessment> statusList2=new ArrayList<>();
    AssessmentsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);

        tTv= (TextView) findViewById(R.id.tTv);
        sdTv= (TextView) findViewById(R.id.sdTv);
        edTv= (TextView) findViewById(R.id.edTv);
        //
        appDatabase= AppDatabase.getDbInstance(this);



        Bundle bundle=getIntent().getExtras();
        if (bundle!=null){

            tTv.setText(bundle.getString("title"));
            edTv.setText(bundle.getString("ed"));

            // edit test
            tTv.setText(bundle.getString("title")+" ("+bundle.getString(String.valueOf(statusList2))+" )");


        }
    }
}