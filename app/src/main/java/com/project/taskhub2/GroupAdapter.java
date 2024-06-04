package com.project.taskhub2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {

    public final LayoutInflater inflater;
    private final List<Group> groups;

    public GroupAdapter(Context context, List<Group> groups) {
        this.groups = groups;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupAdapter.ViewHolder holder, int position) {
        Group group = groups.get(position);
        holder.groupTitle
        holder.taskDescription.setText(task.getText());
        holder.taskGroup.setText(task.getGroup().getName());
    }

    @NonNull
    @Override
    public GroupAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.task_item, parent, false);
        return new GroupAdapter(view);
    }
}
