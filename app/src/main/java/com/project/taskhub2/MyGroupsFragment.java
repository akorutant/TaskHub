package com.project.taskhub2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class MyGroupsFragment extends Fragment {

    ArrayList<Group> groups = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_groups, container, false);



        return v
    }

    private void setInitialData(){

        User user1 = new User("1", "user1", "mail@mail.ma");
        User user2 = new User("2", "user222", "maiffl@madfsil.ma");
        User user3 = new User("3", "user33333", "fdfd@mafdsfil.ma");

        Group gr1 = new Group("1", "gr1", user1);
        Group gr2 = new Group("2", "gr2 team", user1);

        groups.add(gr1);
        groups.add(gr2)

    }
}