package com.capstone.petgroomingapplication.registration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.capstone.petgroomingapplication.Login;
import com.capstone.petgroomingapplication.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hbb20.CountryCodePicker;

public class Registration extends AppCompatActivity {

    TextView textViewLogin;
    Button signupBtn;
    CountryCodePicker RcountryCodePicker;
    EditText phoneInput;
    ProgressBar progressBar;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        textViewLogin = findViewById(R.id.btnLogin);
        getLoginButton();

        signupBtn = findViewById(R.id.btnSignUp);
        getSignUp();

        RcountryCodePicker = findViewById(R.id.countryCodePickerReg);
        phoneInput = findViewById(R.id.phoneNumber);
        progressBar = findViewById(R.id.RprogressBar);

        progressBar.setVisibility(View.GONE);

        RcountryCodePicker.registerCarrierNumberEditText(phoneInput);

    }
    private void getLoginButton() {
        textViewLogin.setOnClickListener(v -> {
            Intent intent = new Intent(Registration.this, Login.class);
            startActivity(intent);
        });
    }
    private void getSignUp(){
        signupBtn.setOnClickListener(v -> {

            if (!RcountryCodePicker.isValidFullNumber()) {
                phoneInput.setError("Phone number not valid");
                return;
            }

            String phoneNumber = RcountryCodePicker.getFullNumberWithPlus();

            // Check if the phone number is already registered
            checkPhoneNumberAvailability(phoneNumber, isAvailable -> {
                if (isAvailable) {
                    // Phone number is available, proceed with registration
                    Intent intent = new Intent(Registration.this, OTP_Verification.class);
                    intent.putExtra("phone", phoneNumber);
                    startActivity(intent);
                } else {
                    // Phone number is already registered
                    phoneInput.setError("Number already registered");
                }
            });
        });
    }

    private void checkPhoneNumberAvailability(String phoneNumber, PhoneNumberCheckCallback callback) {

        db.collection("admin") // Replace with your actual collection name
                .whereEqualTo("phoneNumber", phoneNumber)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        boolean isAvailable = task.getResult().isEmpty();
                        callback.onResult(isAvailable);
                    } else {
                        Toast.makeText(Registration.this, "Error checking phone number", Toast.LENGTH_SHORT).show();
                        callback.onResult(false);
                    }
                });
    }

    // Callback interface to handle phone number check result
    interface PhoneNumberCheckCallback {
        void onResult(boolean isAvailable);
    }
}