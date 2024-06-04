package com.project.taskhub2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsFragment extends Fragment {
    TextView username, email;
    MaterialButton logout;

    FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        username = v.findViewById(R.id.username_tv);
        email = v.findViewById(R.id.email_tv);
        logout = v.findViewById(R.id.logoutButton);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                if (user != null) {
                    Log.i("LOGOUT", "FAIL: " + user.getDisplayName());
                }
                else {
                    Log.i("LOGOUT", "suxxesxs");
                }

            }
        });

        assert user != null;

        username.setText(user.getDisplayName());
        email.setText(user.getEmail());

        return v;
    }
}