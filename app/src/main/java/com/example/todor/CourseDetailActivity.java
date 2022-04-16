package com.example.todor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.todor.adapters.AssessmentsAdapter;
import com.example.todor.adapters.CoursesAdapter;
import com.example.todor.database.AppDatabase;
import com.example.todor.database.Assessment;

import java.util.ArrayList;
import java.util.List;

public class CourseDetailActivity extends AppCompatActivity {
    int courseId;
    TextView tTv,sdTv,edTv,nameTv,phoneTv,mailTv,noteTv,shareTv;
    RecyclerView rv;
    AppDatabase appDatabase;
    List<Assessment> assessmentList=new ArrayList<>();
    AssessmentsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        shareTv= (TextView) findViewById(R.id.shareTv);
        tTv= (TextView) findViewById(R.id.tTv);
        sdTv= (TextView) findViewById(R.id.sdTv);
        edTv= (TextView) findViewById(R.id.edTv);
        nameTv= (TextView) findViewById(R.id.nameTv);
        phoneTv= (TextView) findViewById(R.id.phoneTv);
        mailTv= (TextView) findViewById(R.id.emailTv);
        noteTv= (TextView) findViewById(R.id.noteTv);
        rv=findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        appDatabase= AppDatabase.getDbInstance(this);

        Bundle bundle=getIntent().getExtras();
        if (bundle!=null){
            courseId=bundle.getInt("id");
            tTv.setText(bundle.getString("title")+" ("+bundle.getString("status")+" )");
            sdTv.setText(bundle.getString("sd"));
            edTv.setText(bundle.getString("ed"));
            nameTv.setText("Instructor Name  : "+bundle.getString("in"));
            phoneTv.setText("Instructor Phone : "+bundle.getString("ip"));
            mailTv.setText("Instructor Email  : "+bundle.getString("ie"));
            noteTv.setText(bundle.getString("note"));

        }

        shareTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
//                sendIntent.setData(Uri.parse("sms:"));
//                sendIntent.putExtra("sms_body", noteTv.getText().toString());
//                startActivity(sendIntent);

                Intent intent2 = new Intent();
                intent2.setAction(Intent.ACTION_SEND);
                intent2.setType("text/plain");
                intent2.putExtra(Intent.EXTRA_TEXT, noteTv.getText().toString() );
                startActivity(Intent.createChooser(intent2, "Share via"));
            }
        });


    }

    public void addNewAssessment(View view) {

        Intent intent=new Intent(getApplicationContext(),AddAssessmentActivity.class);
        intent.putExtra("id",courseId);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        loadAssessments();
    }

    private void loadAssessments() {
        assessmentList.clear();
        assessmentList=appDatabase.assessmentDao().getAllAssessments(courseId);
        adapter=new AssessmentsAdapter(assessmentList,getApplicationContext());
        rv.setAdapter(adapter);

    }
}