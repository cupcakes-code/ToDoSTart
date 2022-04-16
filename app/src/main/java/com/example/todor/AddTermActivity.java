package com.example.todor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todor.database.AppDatabase;
import com.example.todor.database.Term;

import java.util.Calendar;

public class AddTermActivity extends AppCompatActivity {
EditText tEt,sdEt,edEt;
AppDatabase appDatabase;

    DatePickerDialog.OnDateSetListener  sDatesetListener;
    DatePickerDialog.OnDateSetListener  eDatesetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);

        tEt= (EditText) findViewById(R.id.tEt);
        sdEt= (EditText) findViewById(R.id.sdEt);
        edEt= (EditText) findViewById(R.id.edEt);

        appDatabase=AppDatabase.getDbInstance(this);

        sdEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int mnth = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTermActivity.this, android.R.style.Theme_DeviceDefault_Dialog, sDatesetListener, year, mnth, day);
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

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTermActivity.this, android.R.style.Theme_DeviceDefault_Dialog, eDatesetListener, year, mnth, day);
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

    public void addNewTerm(View view) {

        if (tEt.getText().toString().length()<1){
            tEt.setError("Enter title");
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

        Term term=new Term();
        term.title=tEt.getText().toString();
        term.startDate=sdEt.getText().toString();
        term.endDate=edEt.getText().toString();

        appDatabase.termDao().insertNewTerm(term);
        Toast.makeText(this, "New term inserted successfully", Toast.LENGTH_SHORT).show();
        finish();


    }
}