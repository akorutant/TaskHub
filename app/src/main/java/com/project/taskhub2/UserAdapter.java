package com.project.taskhub2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    public final LayoutInflater inflater;
    private final List<User> users;

    public UserAdapter(Context context, List<User> users) {
        this.users = users;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.user_item, parent, false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = users.get(position);

    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView username, email;

        ViewHolder(View view) {
            super(view);
            username = view.findViewById(R.id.username);
            email = view.findViewById(R.id.email);
        }

    }


}
