package com.project.taskhub2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class MainFragment extends Fragment {
    public ArrayList<Task> tasks = new ArrayList<Task>();
    RecyclerView recyclerView;
    TaskAdapter taskAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_main, container, false);

        setInitialData();
        recyclerView = v.findViewById(R.id.recycleView);
        taskAdapter = new TaskAdapter(this.getContext(), tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(taskAdapter);

        return v;
    }

    private void setInitialData(){

        User user1 = new User("1", "user1", "mail@mail.ma");
        User user2 = new User("2", "user222", "maiffl@madfsil.ma");
        User user3 = new User("3", "user33333", "fdfd@mafdsfil.ma");

        Group gr1 = new Group("1", "gr1", user1);
        Group gr2 = new Group("2", "gr2 team", user1);

        tasks.add(new Task("1", "Что-то сделать ээ", "аовылмроролруолкруцоролровыарвыоаротмсчолмтолраолыр", user1, gr1));
        tasks.add(new Task("2", "Что-то сделать ээ", "аовылмроролруолкруцоролровыарвыоаротмсчолмтолраолыр", user2, gr1));
        tasks.add(new Task("3", "Что-то сделать ээ", "аовылмроролруолкруцоролровыарвыоаротмсчолмтолраолыр", user3, gr2));
        tasks.add(new Task("4", "Что-то сделать ээ", "аовылмроролруолкруцоролровыарвыоаротмсчолмтолраолыр", user2, gr2));
        tasks.add(new Task("5", "Что-то сделать ээ", "аовылмроролруолкруцоролровыарвыоаротмсчолмтолраолыр", user3, gr1));
        tasks.add(new Task("6", "Что-то сделать ээ", "аовылмроролруолкруцоролровыарвыоаротмсчолмтолраолыр", user1, gr1));
        tasks.add(new Task("7", "Что-то сделать ээ", "аовылмроролруолкруцоролровыарвыоаротмсчолмтолраолыр", user3, gr1));

    }
}