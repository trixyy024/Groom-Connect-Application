package com.capstone.petgroomingapplication.registration;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.capstone.petgroomingapplication.R;
import com.capstone.petgroomingapplication.model.UserModel;
import com.capstone.petgroomingapplication.store.LocalUser;
import com.capstone.petgroomingapplication.utils.FirebaseUtils;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisteringAccount extends AppCompatActivity {

    EditText usernameInput, passwordInput, contactNoInput, addressInput;
    Button btnConASadmin;
    Button btnConAsCustomer;
    ProgressBar progressBar;
    String phoneNumber;
    UserModel userModel;
    UserModel localUser;
    CheckBox checkboxAgree;

    private String userType = "customer"; // Default value

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registering_account);

        // Initialize views
        TextView textView = findViewById(R.id.text_view_terms_conditions);
        String text = "By clicking 'Agree', you agree to our Terms and Conditions";
        SpannableString spannableString = new SpannableString(text);

        // Define the clickable part
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                // Open terms and conditions URL
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://spiffy-moonbeam-220cb2.netlify.app/"));
                startActivity(browserIntent);
            }
        };

        // Set the clickable part within the SpannableString
        spannableString.setSpan(clickableSpan, text.indexOf("Terms and Conditions"), text.indexOf("Terms and Conditions") + "Terms and Conditions".length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        usernameInput = findViewById(R.id.userNameInput);
        passwordInput = findViewById(R.id.password);
        contactNoInput = findViewById(R.id.contactNo);
        addressInput = findViewById(R.id.address);
        btnConASadmin = findViewById(R.id.btnContinueRegAdmin);
        btnConAsCustomer = findViewById(R.id.btnContinueRegCustomer);
        progressBar = findViewById(R.id.progressBarOTP);
        checkboxAgree = findViewById(R.id.checkbox_agree);

        // Retrieve phone number from intent
        phoneNumber = getIntent().getExtras().getString("phone");

        // Setup click listeners for buttons
        btnConAsCustomer.setOnClickListener(v -> {
            if (checkboxAgree.isChecked()) {
                userType = "customer"; // Set userType for customer
                setUserData();
            } else {
                checkboxAgree.setError("You must agree to the terms and conditions");
            }
        });

        btnConASadmin.setOnClickListener(v -> {
            if (checkboxAgree.isChecked()) {
                userType = "admin"; // Set userType for admin
                setUserData();
            } else {
                checkboxAgree.setError("You must agree to the terms and conditions");
            }
        });

        // Pre-fill the form with existing user data
        getUserData();
    }

    // Method to save user data to Firestore
    void setUserData() {
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();
        String contactNo = contactNoInput.getText().toString();
        String address = addressInput.getText().toString();

        if (username.isEmpty() || username.length() < 3) {
            usernameInput.setError("Username length should be at least 3 chars");
            return;
        }
        if (password.isEmpty()) {
            passwordInput.setError("Password is required");
            return;
        }
        if (contactNo.isEmpty()) {
            contactNoInput.setError("Contact number is required");
            return;
        }
        if (address.isEmpty()) {
            addressInput.setError("Address is required");
            return;
        }

        setInProgress(true);

        FirebaseUser authUser = FirebaseAuth.getInstance().getCurrentUser();

        if (authUser != null) {
            if (localUser != null) {
                localUser.setUsername(username);
                localUser.setPassword(password);
                localUser.setContactNo(contactNo);
                localUser.setAddress(address);
                localUser.setUserId(authUser.getUid());
            } else {
                localUser = new UserModel(phoneNumber, username, Timestamp.now(), authUser.getUid(), password, contactNo, address, "");
            }

            LocalUser.Store(localUser);

            FirebaseFirestore db = FirebaseFirestore.getInstance();

            // Save user data to the appropriate Firestore collection
            db.collection(userType).document(localUser.getUserId()).set(localUser).addOnCompleteListener(task -> {
                setInProgress(false);
                if (task.isSuccessful()) {
                    // Redirect to the corresponding dashboard based on userType
                    if (userType.equals("admin")) {
                        Intent intent = new Intent(RegisteringAccount.this, com.capstone.petgroomingapplication.admin.Dashboard.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else if (userType.equals("customer")) {
                        Intent intent = new Intent(RegisteringAccount.this, com.capstone.petgroomingapplication.customer.Dashboard.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }
            });
        }
    }

    // Method to retrieve user data from Firestore and pre-fill the form
    void getUserData() {
        setInProgress(true);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Fetch data from Firestore based on userType
        db.collection(userType).document(FirebaseUtils.currentUserId()).get().addOnCompleteListener(task -> {
            setInProgress(false);
            if (task.isSuccessful()) {
                localUser = task.getResult().toObject(UserModel.class);
                if (localUser != null) {
                    usernameInput.setText(userModel.getUsername());
                    passwordInput.setText(userModel.getPassword());
                    contactNoInput.setText(userModel.getContactNo());
                    addressInput.setText(userModel.getAddress());
                }
            } else {
                // Handle error
                Log.e("GetUserData", "Error getting user data: " + task.getException().getMessage());
            }
        });
    }

    // Method to show or hide progress bar
    void setInProgress(boolean inProgress) {
        if (inProgress) {
            progressBar.setVisibility(View.VISIBLE);
            btnConASadmin.setVisibility(View.GONE);
            btnConAsCustomer.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            btnConASadmin.setVisibility(View.VISIBLE);
            btnConAsCustomer.setVisibility(View.VISIBLE);
        }
    }
}
