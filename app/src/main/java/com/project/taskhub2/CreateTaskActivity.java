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

import java.util.ArrayList;
import java.util.HashMap;

public class CreateTaskActivity extends AppCompatActivity {
    EditText taskTitle, taskDescription;

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
                    HashMap<String, String> creationData = new HashMap<String, String>();
                    creationData.put("taskTitle", taskTitle.getText().toString());
                    creationData.put("taskDescription", taskDescription.getText().toString());

                    Log.i("CREATED", "New Task Created: " + taskTitle.getText() + " " + taskDescription.getText());

                    Intent i = new Intent();
                    i.putExtra("creationData", creationData);
                    setResult(RESULT_OK, i);
                    finish();
                }
                else {
                    Log.i("FAIL", "Task Creation Fail: ");
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
