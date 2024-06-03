package com.project.taskhub2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

public class TaskActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        LinearLayout workerLayout = findViewById(R.id.workerLayout);
        TextView workerInfo = findViewById(R.id.workerInfo);
        Button takeTaskButton = findViewById(R.id.takeTaskButton);
        Button taskCompletedButton = findViewById(R.id.taskCompletedButton);
        Button editTaskButton = findViewById(R.id.editTaskButton);

        Gson gson = new Gson();
        String taskJson = getIntent().getStringExtra("task_info");
        Task task = gson.fromJson(taskJson, Task.class);

        TextView taskNameTextView = findViewById(R.id.taskName);
        taskNameTextView.setText(task.getName());

        TextView taskDescriptionTextView = findViewById(R.id.taskDescription);
        taskDescriptionTextView.setText(task.getText());

        if (!task.isSelect) {
            workerLayout.setVisibility(View.GONE);
        } else {
            takeTaskButton.setVisibility(View.GONE);
            taskCompletedButton.setVisibility(View.GONE);
            editTaskButton.setVisibility(View.GONE);

            workerInfo.setText("> " + task.worker.getName());
        }

        editTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String taskJson = gson.toJson(task);

                Intent intent = new Intent(getApplicationContext(), EditTaskActivity.class);
                intent.putExtra("task_info", taskJson);
                startActivity(intent);             }
        });

    }
}
