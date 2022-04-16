package com.example.todor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todor.database.AppDatabase;
import com.example.todor.database.Course;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditCourseActivity extends AppCompatActivity {
    List<String> statusList=new ArrayList<>();
    MaterialSpinner spinner;
    EditText tEt,sdEt,edEt,inEt,ipEt,ieEt,noteEt;
    AppDatabase appDatabase;
    DatePickerDialog.OnDateSetListener  sDatesetListener;
    DatePickerDialog.OnDateSetListener  eDatesetListener;
    int courseId;
    int termId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);

        statusList.add("In progress");
        statusList.add("Completed");
        statusList.add("Dropped");
        statusList.add("Plan to take");
        spinner = (MaterialSpinner)findViewById(R.id.spinner);
        spinner.setItems(statusList);
        tEt= (EditText) findViewById(R.id.tEt);
        sdEt= (EditText) findViewById(R.id.sdEt);
        edEt= (EditText) findViewById(R.id.edEt);
        inEt= (EditText) findViewById(R.id.inEt);
        ipEt= (EditText) findViewById(R.id.ipEt);
        ieEt= (EditText) findViewById(R.id.ieEt);
        noteEt= (EditText) findViewById(R.id.noteEt);

        Bundle bundle=getIntent().getExtras();
        if (bundle!=null){
            courseId=bundle.getInt("id");
            termId=bundle.getInt("termId");
            tEt.setText(bundle.getString("title"));
            sdEt.setText(bundle.getString("sd"));
            edEt.setText(bundle.getString("ed"));
            inEt.setText( bundle.getString("in"));
            ipEt.setText(bundle.getString("ip"));
            ieEt.setText(bundle.getString("ie"));
            noteEt.setText(bundle.getString("note"));
            spinner.setText(bundle.getString("status"));
        }


        appDatabase=AppDatabase.getDbInstance(this);

        sdEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int mnth = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditCourseActivity.this, android.R.style.Theme_DeviceDefault_Dialog, sDatesetListener, year, mnth, day);
                datePickerDialog.show();

            }
        });

        sDatesetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                i1++;
                sdEt.setText(i2 + "/" + i1 + "/" + i);

            }
        };

        edEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int mnth = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(EditCourseActivity.this, android.R.style.Theme_DeviceDefault_Dialog, eDatesetListener, year, mnth, day);
                datePickerDialog.show();

            }
        });

        eDatesetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                i1++;
                edEt.setText(i2 + "/" + i1 + "/" + i);

            }
        };
    }

    public void editCourse(View view) {


        if (tEt.getText().toString().length()<1){
            tEt.setError("Enter title");
            return;
        }

        if (inEt.getText().toString().length()<1){
            inEt.setError("Enter name");
            return;
        }
        if (ipEt.getText().toString().length()<1){
            ipEt.setError("Enter phone number");
            return;
        }
        if (ieEt.getText().toString().length()<1){
            ieEt.setError("Enter email");
            return;
        }

        if (sdEt.getText().toString().length()<1){
            sdEt.setError("Enter starting date");
            return;
        }

        if (edEt.getText().toString().length()<1){
            edEt.setError("Enter ending date");
            return;
        }

        Course course=new Course();
        course.title=tEt.getText().toString();
        course.startDate=sdEt.getText().toString();
        course.endDate=edEt.getText().toString();
        course.instructorName=inEt.getText().toString();
        course.instructorPhone=ipEt.getText().toString();
        course.instructorEmail=ieEt.getText().toString();
        course.termId=termId;
        course.id=courseId;
        course.status=spinner.getText().toString();
        course.note=noteEt.getText().toString();

        appDatabase.courseDao().updateCourse(course);
        Toast.makeText(this, "Course updated successfully", Toast.LENGTH_SHORT).show();
        finish();

    }
}