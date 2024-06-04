package com.project.taskhub2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {

    public final LayoutInflater inflater;
    private final List<Group> groups;
//    private final List<Task> groupTasks;

    public GroupAdapter(Context context, List<Group> groups) {
        this.groups = groups;
        this.inflater = LayoutInflater.from(context);
//        this.groupTasks = groupTasks;
    }

    public GroupAdapter(LayoutInflater inflater, List<Group> groups) {
        this.inflater = inflater;
        this.groups = groups;
//        this.groupTasks = groupTasks;
    }

    @NonNull
    @Override
    public GroupAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.group_item, parent, false);
        return new GroupAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Group group = groups.get(position);

        holder.groupName.setText(group.getName());
        holder.peopleCount.setText(String.valueOf(group.users.length));

//        List<Task> filteredTasks = groupTasks.stream()
//                .filter(task -> task.getGroup().getId().equals(group.getId()))
//                .collect(Collectors.toList());
//
//        holder.tasksCount.setText(String.valueOf(filteredTasks.size()));
//
//        int completedTasksCount = (int) filteredTasks.stream()
//                .filter(Task::getCompleted)
//                .count();
//        holder.completedTasks.setText(String.valueOf(completedTasksCount));
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
