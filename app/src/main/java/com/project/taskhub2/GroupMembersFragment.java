package com.project.taskhub2;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class GroupMembersFragment extends Fragment {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private ArrayList<User> users = new ArrayList<>();
    private String groupId;

    public GroupMembersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_group_members, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            groupId = bundle.getString("groupId");
        }

        if (groupId == null) {
            Log.e("GroupMembersFragment", "groupId is null");
            return v;
        }

        recyclerView = v.findViewById(R.id.groupMembersRecyclerView);
        userAdapter = new UserAdapter(this.getContext(), users);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(userAdapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference groupRef = database.getReference("Group").child(groupId).child("users");
        groupRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users.clear();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    if (user != null) {
                        users.add(user);
                    }
                }
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("GroupMembersFragment", "onCancelled", databaseError.toException());
            }
        });

        return v;
    }
}
