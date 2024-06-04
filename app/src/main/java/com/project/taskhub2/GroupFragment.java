package com.project.taskhub2;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
    FloatingActionButton createTaskButton, logoutGroupBtn, optionsBtn;

    Animation rotateOpen, rotateClose, toBottom, fromBottom;

    ArrayList<User> users;
    boolean clicked;

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

        rotateClose = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_cloe_anim);
        rotateOpen = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_open_anim);
        fromBottom = AnimationUtils.loadAnimation(getContext(), R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(getContext(), R.anim.to_bottom_anim);

        optionsBtn = v.findViewById(R.id.groupOptionsBtn);
        logoutGroupBtn = v.findViewById(R.id.logoutGroupBtn);
        createTaskButton = v.findViewById(R.id.createTask);
        clicked = false;

        optionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsButtonClicked();
            }
        });
        createTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CreateTaskActivity.class);
                intent.putExtra("groupId", groupId);
                startActivity(intent);
            }
        });
        logoutGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: сделать выход из группы (ЕСЛИ ЭТО ВЛАДЕЛЕЦ - УДАЛЯТЬ ГРУППУ К .!.-уям собачьим
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

    private void onOptionsButtonClicked() {
        setVisibility(clicked);
        setAnimation(clicked);
        clicked = !clicked;
    }
    private void setVisibility(boolean clicked) {
        if (!clicked) {
            createTaskButton.setVisibility(View.VISIBLE);
            logoutGroupBtn.setVisibility(View.VISIBLE);
        } else {
            createTaskButton.setVisibility(View.INVISIBLE);
            logoutGroupBtn.setVisibility(View.INVISIBLE);
        }
    }
    private void setAnimation(boolean clicked) {
        if (!clicked) {
            createTaskButton.startAnimation(fromBottom);
            logoutGroupBtn.startAnimation(fromBottom);
            optionsBtn.startAnimation(rotateOpen);
        } else {
            createTaskButton.startAnimation(toBottom);
            logoutGroupBtn.startAnimation(toBottom);
            optionsBtn.startAnimation(rotateClose);
        }
    }
}