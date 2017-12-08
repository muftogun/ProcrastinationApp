package com.team1.cmsc434.procrastinationapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Santiago Vanegas Marino on 11/8/2017.
 */

public class
NewTask extends AppCompatActivity {
    private final String TAG = "NEW_TASK_ACTIVITY";

    EditText taskName;
    Spinner taskType;
    EditText taskDate;
    Spinner taskDifficulty;
    RatingBar taskImportance;
    EditText taskDetails;

    Button addTaskButton;

    Calendar myCalendar;

    DatePickerDialog.OnDateSetListener dateListener;

    @Override
    public void onCreate(Bundle savedInstanceStance) {
        super.onCreate(savedInstanceStance);

        myCalendar = Calendar.getInstance();

        setContentView(R.layout.add_new_task_layout);

        taskName = findViewById(R.id.name_field);
        taskType = findViewById(R.id.type_field);
        taskDate = findViewById(R.id.date_field);
        taskDifficulty = findViewById(R.id.difficulty_field);
        taskImportance = findViewById(R.id.importance_field);
        taskDetails = findViewById(R.id.details_field);

        addTaskButton = findViewById(R.id.add_task_button);

        updateLabel();

        taskDetails.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    addTaskButton.performClick();
                    return true;
                }
                return false;
            }
        });

        dateListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        taskDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(NewTask.this, dateListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(taskName.getText().toString() != "") {

                   Task adding = new Task(taskName.getText().toString(),
                            Task.Type.valueOf(String.valueOf(taskType.getSelectedItem())),
                            myCalendar,
                            Task.Difficulty.valueOf(String.valueOf(taskDifficulty.getSelectedItem())),
                            taskImportance.getRating(), taskDetails.getText().toString());
                   FileOutputStream fos;
                   try {
                       fos = openFileOutput(HomeActivity.dataFile, Context.MODE_APPEND);
                       fos.write(adding.packageForFile().getBytes());
                       fos.close();
                       Log.d(TAG, "Wrote to datafile.");
                   } catch (java.io.IOException e) {
                       Log.d(TAG, "Could not write to datafile.");
                   }
                   Toast.makeText(getApplicationContext(), "Task added.", Toast.LENGTH_SHORT).show();
                   finish();
                }
            }
        });
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        taskDate.setText(sdf.format(myCalendar.getTime()));
    }
}
