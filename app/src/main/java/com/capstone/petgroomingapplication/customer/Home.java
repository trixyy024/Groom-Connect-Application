package com.capstone.petgroomingapplication.customer;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.capstone.petgroomingapplication.R;
import com.capstone.petgroomingapplication.model.UserModel;
import com.capstone.petgroomingapplication.store.LocalUser;
import com.capstone.petgroomingapplication.utils.AndroidUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Home extends Fragment {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private RecyclerView recyclerView;
    private FirebaseFirestore firestore;
    private TextView welcomeMessage, userName;
    private ImageView profileDB;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firestore = FirebaseFirestore.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home__customer, container, false);

        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);
        welcomeMessage = view.findViewById(R.id.welcomeBack);
        userName = view.findViewById(R.id.userNameCustomer);
        profileDB = view.findViewById(R.id.profilePicture);

        viewProfileBtn(); // Set the onClickListener for the profile button
        viewPager.setAdapter(new MyFragmentStateAdapter(this));

        // Attach the TabLayout with the ViewPager2
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Products");
                    break;
                case 1:
                    tab.setText("Services");
                    break;
            }
        }).attach();
        getUserRelatedData();
        // Fetch and display user data
       // fetchUserDataAndDisplayData(view);

        return view;
    }

    @SuppressLint("SetTextI18n")
    private void getUserRelatedData() {
        String Username = LocalUser.getUsername().toUpperCase();
        userName.setText(Username);
    }


    private void fetchUserDataAndDisplayData(View rootView) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        String collection = "customer";
        db.collection(collection).document(currentUserId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    UserModel user = document.toObject(UserModel.class);
                    updateUI(user);
                } else {
                    Toast.makeText(getActivity(), "No such user", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "Error getting documents: " + task.getException(), Toast.LENGTH_SHORT).show();
            }
        });

        DocumentReference docRef = firestore.collection(currentUserId).document(currentUserId);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        UserModel user = document.toObject(UserModel.class);
                        if (user != null) {
                            String username = user.getUsername();
                            welcomeMessage.setText("WELCOME BACK, " + username.toUpperCase());
                        }
                    }
                } else {
                    // Handle the error
                }
            }
        });
    }

    private static class MyFragmentStateAdapter extends FragmentStateAdapter {
        public MyFragmentStateAdapter(@NonNull Fragment fragment) {
            super(fragment);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new Products();
                case 1:
                    return new Services();
                default:
                    throw new IllegalArgumentException("Invalid position: " + position);
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }

    // Implement this method to retrieve the current user's ID
    private String getCurrentUserId() {
        // You can retrieve the user ID from FirebaseAuth or any other source you're using
        // For example:
        // return FirebaseAuth.getInstance().getCurrentUser().getUid();
        return "USER_ID"; // Replace with actual user ID retrieval logic
    }

    private void viewProfileBtn() {
        profileDB.setOnClickListener(view -> {
            Log.d("HomeFragment", "Profile button clicked");
            loadFragment(new com.capstone.petgroomingapplication.customer.Account());
        });
    }


    // Load Fragment method within the Fragment class
    private void loadFragment(Fragment fragment) {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    private void updateUI(UserModel user) {
        if (user != null) {
            // Update the TextViews with user information
            // editInfoButton.setText(user.getUsername()); // Example of setting username

            // Update other fields as necessary, e.g., contactNo, address
            // Set profile picture if the user has one
            if (user.getProfilePicUrl() != null) {
                AndroidUtils.setProfilePic(getContext(), Uri.parse(user.getProfilePicUrl()), profileDB);
            }
        }
    }
}
