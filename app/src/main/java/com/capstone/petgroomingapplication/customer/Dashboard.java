package com.capstone.petgroomingapplication.customer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_customer_dashboard);

        bottomNavigation = findViewById(R.id.bottomNavigation);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_viewCustomer);
        Toolbar toolbar = findViewById(R.id.toolbarCustomer);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            loadFragment(new com.capstone.petgroomingapplication.customer.Home(), true);
            navigationView.setCheckedItem(R.id.home);
        }

        // Bottom Navigation Item Selection Listener
        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.home) {
                loadFragment(new com.capstone.petgroomingapplication.customer.Home(), false);
            } else if (itemId == R.id.payment) {
                loadFragment(new com.capstone.petgroomingapplication.customer.Payment(), false);
            } else if (itemId == R.id.messages) {
                loadFragment(new com.capstone.petgroomingapplication.customer.Messages(), false);
            } else {
                loadFragment(new com.capstone.petgroomingapplication.customer.Account(), false);
            }

            return true;
        });

        // Navigation Drawer Item Selection Listener
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.home) {
                loadFragment(new com.capstone.petgroomingapplication.customer.Home(), false);
            } else if (id == R.id.account) {
                loadFragment(new com.capstone.petgroomingapplication.customer.Account(), false);
            } else if (id == R.id.petProfile) {
                loadFragment(new com.capstone.petgroomingapplication.customer.PetProfile(), false);
            } else if (id == R.id.appoint) {
                loadFragment(new com.capstone.petgroomingapplication.customer.Services(), false);
            } else if (id == R.id.messages) {
                loadFragment(new com.capstone.petgroomingapplication.customer.Messages(), false);
            } else if (id == R.id.faq) {
                loadFragment(new com.capstone.petgroomingapplication.customer.FaqsFragment(), false);
            } else if (id == R.id.customer_logout) {
                showLogoutConfirmationDialog();
            }

            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    private void loadFragment(Fragment fragment, boolean isAppInitialized) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (isAppInitialized) {
            fragmentTransaction.add(R.id.frameLayout, fragment);
        } else {
            fragmentTransaction.replace(R.id.frameLayout, fragment);
        }

        fragmentTransaction.commit();
    }

    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    clearUserSession();
                    redirectToLogin();
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    private void clearUserSession() {
        SharedPreferences preferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

    private void redirectToLogin() {
        Intent intent = new Intent(this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
