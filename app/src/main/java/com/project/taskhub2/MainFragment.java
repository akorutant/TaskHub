package com.project.taskhub2;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

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

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this.getContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Task selectedTask = tasks.get(position);
                        Gson gson = new Gson();
                        String taskJson = gson.toJson(selectedTask);

                        Intent intent = new Intent(getContext(), TaskActivity.class);
                        intent.putExtra("task_info", taskJson);
                        startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        Log.i("ESHKEREEE", " LONG!!!!!!! click on item)");
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

        tasks.add(new Task("1", "СЕРЬЕЗНО", "Текст задачи для теста", user1, gr1));
        tasks.add(new Task("2", "Что-то сделать ээ", "аовылмроролруолкруцоролровыарвыоаротмсчолмтолраолыр", user2, gr1));
        Task taskTest = tasks.get(1);
        taskTest.setSelect(true);
        taskTest.setWorker(user3);

        tasks.add(new Task("3", "Тест реадктирования", "дота2", user2, gr1));
    }
}