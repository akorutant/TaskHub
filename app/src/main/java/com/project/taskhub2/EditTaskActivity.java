package com.project.taskhub2;

import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

public class EditTaskActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_tak_activity);
        EditText taskTitle = findViewById(R.id.taskTitle);
        EditText taskDescription = findViewById(R.id.taskDescription);

        Gson gson = new Gson();
        String taskJson = getIntent().getStringExtra("task_info");
        Task task = gson.fromJson(taskJson, Task.class);

        taskTitle.setText(task.getName());
        taskDescription.setText(task.getText());

    }
}
