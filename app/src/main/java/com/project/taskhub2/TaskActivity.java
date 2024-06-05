package com.project.taskhub2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.Objects;

public class TaskActivity extends AppCompatActivity {
    Button takeTaskButton, taskCompletedButton, editTaskButton, deleteBtn;
    FirebaseDatabase database;
    DatabaseReference taskRef;
    FirebaseAuth auth;
    FirebaseUser user;
    CardView completion, someonetake;
    TextView workerInfo;

    User currentWorker;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        LinearLayout workerLayout = findViewById(R.id.workerLayout);
        takeTaskButton = findViewById(R.id.takeTaskButton);
        taskCompletedButton = findViewById(R.id.taskCompletedButton);
        editTaskButton = findViewById(R.id.editTaskButton);
        completion = findViewById(R.id.completion);
        workerInfo = findViewById(R.id.workerInfo);
        someonetake = findViewById(R.id.someonetake);
        deleteBtn = findViewById(R.id.deleteTask);

        Gson gson = new Gson();
        String taskJson = getIntent().getStringExtra("task_info");
        Task task = gson.fromJson(taskJson, Task.class);

        database = FirebaseDatabase.getInstance();
        taskRef = database.getReference("Task").child(task.getId());
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        taskRef.child("completed").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Boolean val = snapshot.getValue(Boolean.class);
                    if (Boolean.TRUE.equals(val)) {
                        completion.setVisibility(View.VISIBLE);
                        takeTaskButton.setVisibility(View.GONE);
                        taskCompletedButton.setVisibility(View.GONE);
                        editTaskButton.setVisibility(View.GONE);
                        someonetake.setVisibility(View.GONE);
                        deleteBtn.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        taskRef.child("worker").addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    User worker = snapshot.getValue(User.class);
                    currentWorker = worker;
                    assert worker != null;
                    // кто то взял задачу
                    if (!Objects.equals(worker.getId(), user.getUid()) && !Objects.equals(worker.getId(), "NULL")) {
                        takeTaskButton.setVisibility(View.GONE);
                        taskCompletedButton.setVisibility(View.GONE);
                        editTaskButton.setVisibility(View.GONE);
                        someonetake.setVisibility(View.VISIBLE);
                        workerInfo.setText("> " + worker.getName());
                    }
                    else if (Objects.equals(worker.getId(), user.getUid())) {
                        takeTaskButton.setBackgroundResource(R.drawable.peudo_checkbox);
                        takeTaskButton.setText("Вы взялись за эту задачу");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
//f
            }
        });

        TextView taskNameTextView = findViewById(R.id.taskName);
        taskNameTextView.setText(task.getName());

        TextView taskDescriptionTextView = findViewById(R.id.taskDescription);
        taskDescriptionTextView.setText(task.getText());

        editTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String taskJson = gson.toJson(task);

                Intent intent = new Intent(getApplicationContext(), EditTaskActivity.class);
                intent.putExtra("task_info", taskJson);
                startActivity(intent);
            }
        });

        takeTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User currentUser = new User();
                currentUser.setId(user.getUid());
                currentUser.setEmail(user.getEmail());
                currentUser.setName(user.getDisplayName());
                if (Objects.equals(currentUser.getId(), currentWorker.getId())) {
                    taskRef.child("worker").setValue(new User("NULL", "NULL", "NULL"));
                    takeTaskButton.setText("Взять задачу");
                    taskRef.child("select").setValue(false);
                }
                else {
                    taskRef.child("worker").setValue(currentUser);
                    taskRef.child("select").setValue(true);
                    takeTaskButton.setBackground(getDrawable(R.drawable.peudo_checkbox));
                    takeTaskButton.setText("Вы взялись за эту задачу");
                }

            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskRef.removeValue();
                Toast.makeText(getApplicationContext(), "Задача удалена!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        taskCompletedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskRef.child("completed").setValue(true);
                Log.i("TAsk", taskRef.child("completed").toString());
                Toast.makeText(getApplicationContext(), "Задача выполнена!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}
