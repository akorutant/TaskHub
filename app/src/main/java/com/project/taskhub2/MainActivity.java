package com.project.taskhub2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    public ArrayList<Task> tasks = new ArrayList<Task>();
    FloatingActionButton createTaskButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) startActivity(new Intent(this, LoginActivity.class));

        createTaskButton = findViewById(R.id.createTask);
        createTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CreateTaskActivity.class));
            }
        });

        // init Firebase Database
        FirebaseApp.initializeApp(this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference();

        setInitialData();
        RecyclerView recyclerView = findViewById(R.id.recycleView);
        TaskAdapter taskAdapter = new TaskAdapter(this, tasks);
        recyclerView.setAdapter(taskAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case RESULT_OK:
                HashMap<String, String> newTask = (HashMap<String, String>) data.getSerializableExtra("creationData");
                break;
        }
    }

    private void setInitialData(){

        User user1 = new User("1", "user1", "mail@mail.ma");
        User user2 = new User("2", "user222", "maiffl@madfsil.ma");
        User user3 = new User("3", "user33333", "fdfd@mafdsfil.ma");

        Group gr1 = new Group("1", "gr1", user1);
        Group gr2 = new Group("2", "gr2 team", user1);

        tasks.add(new Task("1", "Что-то сделать ээ", "аовылмроролруолкруцоролровыарвыоаротмсчолмтолраолыр", user1, gr1));
        tasks.add(new Task("2", "Что-то сделать ээ", "аовылмроролруолкруцоролровыарвыоаротмсчолмтолраолыр", user2, gr1));
        tasks.add(new Task("3", "Что-то сделать ээ", "аовылмроролруолкруцоролровыарвыоаротмсчолмтолраолыр", user3, gr2));
        tasks.add(new Task("4", "Что-то сделать ээ", "аовылмроролруолкруцоролровыарвыоаротмсчолмтолраолыр", user2, gr2));
        tasks.add(new Task("5", "Что-то сделать ээ", "аовылмроролруолкруцоролровыарвыоаротмсчолмтолраолыр", user3, gr1));
        tasks.add(new Task("6", "Что-то сделать ээ", "аовылмроролруолкруцоролровыарвыоаротмсчолмтолраолыр", user1, gr1));
        tasks.add(new Task("7", "Что-то сделать ээ", "аовылмроролруолкруцоролровыарвыоаротмсчолмтолраолыр", user3, gr1));

    }

}