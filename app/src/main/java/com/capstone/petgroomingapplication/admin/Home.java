package com.capstone.petgroomingapplication.admin;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.capstone.petgroomingapplication.R;
import com.capstone.petgroomingapplication.model.UserModel;
import com.capstone.petgroomingapplication.store.LocalUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

public class Home extends Fragment {

    private TextView totalSalesAmount, averageSalesAmount, growthPercentage, dailySalesAmount, topProducts, appointmentDetails, userName, welcomeMessage;
    private FirebaseFirestore firestore;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firestore = FirebaseFirestore.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the fragment layout
        View rootView = inflater.inflate(R.layout.fragment_home_admin, container, false);

        // Initialize TextViews
        totalSalesAmount = rootView.findViewById(R.id.totalSalesAmount);
        averageSalesAmount = rootView.findViewById(R.id.averageSalesAmount);
        growthPercentage = rootView.findViewById(R.id.growthPercentage);
        dailySalesAmount = rootView.findViewById(R.id.dailySalesAmount);
        topProducts = rootView.findViewById(R.id.topProducts);
        appointmentDetails = rootView.findViewById(R.id.appointmentDetails);
        userName = rootView.findViewById(R.id.userName);
        welcomeMessage = rootView.findViewById(R.id.welcomeBack);

        // Fetch and display username
//        fetchAndDisplayUsername(rootView);
        // Set other data
//        setSalesData();
//        setTopProducts();
//        setAppointments();

        getUserRelatedData();

        return rootView;
    }

    @SuppressLint("SetTextI18n")
    private void getUserRelatedData() {
        String Username = LocalUser.getUsername().toUpperCase();
        userName.setText(Username);
    }

    private void fetchAndDisplayUsername(View rootView) {
        Log.e("App", FirebaseAuth.getInstance().getCurrentUser().toString());

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        db.collection("admin").document(currentUserId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                UserModel user = document.toObject(UserModel.class);
                                String username = user.getUsername();
                                userName.setText(username);
                            } else {
                                Log.d("Firestore", "No such document");
                            }
                        } else {
                            Log.d("Firestore", "get failed with ", task.getException());
                        }
                    }
                });
        DocumentReference docRef = firestore.collection("admin").document("admin");

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
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

    private void setSalesData() {
        // Sample data
        double totalSales = 0;
        double averageSales = 0;
        double growthPercent = 0;
        double dailySales = 0;

        totalSalesAmount.setText(String.format("Php%.2f", totalSales));
        averageSalesAmount.setText(String.format("Php%.2f", averageSales));
        growthPercentage.setText(String.format("+%.1f%%", growthPercent));
        dailySalesAmount.setText(String.format("Php%.2f", dailySales));
    }

    @SuppressLint("DefaultLocale")
    private void setTopProducts() {
        // Sample top products
        String[] products = {"Product A", "Product B", "Product C"};
        StringBuilder topProductsString = new StringBuilder();

        for (int i = 0; i < products.length; i++) {
            topProductsString.append(String.format("%d. %s\n", i + 1, products[i]));
        }

        topProducts.setText(topProductsString.toString().trim());
    }

    private void setAppointments() {
        // Sample appointments details
        String appointments = "No more appointments";
        appointmentDetails.setText(appointments);
    }
}
