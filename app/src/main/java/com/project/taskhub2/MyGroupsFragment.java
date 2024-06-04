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

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this.getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Group selectedGroup = groups.get(position);
                        ArrayList<String> userIdArray = new ArrayList<>();
                        for (User user: selectedGroup.users) userIdArray.add(user.getId());

                        Bundle bundle = new Bundle();
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

        User user1 = new User("1", "user1", "mail@mail.ma");
        User user2 = new User("2", "user222", "maiffl@madfsil.ma");
        User user3 = new User("3", "user33333", "fdfd@mafdsfil.ma");

        Group gr1 = new Group("1", "gr1", user1);
        Group gr2 = new Group("2", "gr2 team", user1);
        Group gr3 = new Group("2", "gr433333team", user2, new User[]{user1, user2});
        Group gr4 = new Group("2", "gr44444eam", user1);
        Group gr5 = new Group("2", "g5r55m", user3);

        groups.add(gr1);
        groups.add(gr2);
        groups.add(gr3);
        groups.add(gr4);
        groups.add(gr5);

    }
}