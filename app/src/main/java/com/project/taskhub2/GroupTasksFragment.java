package com.project.taskhub2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GroupTasksFragment extends Fragment {

    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private ArrayList<Task> tasks = new ArrayList<Task>();
    private DatabaseReference tasksRef;
    String groupId;
    public GroupTasksFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_group_tasks, container, false);

        recyclerView = v.findViewById(R.id.groupTaksRecyclerView);
        taskAdapter = new TaskAdapter(this.getContext(), tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(taskAdapter);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            groupId = bundle.getString("groupId");
        }

        if (groupId == null) {
            Log.e("GroupMembersFragment", "groupId is null");
            return v; // или вывести сообщение об ошибке для пользователя
        }

        // Get tasks from Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        tasksRef = database.getReference("Task");
        tasksRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tasks.clear();
                for (DataSnapshot taskSnapshot : dataSnapshot.getChildren()) {
                    Task task = taskSnapshot.getValue(Task.class);
                    // проверяем, что groupId задачи совпадает с groupId группы
                    if (task.getGroup() != null && task.getGroup().getId().equals(groupId)) {
                        tasks.add(task);
                    }
                }
                taskAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("GroupTasksFragment", "onCancelled", databaseError.toException());
            }
        });

        return v;
    }
}
