package com.project.taskhub2;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;


public class MainFragment extends Fragment {
    public ArrayList<Task> tasks = new ArrayList<Task>();
    RecyclerView recyclerView;
    TaskAdapter taskAdapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userRef = database.getReference("Users");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_main, container, false);


        setInitialData();
        recyclerView = v.findViewById(R.id.recycleView);
        taskAdapter = new TaskAdapter(this.getContext(), tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(taskAdapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this.getContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Task selectedTask = tasks.get(position);
                        Gson gson = new Gson();
                        String taskJson = gson.toJson(selectedTask);

                        Intent intent = new Intent(getContext(), TaskActivity.class);
                        intent.putExtra("task_info", taskJson);
                        startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        Log.i("ESHKEREEE", " LONG!!!!!!! click on item)");
                    }
                })
        );

        return v;
    }

    private void setInitialData(){
        
    }
}