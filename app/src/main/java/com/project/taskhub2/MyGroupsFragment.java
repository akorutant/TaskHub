package com.project.taskhub2;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MyGroupsFragment extends Fragment {

    ArrayList<Group> groups = new ArrayList<>();

    RecyclerView recyclerView;
    GroupAdapter groupsAdapter;
    boolean clicked;
    FloatingActionButton createOrJoinBtn, createBtn, joinBtn;

    Animation rotateOpen, rotateClose, toBottom, fromBottom;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_groups, container, false);

        setInitialData();
        recyclerView = v.findViewById(R.id.groupsRecycleView);
        groupsAdapter = new GroupAdapter(this.getContext(), groups);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(groupsAdapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this.getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Group selectedGroup = groups.get(position);
                        ArrayList<String> userIdArray = new ArrayList<>();
                        for (User user: selectedGroup.users) userIdArray.add(user.getId());

                        Bundle bundle = new Bundle();
                        bundle.putString("groupId", selectedGroup.id);
                        bundle.putString("groupName", selectedGroup.name);
                        bundle.putStringArrayList("usersId", userIdArray);

                        GroupFragment groupFragment = new GroupFragment();
                        groupFragment.setArguments(bundle);

                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.frameLayout, groupFragment);
                        ft.commit();
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        Log.i("Long click", "ECHKEREEE");
                    }
                })
        );

        rotateClose = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_cloe_anim);
        rotateOpen = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_open_anim);
        fromBottom = AnimationUtils.loadAnimation(getContext(), R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(getContext(), R.anim.to_bottom_anim);

        createOrJoinBtn = v.findViewById(R.id.createOrJoinGroup);
        createBtn = v.findViewById(R.id.createGroup);
        joinBtn = v.findViewById(R.id.joinGroup);
        clicked = false;

        createOrJoinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsButtonClicked();
            }
        });


        /*
        createOrJoinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CreateGroupActivity.class));
            }
        });
         */



        return v;
    }

    private void setInitialData(){

    }

    private void onOptionsButtonClicked() {
        setVisibility(clicked);
        setAnimation(clicked);
        clicked = !clicked;
    }
    private void setVisibility(boolean clicked) {
        if (!clicked) {
            createBtn.setVisibility(View.VISIBLE);
            joinBtn.setVisibility(View.VISIBLE);
        } else {
            createBtn.setVisibility(View.INVISIBLE);
            joinBtn.setVisibility(View.INVISIBLE);
        }
    }
    private void setAnimation(boolean clicked) {
        if (!clicked) {
            createBtn.startAnimation(fromBottom);
            joinBtn.startAnimation(fromBottom);
            createOrJoinBtn.startAnimation(rotateOpen);
        } else {
            createBtn.startAnimation(toBottom);
            joinBtn.startAnimation(toBottom);
            createOrJoinBtn.startAnimation(rotateClose);
        }
    }
}