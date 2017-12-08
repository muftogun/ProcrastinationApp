package com.team1.cmsc434.procrastinationapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class
EditTask extends AppCompatActivity {
    private final String TAG = "EDIT_TASK_ACTIVITY";

    EditText taskName;
    Spinner taskType;
    EditText taskDate;
    Spinner taskDifficulty;
    RatingBar taskImportance;
    EditText taskDetails;

    Button updateTaskButton;

    Calendar myCalendar;

    DatePickerDialog.OnDateSetListener dateListener;

    @Override
    public void onCreate(Bundle savedInstanceStance) {
        super.onCreate(savedInstanceStance);

        myCalendar = Calendar.getInstance();

        setContentView(R.layout.edit_task_layout);

        taskName = findViewById(R.id.name_field);
        taskType = findViewById(R.id.type_field);
        taskDate = findViewById(R.id.date_field);
        taskDifficulty = findViewById(R.id.difficulty_field);
        taskImportance = findViewById(R.id.importance_field);
        taskDetails = findViewById(R.id.details_field);

        updateTaskButton = findViewById(R.id.update_task_button);

        final Task task = new Task(getIntent());

        myCalendar.set(Calendar.YEAR, task.dueDate.getYear());
        myCalendar.set(Calendar.MONTH, task.dueDate.getMonth());
        myCalendar.set(Calendar.DAY_OF_MONTH, task.dueDate.getDate());

        taskName.setText(task.name);
        //taskType.setEn(task.type.name());
        taskDate.setText(task.dueDate.toString());
        //taskDifficulty.setText(task.difficulty.name());
        taskImportance.setRating(task.importance);
        taskDetails.setText(task.details);

        String compareValue = task.type.name();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        taskType.setAdapter(adapter);
        if (!compareValue.equals(null)) {
            int spinnerPosition = adapter.getPosition(compareValue);
            taskType.setSelection(spinnerPosition);
        }

        String compareValue2 = task.difficulty.name();
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.difficulty_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        taskDifficulty.setAdapter(adapter2);
        if (!compareValue2.equals(null)) {
            int spinnerPosition = adapter2.getPosition(compareValue2);
            taskDifficulty.setSelection(spinnerPosition);
        }

        updateLabel();

        taskDetails.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    updateTaskButton.performClick();
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
                new DatePickerDialog(EditTask.this, dateListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        updateTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(taskName.getText().toString() != "") {
                    task.name = taskName.getText().toString();
                    task.type = Task.Type.valueOf(String.valueOf(taskType.getSelectedItem()));
                    task.dueDate = myCalendar.getTime();
                    task.difficulty = Task.Difficulty.valueOf(String.valueOf(taskDifficulty.getSelectedItem()));
                    task.importance = taskImportance.getRating();
                    task.details = taskDetails.getText().toString();

                    /*task.se
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
                    }*/
                    Toast.makeText(getApplicationContext(), "Task updated.", Toast.LENGTH_SHORT).show();
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
