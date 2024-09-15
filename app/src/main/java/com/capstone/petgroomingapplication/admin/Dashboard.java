package com.capstone.petgroomingapplication.admin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.capstone.petgroomingapplication.Login;
import com.capstone.petgroomingapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class Dashboard extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        bottomNavigationView = findViewById(R.id.bottomNavigationAdmin);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_viewAdmin);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        // Setup the navigation drawer and listeners
        setupListeners(savedInstanceState);
    }

    private void setupListeners(Bundle savedInstanceState) {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // If no previous state exists, load the default fragment
        if (savedInstanceState == null) {
            loadFragment(new Home(), true);
            navigationView.setCheckedItem(R.id.home);
        }

        // Setup the bottom navigation item selection listener
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.home) {
                loadFragment(new com.capstone.petgroomingapplication.admin.Home(), false);
            } else if (itemId == R.id.ProdAndServ) {
                loadFragment(new  com.capstone.petgroomingapplication.admin.ProductsAndServices(), false);
            } else if (itemId == R.id.SalesReport) {
                loadFragment(new  com.capstone.petgroomingapplication.admin.SalesReport(), false);
            } else {
                loadFragment(new  com.capstone.petgroomingapplication.admin.Appointment(), false);
            }
            return true;
        });

        // Setup the navigation drawer item selection listener
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.home) {
                loadFragment(new  com.capstone.petgroomingapplication.admin.Home(), false);
            } else if (id == R.id.accountA) {
                loadFragment(new  com.capstone.petgroomingapplication.admin.Account(), false);
            } else if (id == R.id.petsprofiles) {
                loadFragment(new  com.capstone.petgroomingapplication.admin.PetProfile(), false);
            } else if (id == R.id.sales) {
                loadFragment(new  com.capstone.petgroomingapplication.admin.SalesReport(), false);
            } else if (id == R.id.Appointments) {
                loadFragment(new  com.capstone.petgroomingapplication.admin.Appointment(), false);
            } else if (id == R.id.inventory) {
                loadFragment(new  com.capstone.petgroomingapplication.admin.Inventory(), false);
            } else if (id == R.id.calendar){
                loadFragment(new  com.capstone.petgroomingapplication.admin.Calendar(), false);
            }
            else if(id == R.id.admin_logout) {
                // Show confirmation dialog
                new AlertDialog.Builder(Dashboard.this)
                        .setTitle("Logout")
                        .setMessage("Are you sure you want to log out?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            // Clear user data from SharedPreferences
                            SharedPreferences preferences = getSharedPreferences("UserSession", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.clear();  // Clear all stored data
                            editor.apply();

                            // Redirect to Login activity
                            Intent intent = new Intent(Dashboard.this, Login.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                            // Optionally finish the current activity
                            finish();
                        })
                        .setNegativeButton("No", (dialog, which) -> {
                            // Dismiss the dialog and do nothing
                            dialog.dismiss();
                        })
                        .create()
                        .show();
            }

            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }
    // Helper method to load fragments into the container
    private void loadFragment(Fragment fragment, boolean isAppInitialized) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (isAppInitialized) {
            fragmentTransaction.add(R.id.fragment_container, fragment);
        } else {
            fragmentTransaction.replace(R.id.fragment_container, fragment);
        }
        fragmentTransaction.commit();
    }
}
