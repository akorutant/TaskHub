package com.project.taskhub2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.Objects;


public class GroupFragment extends Fragment {

    TabLayout tabLayout;
    FrameLayout frameLayout;
    TextView groupName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_group, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            groupName = v.findViewById(R.id.group_name_tv);
            groupName.setText(Objects.requireNonNull(bundle.get("groupName")).toString());
        }

        // TODO: сделать логику переключения pager между фрагментами ок там на самом деле 80% простые но за 2 дня вряд ли упеешь все разобрать

        return v;
    }
}