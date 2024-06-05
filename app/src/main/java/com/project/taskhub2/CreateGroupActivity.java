package com.project.taskhub2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CreateGroupActivity extends AppCompatActivity {
    EditText groupTitle, groupBio, groupMembersCount;
    Button submitButton;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference groupRef = database.getReference("Group");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        groupTitle = findViewById(R.id.groupTitle);
        groupBio = findViewById(R.id.groupBio);
        groupMembersCount = findViewById(R.id.groupMembersCount);

        submitButton = findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String groupNameText = groupTitle.getText().toString();
                String description = groupBio.getText().toString();
                String groupMembersCountText = groupMembersCount.getText().toString();
                int groupMembersCount = Integer.parseInt(groupMembersCountText);

                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                assert firebaseUser != null;
                String userId = firebaseUser.getUid();
                String userName = firebaseUser.getDisplayName();
                String userEmail = firebaseUser.getEmail();
                User owner =  new User(userId, userName, userEmail);

                String groupId = groupRef.push().getKey();

                ArrayList<User> users = new ArrayList<>();
                users.add(owner);

                String slug = UUID.randomUUID().toString().substring(0, 6);

                Group group = new Group(groupId, groupNameText, description, owner, groupMembersCount, slug);

                assert groupId != null;
                groupRef.child(groupId).setValue(group);

                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();

            }
        });
    }
}
