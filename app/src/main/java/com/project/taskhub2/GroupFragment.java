package com.project.taskhub2;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;


public class GroupFragment extends Fragment {

    TabLayout tabLayout;
    FrameLayout frameLayout;
    TextView groupName, groupCode;
    String groupId;
    FloatingActionButton createTaskButton, logoutGroupBtn, optionsBtn, refreshBtn;

    Animation rotateOpen, rotateClose, toBottom, fromBottom;

    ArrayList<User> users;
    boolean clicked;
    private GroupTasksFragment groupTasksFragment;
    private GroupMembersFragment groupMembersFragment;
    FirebaseDatabase database;
    DatabaseReference groupRef;
    FirebaseAuth auth;
    FirebaseUser user;


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

        database = FirebaseDatabase.getInstance();
        groupRef = database.getReference("Group").child(groupId);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        groupCode = v.findViewById(R.id.groupCodeTv);
        groupRef.child("slug").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String slug = snapshot.getValue(String.class);
                    groupCode.setText(slug);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        tabLayout = v.findViewById(R.id.tabLayout);
        ViewPager viewPager = v.findViewById(R.id.viewPager);

        groupTasksFragment = new GroupTasksFragment();
        groupMembersFragment = new GroupMembersFragment();

        groupTasksFragment.setArguments(bundle);
        groupMembersFragment.setArguments(bundle);
        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position == 0) {
                    return groupTasksFragment;
                } else if (position == 1) {
                    return groupMembersFragment;
                }
                return null;
            }

            @Override
            public int getCount() {
                // Теперь у нас две вкладки
                return 2;
            }
        });

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText(R.string.tasks);
        tabLayout.getTabAt(1).setText(R.string.members);

        rotateClose = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_cloe_anim);
        rotateOpen = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_open_anim);
        fromBottom = AnimationUtils.loadAnimation(getContext(), R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(getContext(), R.anim.to_bottom_anim);

        optionsBtn = v.findViewById(R.id.groupOptionsBtn);
        logoutGroupBtn = v.findViewById(R.id.logoutGroupBtn);
        createTaskButton = v.findViewById(R.id.createTask);
        refreshBtn = v.findViewById(R.id.refreshBtn);
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
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference groupRef = FirebaseDatabase.getInstance().getReference("Group");
                if (groupId == null) {
                    // Не удалось получить идентификатор группы, выходим из метода
                    return;
                }

                groupRef.child(groupId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.exists()) {
                            // Группа не найдена, выходим из метода
                            return;
                        }

                        Group group = dataSnapshot.getValue(Group.class);

                        // Удаляем текущего пользователя из списка пользователей группы
                        User currentUserUser = null;
                        for (User user : group.getUsers()) {
                            if (user.getId().equals(currentUser.getUid())) {
                                currentUserUser = user;
                                break;
                            }
                        }
                        if (currentUserUser != null) {
                            group.getUsers().remove(currentUserUser);
                        }

                        // Если владелец группы выходит из нее, удаляем группу из базы данных
                        if (group.getOwner().getId().equals(currentUser.getUid())) {
                            dataSnapshot.getRef().removeValue();
                        } else {
                            // Сохраняем изменения в базе данных
                            dataSnapshot.getRef().child("users").setValue(group.getUsers());
                        }

                        MyGroupsFragment myGroupsFragment = new MyGroupsFragment();


                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.frameLayout, myGroupsFragment);
                        ft.commit();


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Обрабатываем ошибку
                    }
                });
            }
        });

        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newCode = generateNewCode();
                groupRef.child("slug").setValue(newCode);
                groupCode.setText(newCode);
                Toast.makeText(getContext(), "Код группы обновлен!", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
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
            if (user.getUid().equals(groupRef.child("owner").child("id").toString())) {
                refreshBtn.setVisibility(View.VISIBLE);
            }
        } else {
            createTaskButton.setVisibility(View.INVISIBLE);
            logoutGroupBtn.setVisibility(View.INVISIBLE);
            refreshBtn.setVisibility(View.INVISIBLE);
        }
    }
    private void setAnimation(boolean clicked) {
        if (!clicked) {
            createTaskButton.startAnimation(fromBottom);
            logoutGroupBtn.startAnimation(fromBottom);
            refreshBtn.startAnimation(fromBottom);
            optionsBtn.startAnimation(rotateOpen);
        } else {
            createTaskButton.startAnimation(toBottom);
            logoutGroupBtn.startAnimation(toBottom);
            refreshBtn.startAnimation(toBottom);
            optionsBtn.startAnimation(rotateClose);
        }
    }

    private String generateNewCode() {
        String code = UUID.randomUUID().toString().substring(0, 6);
        return code;
    }
}