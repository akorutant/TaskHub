package com.project.taskhub2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateTaskActivity extends AppCompatActivity {
    EditText taskTitle, taskDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        taskTitle = findViewById(R.id.taskTitleEdit);
        taskDescription = findViewById(R.id.taskDescriptionEdit);
        Button submit = findViewById(R.id.submitButton);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                states.add(new State ("Помочь максиму", "Не помогать максиму сам справится"));
            }
        });

    }
}
