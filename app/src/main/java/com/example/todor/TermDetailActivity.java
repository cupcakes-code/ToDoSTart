package com.example.todor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.todor.adapters.CoursesAdapter;
import com.example.todor.database.AppDatabase;
import com.example.todor.database.Course;

import java.util.ArrayList;
import java.util.List;

public class TermDetailActivity extends AppCompatActivity {
int termId;
TextView tTv,sdTv,edTv;
    RecyclerView rv;
    AppDatabase appDatabase;
    List<Course> courseList=new ArrayList<>();
    CoursesAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);

        tTv= (TextView) findViewById(R.id.tTv);
        sdTv= (TextView) findViewById(R.id.sdTv);
        edTv= (TextView) findViewById(R.id.edTv);
        rv=findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        appDatabase= AppDatabase.getDbInstance(this);

        Bundle bundle=getIntent().getExtras();
        if (bundle!=null){
            termId=bundle.getInt("id");
            tTv.setText(bundle.getString("title"));
            sdTv.setText(bundle.getString("sd"));
            edTv.setText(bundle.getString("ed"));
        }
    }

    public void addNewCourse(View view) {
        Intent intent=new Intent(getApplicationContext(),AddCourseActivity.class);
        intent.putExtra("id",termId);
         startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        loadCourses();
    }

    private void loadCourses() {
        courseList.clear();
        courseList=appDatabase.courseDao().getAllCourses(termId);
        adapter=new CoursesAdapter(courseList,getApplicationContext());
        rv.setAdapter(adapter);

    }
}