package com.project.taskhub2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    public final LayoutInflater inflater;
    private final List<Task> tasks;

    public TaskAdapter(Context context, List<Task> tasks) {
        this.tasks = tasks;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.task_item, parent, false);
        return new TaskAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.taskTitle.setText(task.getName());
        holder.taskDescription.setText(task.getText());
        holder.taskGroup.setText(task.getGroup().getName());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView taskTitle, taskDescription, taskGroup;

        ViewHolder(View view) {
            super(view);
            taskTitle = view.findViewById(R.id.taskTitle);
            taskDescription = view.findViewById(R.id.taskDescription);
            taskGroup = view.findViewById(R.id.taskGroup);
        }

    }


}
