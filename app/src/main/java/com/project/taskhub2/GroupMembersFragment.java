package com.project.taskhub2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public class GroupMembersFragment extends Fragment {

    RecyclerView recyclerView;
    UserAdapter userAdapter;
    List<User> users;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_group_members, container, false);

        setInitialData();
        recyclerView = v.findViewById(R.id.groupMembersRecyclerView);
        userAdapter = new UserAdapter(getContext(), users);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(userAdapter);

        return v;
    }

    private void setInitialData() {

        User user1 = new User("1", "user1", "mail@mail.ma");
        User user2 = new User("2", "user222", "maiffl@madfsil.ma");
        User user3 = new User("3", "user33333", "fdfd@mafdsfil.ma");

    }

}