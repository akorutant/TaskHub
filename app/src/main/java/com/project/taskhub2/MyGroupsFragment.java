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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MyGroupsFragment extends Fragment {

    ArrayList<Group> groups = new ArrayList<>();

    RecyclerView recyclerView;
    GroupAdapter groupsAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_groups, container, false);

        setInitialData();
        recyclerView = v.findViewById(R.id.groupsRecycleView);
        groupsAdapter = new GroupAdapter(this.getContext(), groups);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(groupsAdapter);
        FloatingActionButton createOrJoinGroup = v.findViewById(R.id.createOrJoinGroup);

        createOrJoinGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CreateGroupActivity.class));
            }
        });

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

        return v;
    }

    private void setInitialData(){

    }
}