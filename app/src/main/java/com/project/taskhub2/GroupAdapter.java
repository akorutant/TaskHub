package com.project.taskhub2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {

    private Context context;
    private List<Group> groups;
    private DatabaseReference taskRef;

    public GroupAdapter(Context context, List<Group> groups) {
        this.context = context;
        this.groups = groups;
        taskRef = FirebaseDatabase.getInstance().getReference("Task");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.group_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Group group = groups.get(position);
        holder.groupName.setText(group.getName());
        holder.peopleCount.setText(String.valueOf(group.users.size()));

        // Получаем список задач по groupId
        Query taskQuery = taskRef.orderByChild("group/id").equalTo(group.getId());
        taskQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int completedTasks = 0;
                int allTasks = (int) dataSnapshot.getChildrenCount();
                for (DataSnapshot taskSnapshot : dataSnapshot.getChildren()) {
                    Task task = taskSnapshot.getValue(Task.class);
                    if (task.getCompleted()) {
                        completedTasks++;
                    }
                }
                holder.tasksCount.setText(String.valueOf(allTasks));
                holder.completedTasks.setText(String.valueOf(completedTasks));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Обработка ошибки
            }
        });
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView groupName, peopleCount, tasksCount, completedTasks;

        ViewHolder(View view) {
            super(view);
            groupName = view.findViewById(R.id.group_name_tv);
            peopleCount = view.findViewById(R.id.people_count_tv);
            tasksCount = view.findViewById(R.id.all_tasks_count_tv);
            completedTasks = view.findViewById(R.id.tasks_to_complete_count_tv);
        }
    }
}
