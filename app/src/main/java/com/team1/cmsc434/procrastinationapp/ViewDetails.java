package com.team1.cmsc434.procrastinationapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Santiago Vanegas Marino on 11/8/2017.
 */

public class ViewDetails extends AppCompatActivity {
    private final String TAG = "VIEW_DETAILS_ACTIVITY";

    TextView taskName;
    TextView taskType;
    TextView taskDate;
    TextView taskDifficulty;
    RatingBar taskImportance;
    TextView taskDetails;
    Button editTaskButton;

    @Override
    public void onCreate(Bundle savedInstanceStance) {
        super.onCreate(savedInstanceStance);

        setContentView(R.layout.view_task_details);
        taskName = findViewById(R.id.name_details);
        taskType = findViewById(R.id.type_details);
        taskDate = findViewById(R.id.deadline_details);
        taskDifficulty = findViewById(R.id.difficulty_details);
        taskImportance = findViewById(R.id.ratingBar);
        taskDetails = findViewById(R.id.details_field);
        editTaskButton = findViewById(R.id.edittask);

        Task task = new Task(getIntent());

        taskName.setText(task.name);
        taskType.setText(task.type.name());
        taskDate.setText(task.dueDate.toString());
        taskDifficulty.setText(task.difficulty.name());
        taskImportance.setRating(task.importance);
        taskDetails.setText(task.details);

        editTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task task = new Task(getIntent());
                Intent intent = task.packageToIntent();

                intent.setClass(getApplicationContext(),EditTask.class);
                startActivity(intent);
                //startActivity(new Intent(getApplicationContext(), EditTask.class));
            }
        });
    }
}
