package com.team1.cmsc434.procrastinationapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class HomeActivity extends AppCompatActivity {
    private final String TAG = "HOME_ACTIVITY";
    public static final String dataFile = "taskData.txt";

    private TaskAdapter mAdapter;

    ListView taskList;
    ImageButton viewAllButton;
    ImageButton addNewButton;

    @SuppressLint("WrongViewCast")
    @Override
    public void onCreate(Bundle savedInstanceStance) {
        super.onCreate(savedInstanceStance);

        FileOutputStream fos;
        try {
            fos = openFileOutput(dataFile, Context.MODE_PRIVATE);
            fos.close();
        } catch (Exception E){
            // ;)
        }

        setContentView(R.layout.home_layout);

        taskList = findViewById(R.id.home_task_list);

        viewAllButton = findViewById(R.id.view_all_button);
        addNewButton = findViewById(R.id.add_new_button);

        taskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                Log.d(TAG, "Tapped a list item.");
                Task task = (Task) mAdapter.getItem(pos);

                Intent intent = task.packageToIntent();

                intent.setClass(getApplicationContext(),ViewDetails.class);
                startActivity(intent);
            }
        });

        viewAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ViewAll.class));
            }
        });

        addNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), NewTask.class));
            }
        });

        mAdapter = new TaskAdapter(getApplicationContext());

        taskList.setAdapter(mAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAdapter.clear();
        FileInputStream fis;
        try {
            fis = openFileInput(dataFile);
            Scanner scanner = new Scanner(fis);
            scanner.useDelimiter("`"); // ` will separate entries in the file
            while(scanner.hasNext())
                mAdapter.add(new Task(scanner.next()));
            scanner.close();
            fis.close();
        } catch (java.io.IOException e) {
            Log.d(TAG, "Unable to access dataFile. Has user added any tasks?");
        }
    }
}
