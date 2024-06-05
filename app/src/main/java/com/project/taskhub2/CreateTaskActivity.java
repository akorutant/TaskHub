package com.project.taskhub2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.transition.Visibility;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class CreateTaskActivity extends AppCompatActivity {
    EditText taskTitle, taskDescription;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userRef = database.getReference("User ");
    DatabaseReference taskRef = database.getReference("Task");
    DatabaseReference groupRef = database.getReference("Group");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        taskTitle = findViewById(R.id.taskTitle);
        taskDescription = findViewById(R.id.taskDescription);
        Button submit = findViewById(R.id.submitButton);

        taskTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                taskTitle.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.text_muted)));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        taskDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                taskDescription.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.text_muted)));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (taskTitle.getText().toString().length() >= 4 && taskDescription.getText().toString().length() >= 8) {
                    String groupId = getIntent().getStringExtra("groupId");

                    DatabaseReference groupRef = FirebaseDatabase.getInstance().getReference("Group");
                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    assert firebaseUser != null;
                    String userId = firebaseUser.getUid();
                    String userName = firebaseUser.getDisplayName();
                    String userEmail = firebaseUser.getEmail();
                    User user =  new User(userId, userName, userEmail);

                    String taskId = taskRef.push().getKey();

                    groupRef.child(groupId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Group group = dataSnapshot.getValue(Group.class);
                            User nullWorker = new User("NULL", "NULL", "NULL");
                            Task task = new Task(taskId, taskTitle.getText().toString(), taskDescription.getText().toString(), user, group, false, nullWorker);

                            assert taskId != null;
                            taskRef.child(taskId).setValue(task);

                            Intent intent = new Intent();
                            setResult(RESULT_OK, intent);
                            finish();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.e("CreateTaskActivity", "Failed to read group data", databaseError.toException());
                        }
                    });

                }
                else {
                    Log.i("FAIL", "Task Creation Fail: ");
                    Toast.makeText(getApplicationContext(), "Мин. длина заголовка - 4 симв., Описания - 8 симв.", Toast.LENGTH_SHORT).show();
                    if (taskTitle.getText().toString().length() < 4) {
                        taskTitle.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.danger)));
                    }
                    if (taskDescription.getText().toString().length() < 8) {
                        taskDescription.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.danger)));
                    }
                }
            }
        });



    }
}
