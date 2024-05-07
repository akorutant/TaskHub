package com.project.taskhub2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<State> states = new ArrayList<State>();
    FloatingActionButton createTaskButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createTaskButton = findViewById(R.id.createTask);
        createTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateTaskActivity.class);
                startActivity(intent);
            }
        });


        // init Firebase Database
        FirebaseApp.initializeApp(this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference();

        setInitialData();
        RecyclerView recyclerView = findViewById(R.id.recycleView);
        StateAdapter adapter = new StateAdapter(this, states);
        recyclerView.setAdapter(adapter);
    }

    private void setInitialData(){

        states.add(new State ("Помочь максиму", "Не помогать максиму сам справится"));
    }

}