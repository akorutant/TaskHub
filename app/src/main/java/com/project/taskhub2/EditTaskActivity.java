package com.project.taskhub2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class EditTaskActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference taskRef;
    EditText taskTitle, taskDescription;
    Button commitBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_tak_activity);
        taskTitle = findViewById(R.id.taskTitle);
        taskDescription = findViewById(R.id.taskDescription);
        commitBtn = findViewById(R.id.submitButton);

        Gson gson = new Gson();
        String taskJson = getIntent().getStringExtra("task_info");
        Task task = gson.fromJson(taskJson, Task.class);

        database = FirebaseDatabase.getInstance();
        taskRef = database.getReference("Task").child(task.id);

        taskRef.child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String name = snapshot.getValue(String.class);
                    taskTitle.setText(name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        taskRef.child("description").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String d = snapshot.getValue(String.class);
                    taskDescription.setText(d);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        commitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (taskTitle.getText().toString().length() >= 4 && taskDescription.getText().toString().length() >= 8) {
                    taskRef.child("name").setValue(taskTitle.getText().toString());
                    taskRef.child("description").setValue(taskDescription.getText().toString());
                    Toast.makeText(getApplicationContext(), "Успешно!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Мин. длина заголовка - 4 симв., Описания - 8 симв.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
