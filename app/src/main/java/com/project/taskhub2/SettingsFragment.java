package com.project.taskhub2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsFragment extends Fragment {
    TextView username, email;
    FloatingActionButton logout, options;

    FirebaseAuth auth;
    boolean clicked;

    Animation rotateOpen, rotateClose, toBottom, fromBottom;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        username = v.findViewById(R.id.username_tv);
        email = v.findViewById(R.id.email_tv);

        logout = v.findViewById(R.id.logoutBtn);
        options = v.findViewById(R.id.optionsBtn);

        rotateClose = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_cloe_anim);
        rotateOpen = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_open_anim);
        fromBottom = AnimationUtils.loadAnimation(getContext(), R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(getContext(), R.anim.to_bottom_anim);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsClicked();
            }
        });

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

    private void onOptionsClicked() {
        setVisibility();
        setAnimations();
        clicked = !clicked;
    }
    private void setVisibility() {
        if (!clicked) logout.setVisibility(View.VISIBLE);
        else logout.setVisibility(View.INVISIBLE);
    }
    private void setAnimations() {
        if (!clicked) {
            options.startAnimation(rotateOpen);
            logout.startAnimation(fromBottom);
        }
        else {
            options.startAnimation(rotateClose);
            logout.startAnimation(toBottom);
        }
    }
}