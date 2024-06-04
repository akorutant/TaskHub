package com.project.taskhub2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    public FirebaseAuth firebaseAuth;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("User ");
        DatabaseReference taskRef = database.getReference("Task");
        DatabaseReference groupRef = database.getReference("Group");

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) startActivity(new Intent(this, LoginActivity.class));

        String username = firebaseAuth.getCurrentUser().getDisplayName();
        String email = firebaseAuth.getCurrentUser().getEmail();

        bottomNavigationView = findViewById(R.id.bottom_nav);
        setNewFragment(new MainFragment(), null);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView.setSelectedItemId(R.id.nav_main);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_settings) {
                    Bundle bundle = new Bundle();
                    bundle.putString("username", username);
                    bundle.putString("email", email);
                    Log.i("BUNDLE", bundle.getString("username"));
                    setNewFragment(new SettingsFragment(), bundle);
                }
                else if (item.getItemId() == R.id.nav_main) {
                    setNewFragment(new MainFragment(), null);
                }
                else if (item.getItemId() == R.id.nav_groups) {
                    setNewFragment(new MyGroupsFragment(), null);
                }
                else {
                    return false;
                }
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_OK:
                HashMap<String, String> newTask = (HashMap<String, String>) data.getSerializableExtra("creationData");
                break;
        }
    }

    public void setNewFragment(Fragment fragment, Bundle bundle) {
        if (bundle != null) fragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout, fragment);
        ft.commit();
    }

    public FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }
}