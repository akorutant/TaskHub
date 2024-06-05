package com.project.taskhub2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JoinGroupActivity extends AppCompatActivity {
    EditText groupCode;
    Button joinBtn;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference groupRef = database.getReference("Group");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_group_activity);

        groupCode = findViewById(R.id.groupCode);
        joinBtn = findViewById(R.id.joinBtn);

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = groupCode.getText().toString().trim();
                if (code.isEmpty()) {
                    Toast.makeText(getApplicationContext(), R.string.code_empty, Toast.LENGTH_LONG).show();
                    return;
                }

                groupRef.orderByChild("slug").equalTo(code).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.exists()) {
                            Toast.makeText(getApplicationContext(), R.string.not_found_group, Toast.LENGTH_LONG).show();
                            return;
                        }

                        DataSnapshot groupSnapshot = dataSnapshot.getChildren().iterator().next();

                        Group group = groupSnapshot.getValue(Group.class);
                        ArrayList<User> users = new ArrayList<>();
                        for (DataSnapshot userSnapshot : groupSnapshot.child("users").getChildren()) {
                            User user = userSnapshot.getValue(User.class);
                            if (user != null) {
                                users.add(user);
                            }
                        }
                        group.setUsers(users);

                        if (group == null || group.users.size() >= group.getMembersCount()) {
                            Toast.makeText(getApplicationContext(), R.string.to_many_group_users, Toast.LENGTH_LONG).show();
                            return;
                        }

                        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        if (currentUser != null) {
                            boolean userExists = false;
                            for (User user : group.getUsers()) {
                                if (user.getId().equals(currentUser.getUid())) {
                                    userExists = true;
                                    break;
                                }
                            }
                            if (userExists) {
                                Toast.makeText(getApplicationContext(), R.string.already_in_group, Toast.LENGTH_LONG).show();
                                return;
                            }
                        }

                        assert currentUser != null;
                        users.add(new User(currentUser.getUid(), currentUser.getDisplayName(), currentUser.getEmail()));
                        group.setUsers(users);
                        groupRef.child(group.getId()).setValue(group);

                        finish();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
