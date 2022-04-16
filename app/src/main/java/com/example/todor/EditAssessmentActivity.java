package com.example.todor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todor.database.AppDatabase;
import com.example.todor.database.Assessment;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditAssessmentActivity extends AppCompatActivity {
    // edit spinner
    List<String> statusList2=new ArrayList<>();
    MaterialSpinner spinner2;

    EditText tEt,edEt;
    AppDatabase appDatabase;
    int courseId;
    int assessmentId;
    DatePickerDialog.OnDateSetListener  eDatesetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_assessment);
         //edit test
        statusList2.add("Objective Assessment");
        statusList2.add("Performance Assessment");
        statusList2.add("Dropped");
        statusList2.add("Plan to take");
        spinner2 = (MaterialSpinner)findViewById(R.id.spinner);
        spinner2.setItems(statusList2);

        tEt= (EditText) findViewById(R.id.tEt);
        edEt= (EditText) findViewById(R.id.edEt);
        appDatabase=AppDatabase.getDbInstance(this);

        Bundle bundle=getIntent().getExtras();
        if (bundle!=null){
            assessmentId=bundle.getInt("id");
            courseId=bundle.getInt("courseId");
            tEt.setText(bundle.getString("title"));
            edEt.setText(bundle.getString("ed"));

            //edit spinner 2
            spinner2.setText(bundle.getString("test"));
        }


        edEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int mnth = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(EditAssessmentActivity.this, android.R.style.Theme_DeviceDefault_Dialog, eDatesetListener, year, mnth, day);
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

    public void editAssessment(View view) {

        if (tEt.getText().toString().length()<1){
            tEt.setError("Enter title");
            return;
        }


        if (edEt.getText().toString().length()<1){
            edEt.setError("Enter ending date");
            return;
        }

        Assessment assessment=new Assessment();
        assessment.title=tEt.getText().toString();
        assessment.endDate=edEt.getText().toString();
        assessment.courseId=courseId;
        assessment.id=assessmentId;

        appDatabase.assessmentDao().updateAssessment(assessment);
        Toast.makeText(this, "Assessment updated successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}