package com.project.taskhub2;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Objects;


public class GroupFragment extends Fragment {

    TabLayout tabLayout;
    FrameLayout frameLayout;
    TextView groupName;
    String groupId;
    FloatingActionButton createTaskButton;

    ArrayList<User> users;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_group, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            groupName = v.findViewById(R.id.group_name_tv);
            groupName.setText(Objects.requireNonNull(bundle.get("groupName")).toString());
            groupId = bundle.getString("groupId");
            ArrayList<String> userIds = (ArrayList<String>) bundle.get("usersId");

            Bundle bundle1 = new Bundle();
            bundle1.putStringArrayList("userIds", userIds);
        }

        createTaskButton = v.findViewById(R.id.createTask);
        createTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CreateTaskActivity.class);
                intent.putExtra("groupId", groupId);
                startActivity(intent);
            }
        });

        // TODO: сделать логику переключения pager между фрагментами

        return v;
    }

    private void setInitialData() {
        User user1 = new User("1", "user1", "mail@mail.ma");
        User user2 = new User("2", "user222", "maiffl@madfsil.ma");
        User user3 = new User("3", "user33333", "fdfd@mafdsfil.ma");
        users.add(user1);
        users.add(user2);
        users.add(user3);
    }
}